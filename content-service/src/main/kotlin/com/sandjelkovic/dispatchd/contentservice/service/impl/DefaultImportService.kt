package com.sandjelkovic.dispatchd.contentservice.service.impl

import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.contentservice.service.ImportService
import org.springframework.web.util.UriComponents

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
class DefaultImportService : ImportService {
    override fun importFromUriComponents(uriComponents: UriComponents): ImportStatus {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // change parameter to not be dependent on Spring, especially web based UriComponents
    }

    override fun getImportStatus(id: Long): ImportStatus {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
