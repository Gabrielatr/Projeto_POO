import java.io.File

class CSVFile (caminho: String) {

    val ongs = mutableMapOf<String, List<ONG>>()

    init {
        val csvFile = File(caminho);
        csvFile.forEachLine { line ->
            val props = line.split(",");
            val id = props[0];
            when (props[3]) {
                "Portugal" ->
                    TODO("Not yet implemented")
                "Brasil" ->
                    TODO("Not yet implemented")
            }
        }
    }
}

