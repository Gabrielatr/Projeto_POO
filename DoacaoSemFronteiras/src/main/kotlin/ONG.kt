
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

class ONG(val nome: String, val categoria: String, val pais: String, val url: String): IONG{
    override fun doacao(quantia: Double) {
        TODO("Not yet implemented")
    }

    override fun atualizarDados() {
        TODO("Not yet implemented")
    }

}