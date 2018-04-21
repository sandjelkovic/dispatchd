package com.sandjelkovic.dispatchd.contentservice.data.entity

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */
enum class ImportProgressStatus(val field: String) {
    QUEUED("QUEUED"),
    IN_PROGRESS("IN_PROGRESS"),
    SUCCESS("SUCCESS"),
    ERROR_SHOW_ALREADY_EXISTS("ERROR_SHOW_ALREADY_EXISTS"),
    ERROR_REMOTE_SERVER("ERROR_REMOTE_SERVER"),
    ERROR("ERROR");
}
