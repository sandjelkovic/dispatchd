package com.sandjelkovic.dispatchd.contentservice.service.impl

import com.sandjelkovic.dispatchd.contentservice.data.repository.UpdateJobRepository
import com.sandjelkovic.dispatchd.contentservice.service.ContentRefreshService
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import mu.KLogging

/**
 * @author sandjelkovic
 * @date 10.2.18.
 */
class DefaultContentRefreshService(val updateJobRepository: UpdateJobRepository, val provider: TraktMediaProvider) : ContentRefreshService {
    override fun updateContentIfNeeded(): Long {
        return 0;
    }

    override fun updateAllContent(): Long {
        return 0;
    }

    companion object : KLogging()

    //    private val asyncResultMapper: java.util.function.Function<AsyncResult<TvShow>, TvShow>
//        get() = { tvShowAsyncResult ->
//            try {
//                return tvShowAsyncResult.get(1, TimeUnit.MINUTES)
//            } catch (ignored: ExecutionException) {
//                return null
//            }
//        }
//
//
//    private fun lastUpdateTime() {
//        updateJobRepository.findFirstBySuccessOrderByFinishTimeDesc(true)
//                .map(UpdateJob::finishTime)
//                .orElseGet { ZonedDateTime.from(Instant.MIN) }
//    }
//
//    fun refreshExistingContent(): Long {
//        val fromTime = lastUpdateTime()
//        logger.debug("Refreshing content. Last update was: " + fromTime)
//        val updatedShows = provider.getUpdates(fromTime.toLocalDate())
//
//        val traktIds = getTraktIds(updatedShows)
//        val idsForImport = filterForLocalIds(traktIds)
//        log.debug("Shows to be imported (Trakt IDs): " + traktIds.toString())
//
//        // possible optimisation for failure cases -> scan internal db and compare retrieved.updatedAt < internal.lastLocalUpdate
//        // in order to only update shows that failed in  the past. Since the update time is started from the last successful refresh.
//
//        // independent update of each show in order to continue the refresh evens if some fail. Otherwise, this could have been one stream. (hint!, hint!)
//        val importResults = idsForImport.stream()
//                .map<AsyncResult<TvShow>> { id -> traktImporterService!!.importShowAsync(id) }
//                .collect<List<AsyncResult<TvShow>>, Any>(toList<AsyncResult<TvShow>>())
//
//        val count = importResults.stream()
//                .map<TvShow>(asyncResultMapper)
//                .filter(Predicate<TvShow> { Objects.nonNull(it) })
//                .count()
//
//        saveJob()
//        return count
//    }
//
//    private fun filterForLocalIds(traktIds: List<String>): List<String> {
//        return traktIds.stream()
//                .filter { id -> tvShowService!!.findByTraktId(id).isPresent() }
//                .collect<List<String>, Any>(toList<String>())
//    }
//
//    private fun getTraktIds(updatedShows: List<ShowUpdateTrakt>): List<String> {
//        return updatedShows.stream()
//                .map<TvShowTrakt>(Function<ShowUpdateTrakt, TvShowTrakt> { it.getShow() })
//                .map<Map<String, String>>(Function<TvShowTrakt, Map<String, String>> { it.getIds() })
//                .filter { idMap -> idMap.containsKey("trakt") }
//                .map<String> { idMap -> idMap["trakt"] }
//                .collect<List<String>, Any>(toList<String>())
//    }
//
//    private fun saveJob() {
//        val job = jobRepository!!.save(UpdateJob()
//                .finishTime(ZonedDateTime.now())
//                .success(true))
//        log.debug("Saved job: " + job)
//    }
}
