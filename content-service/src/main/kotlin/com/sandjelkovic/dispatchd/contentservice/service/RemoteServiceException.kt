package com.sandjelkovic.dispatchd.contentservice.service

import org.springframework.web.client.HttpClientErrorException

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
class RemoteServiceException(e: HttpClientErrorException) : RuntimeException()
