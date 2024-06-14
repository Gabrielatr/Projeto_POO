package com.DoacaoSemFronteiras

import com.DoacaoSemFronteiras.external_data.CSVFile
import com.DoacaoSemFronteiras.model.ONG
import com.DoacaoSemFronteiras.repository.ONGRepository
import com.DoacaoSemFronteiras.service.IONGService
import com.DoacaoSemFronteiras.service.ONGService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.io.IOException
import org.junit.jupiter.api.BeforeEach
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ONGControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var ONGRepository: ONGRepository


    @BeforeEach
    fun setUp() {
        val csvFile = CSVFile("C:\\Users\\conta\\OneDrive\\Ambiente de Trabalho\\dsfproject\\projetoPOO\\Projeto_POO\\src\\main\\kotlin\\com\\DoacaoSemFronteiras\\external data\\ongs.csv")
        val lista = csvFile.ongs
        ONGRepository.saveAll(lista)
    }

    @AfterEach
    fun tearDown() {
        ONGRepository.deleteAll()
    }



    @Test
    fun `test find all`() {
        var csvFile = CSVFile("src/main/kotlin/com/DoacaoSemFronteiras/external_data/ongs.csv")
        var lista = csvFile.ongs
        ONGRepository.saveAll(lista)

        mockMvc.perform(MockMvcRequestBuilders.get("/ongs"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].category").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].url").isString)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find by id`() {
        val csvFile = CSVFile("C:\\Users\\conta\\OneDrive\\Ambiente de Trabalho\\dsfproject\\POOprojeto\\Projeto_POO\\src\\main\\kotlin\\com\\DoacaoSemFronteiras\\external data\\ongs.csv")
        val lista = csvFile.ongs
        ONGRepository.saveAll(lista)
        val ONG = ONGRepository.findById(1).orElseThrow()

        mockMvc.perform(MockMvcRequestBuilders.get("/ongs/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(ONG.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.category").value(ONG.category))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value(ONG.url))
            .andDo(MockMvcResultHandlers.print())

        val finById = ONGRepository.findById(1)
    }
    @Test
    fun `test find by category`() {
        var csvFile = CSVFile("src/main/kotlin/com/DoacaoSemFronteiras/external_data/ongs.csv")
        var lista = csvFile.ongs
        ONGRepository.saveAll(lista)
        val category = "Meio_Ambiente"

        mockMvc.perform(MockMvcRequestBuilders.get("/ongs/${category}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find by Brasil`() {
        val csvFile = CSVFile("src/main/kotlin/com/DoacaoSemFronteiras/external_data/ongs.csv")
        val lista = csvFile.ongs
        ONGRepository.saveAll(lista)

        mockMvc.perform(MockMvcRequestBuilders.get("/ongs/Brasil"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }
    @Test
    fun `test find by Portugal`() {
        val csvFile = CSVFile("src/main/kotlin/com/DoacaoSemFronteiras/external_data/ongs.csv")
        val lista = csvFile.ongs
        ONGRepository.saveAll(lista)

        mockMvc.perform(MockMvcRequestBuilders.get("/ongs/Portugal"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test create all ONGs`() {
        var csvFile = CSVFile("C:\\Users\\conta\\OneDrive\\Ambiente de Trabalho\\dsfproject\\POOprojeto\\Projeto_POO\\src\\main\\kotlin\\com\\DoacaoSemFronteiras\\external data\\ongs.csv")
        var lista = csvFile.ongs
        val json = ObjectMapper().writeValueAsString(lista)
        ONGRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/ongs/all")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(ONGRepository.findAll().isEmpty())
    }

    @Test
    fun `test create ONG`() {
        val category = "Meio Ambiente"
        val ONG = ONG(id= 1,name = "ONGTeste", category = category, country = "Brasil", url = "987654321")
        val json = ObjectMapper().writeValueAsString(ONG)
        ONGRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/ongs")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(ONG.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.category").value(ONG.category))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value(ONG.url))
            .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(ONGRepository.findAll().isEmpty())
    }

    @Test
    fun `test update ONG`() {
        val csvFile = CSVFile("C:\\Users\\conta\\OneDrive\\Ambiente de Trabalho\\dsfproject\\POOprojeto\\Projeto_POO\\src\\main\\kotlin\\com\\DoacaoSemFronteiras\\external data\\ongs.csv")
        val lista = csvFile.ongs
        ONGRepository.saveAll(lista)

        val newONG = ONGRepository.findById(1).get()
            .copy(name = "NovoNome", url = "https://localhost:8080")

        val json = ObjectMapper().writeValueAsString(newONG)
        mockMvc.perform(
            MockMvcRequestBuilders.put("/ongs/${newONG.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(newONG.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.category").value(newONG.category))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value(newONG.url))
            .andDo(MockMvcResultHandlers.print())

        val findById = ONGRepository.findById(newONG.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals("NovoNome", findById.get().name)
    }

    @Test
    fun `test delete ONG`() {
        val deleteONG =
            ONGRepository.save(ONG(id= 1,name = "ONGTeste", category = "Meio Ambiente", country = "Brasil", url = "987654321"))


        mockMvc.perform(
            MockMvcRequestBuilders.delete("/ongs/${deleteONG.id}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

        val findById = ONGRepository.findById(deleteONG.id!!)
        Assertions.assertFalse(findById.isPresent)

    }

}
