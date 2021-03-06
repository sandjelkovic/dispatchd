package com.sandjelkovic.dispatchd.content.service

import arrow.core.Either
import arrow.core.Option
import arrow.core.Try
import arrow.core.toOption
import com.sandjelkovic.dispatchd.content.api.JobReportCreated
import com.sandjelkovic.dispatchd.content.data.entity.Show
import com.sandjelkovic.dispatchd.content.data.entity.UpdateJob
import com.sandjelkovic.dispatchd.content.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.content.data.repository.UpdateJobRepository
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowUpdateTrakt
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider
import mu.KLogging
import org.springframework.context.ApplicationEventPublisher
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 10.2.18.
 */
open class ContentRefreshService(
    val updateJobRepository: UpdateJobRepository,
    val provider: TraktMediaProvider,
    val showRepository: ShowRepository,
    val importer: ShowImporter,
    val eventPublisher: ApplicationEventPublisher
) {
    fun updateContentIfStale(): Try<List<Show>> {
        return refreshExistingContent()
    }

    fun updateAllContent(): Long {
        TODO("Not yet implemented")
    }

    companion object : KLogging()

    private fun refreshExistingContent(): Try<List<Show>> =
        getLastUpdateTime().also { logger.debug("Refreshing content. Last update was: $it") }
            .let { fromTime ->
                provider.getUpdates(fromTime.toLocalDate())
                    .map { getIdsForUpdate(it) }
                    .map { executeContentUpdate(it) }
                    .map { extractSuccessfulShows(it) }
//        // possible optimisation for failure cases -> scan internal db and compare retrieved.updatedAt < internal.lastLocalUpdate
//        // in order to only update shows that failed in  the past. Since the update time is started from the last successful refresh.
//        // independent update of each show in order to continue the refresh evens if some fail.

            }

    private fun extractSuccessfulShows(it: List<Either<ImportException, Show>>) =
        it.filter { it.isRight() }
            .map { it as Either.Right }
            .map { it.b }

    private fun executeContentUpdate(ids: List<String>): List<Either<ImportException, Show>> = ids
        .map(importer::refreshImportedShow)
        .also { createAndSaveNewJobReport().also { eventPublisher.publishEvent(JobReportCreated(it.copy())) } }

    private fun getIdsForUpdate(updates: List<ShowUpdateTrakt>): List<String> = updates
        .map(::extractTraktId)
        .mapNotNull(Option<String>::orNull)
        .filter(::showExists)

    private fun showExists(id: String) = showRepository.findByTraktId(id).isPresent

    private fun extractTraktId(update: ShowUpdateTrakt): Option<String> = update.toOption()
        .flatMap { it.show.toOption() }
        .map(ShowTrakt::ids)
        .mapNotNull { it["trakt"] }

    private fun getLastUpdateTime(): ZonedDateTime =
        updateJobRepository.findFirstBySuccessOrderByFinishTimeDesc(true)
            .map(UpdateJob::finishTime)
            .orElseGet { ZonedDateTime.of(LocalDateTime.MIN, ZoneId.systemDefault()) }

    private fun createAndSaveNewJobReport() =
        updateJobRepository.save(UpdateJob(finishTime = ZonedDateTime.now(), success = true)).also {
            logger.debug("Saved job: $it")
        }
}
