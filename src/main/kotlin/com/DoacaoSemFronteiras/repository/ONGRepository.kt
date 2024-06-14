package com.DoacaoSemFronteiras.repository

import com.DoacaoSemFronteiras.model.ONG
import org.springframework.data.jpa.repository.JpaRepository

interface ONGRepository : JpaRepository<ONG, Long>

