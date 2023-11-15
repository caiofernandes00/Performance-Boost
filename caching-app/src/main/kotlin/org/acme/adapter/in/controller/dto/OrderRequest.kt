package org.acme.adapter.`in`.controller.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val name: String
)
