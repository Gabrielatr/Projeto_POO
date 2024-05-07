package com.DoacaoSemFronteiras

import java.io.File
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

enum class Category(val descricao: String) {
        EDUCAÇÃO("Educação"),
        SAÚDE("Saúde"),
        MEIO_AMBIENTE("Meio Ambiente"),
        SOCIAL("Social"),
        CRIANÇA("Criança"),
        VIOLÊNCIA("Violência"),
        DEFICIÊNCIA("Deficiência"),
        SEGURANÇA_ALIMENTAR("Segurança alimentar"),
        CULTURA_E_ARTE("Cultura e Arte"),
        MULHER("Mulher");

        companion object {
                fun fromDescricao(descricao: String): Category{
                        return values().first { it.descricao == descricao }
                }
        }
}

@Entity(name = "ONG")
data class ONG(
        @Id @GeneratedValue
        var id: Long? = null,
        val name: String,
        val category: Category,
        val country: String,
        val url: String
)
