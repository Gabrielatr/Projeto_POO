package com.DoacaoSemFronteiras

import org.springframework.data.jpa.repository.JpaRepository

interface ONGRepository : JpaRepository<ONG, Long> {
}