package com.mediring.app.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestUser (
        val id: Int?,
        val name: String?,
        val email: String?,
        val password: String?,
        val phone: String?
)