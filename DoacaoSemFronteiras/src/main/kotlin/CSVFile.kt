import java.io.File

class CSVFile (caminho: String) {

    val ongs = mutableMapOf<String, MutableList<ONG>>()

    init {
        val csvFile = File(caminho);
        csvFile.forEachLine { line ->
            val props = line.split(",");
            if (props.size > 3 ) {
                val id = props[0].trim()
                val pais = props[3].trim()
                val ong = ONG(props[1], props[2], props[4])

                when (pais) {
                    "Portugal", "Brasil" ->
                        if (ongs.containsKey(pais)) {
                            val lista = ongs[pais]
                            lista?.add(ong)
                        }
                }
            }else {
                println("Linha inválida: ${Exception(line)}")
            }

        }

    }

}

