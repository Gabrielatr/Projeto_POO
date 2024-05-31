package com.DoacaoSemFronteiras

import org.springframework.data.jpa.repository.JpaRepository

interface ONGRepository : JpaRepository<ONG, Long> {
//    fun findByCategory(category: Category): List<ONG>
}