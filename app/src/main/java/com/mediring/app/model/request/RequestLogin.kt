package com.mediring.app.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestLogin(
        val password: String,
        val loginId: String
)