package com.DoacaoSemFronteiras

import java.io.File
import java.io.IOException

class CSVFile(val path: String) {
    val ongs = mutableListOf<ONG>()

    init{
        try {
            readFile(File(path))
        }catch (e: IOException){
            println(e.message)
        }catch (e: Exception){
            println(e.message)
        }
    }

    fun readFile(csvFile: File){
//        csvFile.forEachLine { line ->
//            val props = line.split(",");
//            val id = props[0].trim().toLong()
//            val pais = props[3].trim()
//            val category = Category.valueOf(props[2].trim().toUpperCase().replace(" ", "_"))
//            val ong = ONG(id, props[1], category, pais, props[4])
//
//            when (pais) {
//                "Portugal", "Brasil" -> ongs.add(ong)
//            }
//        }
    }

}
