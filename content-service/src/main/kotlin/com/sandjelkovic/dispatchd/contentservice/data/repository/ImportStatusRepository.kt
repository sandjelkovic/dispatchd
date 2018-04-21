package com.sandjelkovic.dispatchd.contentservice.data.repository

import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */
interface ImportStatusRepository : PagingAndSortingRepository<ImportStatus, Long>
