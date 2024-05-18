package com.DoacaoSemFronteiras

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema
import org.intellij.lang.annotations.Pattern
import org.jetbrains.annotations.NotNull

import java.io.File
import java.io.Serial
import javax.persistence.*

enum class Category(val descricao: String) {
        Educação("Educação"),
        Saúde("Saúde"),
        Meio_Ambiente("Meio Ambiente"),
        Social("Social"),
        Criança("Criança"),
        Violência("Violência"),
        Deficiência("Deficiência"),
        Segurança_Alimentar("Segurança Alimentar"),
        Cultura_e_Arte("Cultura e Arte"),
        Mulher("Mulher"),
        Segurança("Segurança");



//        companion object {
//                fun fromDescricao(descricao: String): Category{
//                        return values().first { it.descricao == descricao }
//                }
//        }
}


@Entity
data class ONG  (
        @Id @GeneratedValue
        var id: Long = 0,
        @JsonProperty("name")
        val name: String,
        @JsonProperty("category")
        @Enumerated(EnumType.STRING)
        val category: Category,
        @JsonProperty("country")
        val country: String,
        @JsonProperty("url")
        val url: String
)
