package com.sandjelkovic.dispatchd.contentservice.interceptor

import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.support.HttpRequestWrapper
import java.io.IOException

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
class HeaderRequestInterceptor(private val headerName: String, private val headerValue: String) : ClientHttpRequestInterceptor {

    @Throws(IOException::class)
    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        val wrapper = HttpRequestWrapper(request)
        wrapper.headers.add(headerName, headerValue)
        return execution.execute(wrapper, body)
    }
}
