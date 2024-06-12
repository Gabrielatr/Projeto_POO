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

        companion object {
                fun fromDescricao(descricao: String): Category?
                {
                        return values().firstOrNull { it.descricao == descricao }
                }
        }
}


@Entity
data class ONG  (
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
        val id: Integer,
        @JsonProperty("name")
        val name: String,
        @JsonProperty("category")
        val category: String,
        @JsonProperty("country")
        val country: String,
        @JsonProperty("url")
        val url: String = ""
){
        init{
                require(name.isNotBlank()) {"O nome não pode estar vazio"}
                require(Category.fromDescricao(category) != null) {"A categoria está errada. Não existe no nosso sistema."}
                require(country == "Brasil" || country == "Portugal") {"Somente aceitamos instituições do Brasil ou de Portugal no momento"}
        }
}
