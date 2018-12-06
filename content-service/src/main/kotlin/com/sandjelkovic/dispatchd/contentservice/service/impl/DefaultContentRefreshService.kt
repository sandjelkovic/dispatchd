package com.sandjelkovic.dispatchd.contentservice.service.impl

import arrow.core.Either
import arrow.core.Option
import arrow.core.Try
import arrow.core.toOption
import com.sandjelkovic.dispatchd.contentservice.data.entity.UpdateJob
import com.sandjelkovic.dispatchd.contentservice.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.UpdateJobRepository
import com.sandjelkovic.dispatchd.contentservice.service.ContentRefreshService
import com.sandjelkovic.dispatchd.contentservice.service.RemoteServiceException
import com.sandjelkovic.dispatchd.contentservice.service.ShowImporter
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowUpdateTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import mu.KLogging
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 10.2.18.
 */
class DefaultContentRefreshService(
    val updateJobRepository: UpdateJobRepository,
    val provider: TraktMediaProvider,
    val showRepository: ShowRepository,
    val importer: ShowImporter
) : ContentRefreshService {
    override fun updateContentIfNeeded(): Either<RemoteServiceException, Int> {
        return refreshExistingContent()
    }

    override fun updateAllContent(): Long {
        TODO("Not yet implemented")
    }

    companion object : KLogging()

    fun refreshExistingContent(): Either<RemoteServiceException, Int> =
        getLastUpdateTime()
            .also { logger.debug("Refreshing content. Last update was: $it") }
            .let { fromTime ->
                provider.getUpdates(fromTime.toLocalDate())
                    .map { getIdsForUpdate(it) }
                    .map { it.also { logger.debug { "Shows to be imported (Trakt IDs): $it" } } }
                    .map { ids -> ids.map { Try { importer.importShow(it) } }.count { it.isSuccess() } }
                    .also { createNewJobReport() }
            }
//        // possible optimisation for failure cases -> scan internal db and compare retrieved.updatedAt < internal.lastLocalUpdate
//        // in order to only update shows that failed in  the past. Since the update time is started from the last successful refresh.
//        // independent update of each show in order to continue the refresh evens if some fail.

    private fun getIdsForUpdate(it: List<ShowUpdateTrakt>): List<String> = it
        .map(::extractTraktId)
        .mapNotNull { it.orNull() }
        .filter(::showExists)

    private fun showExists(id: String) = showRepository.findByTraktId(id).isPresent

    private fun extractTraktId(update: ShowUpdateTrakt): Option<String> = update.toOption()
        .flatMap { it.show.toOption() }
        .map { it.ids }
        .filter { it.containsKey("trakt") }
        .map { it["trakt"]!! }

    private fun getLastUpdateTime(): ZonedDateTime =
        updateJobRepository.findFirstBySuccessOrderByFinishTimeDesc(true)
            .map { it.finishTime }
            .orElseGet { ZonedDateTime.of(LocalDateTime.MIN, ZoneId.systemDefault()) }

    private fun createNewJobReport() =
        updateJobRepository.save(
            UpdateJob().apply {
                finishTime = ZonedDateTime.now()
                success = true
            }
        ).also {
            logger.debug("Saved job: $it")
        }
}
