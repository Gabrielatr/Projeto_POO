
interface IONG{
    fun doacao(quantia: Double);
    fun atualizarDados();
}

interface IListaONGs{
    fun adicionarOng();
    fun removerOng();
    fun pesquisa(nome: String);
    fun filtroPorPais(pais: String);
    fun filtroPorCategoria(categoria: String);

}

class ListaOngs () : IListaONGs {

    override fun adicionarOng() {
        TODO("Not yet implemented")
    }

    override fun removerOng() {
        TODO("Not yet implemented")
    }

    override fun pesquisa(nome: String) {
        TODO("Not yet implemented")
    }

    override fun filtroPorPais(pais: String) {
        TODO("Not yet implemented")
    }

    override fun filtroPorCategoria(categoria: String) {
        TODO("Not yet implemented")
    }


}

class ONG(val nome: String, val categoria: String, val url: String): IONG{
    override fun doacao(quantia: Double) {
        TODO("Not yet implemented")
    }

    override fun atualizarDados() {
        TODO("Not yet implemented")
    }

}

fun main() {
    val ongcsv = "C:\\Users\\conta\\OneDrive\\Documentos\\dev_\\poo\\project\\Projeto_POO\\DoacaoSemFronteiras\\src\\main\\kotlin\\ongs.csv"
    val arquivo = CSVFile(ongcsv)

    arquivo.ongs.forEach { (pais, ongs) ->
        println("País: $pais")
        ongs.forEachIndexed { index, ong ->
            println("Indexed: ${index + 1}")
            println("Nome: ${ong.nome}")
            println("Categoria: ${ong.categoria}")
            println("URL: ${ong.url}")
        }
    }




}
