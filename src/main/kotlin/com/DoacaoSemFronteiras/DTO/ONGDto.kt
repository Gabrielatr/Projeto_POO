package com.DoacaoSemFronteiras.DTO

import com.DoacaoSemFronteiras.model.ONG

data class ONGDto  (
    val name: String,
    val category: String,
    val country: String,
    val url: String = ""
)

fun ONGDto.toEntity(): ONG = ONG(
    name = this.name,
    category = this.category,
    country = this.country,
    url = this.url
)