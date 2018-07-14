package com.sandjelkovic.dispatchd.contentservice.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportProgressStatus
import com.sandjelkovic.dispatchd.contentservice.web.dto.ImportRequestDto
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author sandjelkovic
 * @date 8.7.18.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ImportControllerIntegrationTest {
    companion object {
        const val mediaUrl = "https://trakt.tv/shows/star-trek-the-next-generation"
    }

    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var jacksonMapper: ObjectMapper

    @Test
    fun `Should start import and return status`() {
        val importRequestDto = ImportRequestDto(mediaUrl = mediaUrl)
        mockMvc.perform(
                post("/import")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jacksonMapper.writeValueAsString(importRequestDto))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isAccepted)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.mediaUrl").value(mediaUrl))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(ImportProgressStatus.QUEUED.toString()))
                // TODO Hateoas URLs. Location header.
                // write out Response
                .andReturn().response.contentAsString.also { println(it) }
    }

    @Test
    fun `Should fail on invalid URL`() {
        val importRequestDto = ImportRequestDto(mediaUrl = "http::///something.net")
        mockMvc.perform(
                post("/import")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jacksonMapper.writeValueAsString(importRequestDto))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `Should fail on not supported backend`() {
        val importRequestDto = ImportRequestDto(mediaUrl = "https://www.imdb.com/title/tt0092455/?ref_=nv_sr_1")
        mockMvc.perform(
                post("/import")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jacksonMapper.writeValueAsString(importRequestDto))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `Should fail on empty request`() {
        mockMvc.perform(
                post("/import")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest)
    }
}
