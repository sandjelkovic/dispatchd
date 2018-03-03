package com.sandjelkovic.dispatchd.contentservice.data.repository

import com.sandjelkovic.dispatchd.contentservice.data.entity.UpdateJob
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * @author sandjelkovic
 * @date 3.3.18.
 */

interface UpdateJobRepository : CrudRepository<UpdateJob, Long> {
    fun findFirstByOrderByFinishTimeDesc(): Optional<UpdateJob>

    fun findFirstBySuccessOrderByFinishTimeDesc(success: Boolean): Optional<UpdateJob>
}
