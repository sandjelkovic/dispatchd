package com.sandjelkovic.dispatchd.contentservice.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportProgressStatus
import com.sandjelkovic.dispatchd.contentservice.web.dto.ImportDto
import com.sandjelkovic.dispatchd.contentservice.web.dto.ImportStatusWebDto
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.Resource
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

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
        val importRequestDto = ImportDto(mediaUrl = mediaUrl)
        mockMvc.perform(
                post("/import")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jacksonMapper.writeValueAsString(importRequestDto))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isAccepted)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.mediaUrl").value(mediaUrl))
                .andExpect(jsonPath("\$..statusId").isNotEmpty)
                .andExpect(jsonPath("\$.status").value(ImportProgressStatus.QUEUED.toString()))
                // TODO Hateoas URLs. Location header.
                // write out Response
                .andReturn().response.contentAsString.also { println(it) }
    }

    @Test
    fun `Should fail on invalid URL`() {
        val importRequestDto = ImportDto(mediaUrl = "http::///something.net")
        mockMvc.perform(
                post("/import")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jacksonMapper.writeValueAsString(importRequestDto))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `Should fail on not supported backend`() {
        val importRequestDto = ImportDto(mediaUrl = "https://www.imdb.com/title/tt0092455/?ref_=nv_sr_1")
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

    @Test
    fun `Should create a new Import and read it's status`() {
        val importRequestDto = ImportDto(mediaUrl = mediaUrl)
        val importStatusResponse = mockMvc.perform(
                post("/import")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jacksonMapper.writeValueAsString(importRequestDto))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isAccepted)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.mediaUrl").value(mediaUrl))
                .andExpect(jsonPath("\$.statusId").isNotEmpty)
                .andExpect(jsonPath("\$.status").value(ImportProgressStatus.QUEUED.toString()))
                .andReturn().response.contentAsString.let { jacksonMapper.readValue<Resource<ImportStatusWebDto>>(it) }

        mockMvc.perform(
                get("/import/${importStatusResponse.content.statusId}")  // todo refactor to use link.self
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.mediaUrl").value(mediaUrl))
                .andExpect(jsonPath("\$.statusId").isNotEmpty)
                .andExpect(jsonPath("\$.status").value(ImportProgressStatus.QUEUED.toString()))
    }
}
