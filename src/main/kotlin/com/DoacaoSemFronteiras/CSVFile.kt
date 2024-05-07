package com.DoacaoSemFronteiras

import java.io.File

class CSVFile(val path: String) {
    val ongs = mutableListOf<ONG>()

    init{
        try{
            readFile(File(path))
        }catch (e: Exception){
            println("Erro ao ler o ficheiro. Por favor verifique se o caminho está correto.")
            println(e.message)
        }
    }

    fun readFile(csvFile: File){
        csvFile.forEachLine { line ->
            val props = line.split(",");
            val id = props[0].trim().toLong()
            val pais = props[3].trim()
            val category = Category.fromDescricao(props[2].trim())
            val ong = ONG(id, props[1], category, pais, props[4])

            when (pais) {
                "Portugal", "Brasil" -> ongs.add(ong)
            }
        }
    }

}
