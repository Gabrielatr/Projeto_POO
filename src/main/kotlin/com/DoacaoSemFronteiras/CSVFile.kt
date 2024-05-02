package com.DoacaoSemFronteiras

import java.io.File

class CSVFile(val path: String) {
    val ongs = mutableListOf<ONG>()

    fun retornaMutable() : MutableList<ONG> {
        val csvFile = File(path);
        csvFile.forEachLine { line ->
            val props = line.split(",");
            val id = props[0].trim().toLong()
            val pais = props[3].trim()
            val ong = ONG(id, props[1], props[2], props[4])

            when (pais) {
                "Portugal", "Brasil" -> ongs.add(ong)
            }
        }
        return ongs
    }
}