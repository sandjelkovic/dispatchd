package com.sandjelkovic.dispatchd.contentservice.service

import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import org.springframework.web.util.UriComponents
import java.util.*

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
interface ImportService {
    fun importFromUriComponents(uriComponents: UriComponents): ImportStatus

    fun getImportStatus(id: Long): Optional<ImportStatus>
}
