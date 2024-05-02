package com.DoacaoSemFronteiras

import java.io.File
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "ONG")
data class ONG(
        @Id @GeneratedValue
        var id: Long? = null,
        val name: String,
        val category: String,
        val url: String
)