abstract class ONG (nome: String, categoria: String, pais: String, url: String) {
    abstract fun doacao(quantia: Double);
}

class OngsPortuguesas(nome: String, categoria: String, pais: String, url: String): ONG(nome, categoria, pais, url) {
    override fun doacao(quantia: Double) {
        TODO("Not yet implemented")
    }

}

class OngsBrasileiras(nome: String, categoria: String, pais: String, url: String): ONG(nome, categoria, pais, url) {
    override fun doacao(quantia: Double) {
        TODO("Not yet implemented")
    }

}