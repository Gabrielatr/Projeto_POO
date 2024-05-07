package com.DoacaoSemFronteiras

import com.fasterxml.jackson.databind.ObjectMapper
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

@SpringBootTest
@AutoConfigureMockMvc
class ONGControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var ONGRepository: ONGRepository



    @Test
    fun `test find all`() {
        var csvFile = CSVFile("src/main/kotlin/com/DoacaoSemFronteiras/ongs.csv")
        var lista = csvFile.ongs
        ONGRepository.saveAll(lista)

        mockMvc.perform(MockMvcRequestBuilders.get("/ONGs"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].category").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].url").isString)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find by id`() {
        val csvFile = CSVFile("src/main/kotlin/com/DoacaoSemFronteiras/ongs.csv")
        val lista = csvFile.ongs
        ONGRepository.saveAll(lista)
        val ONG = ONGRepository.findById(1).get()

        mockMvc.perform(MockMvcRequestBuilders.get("/ONGs/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(ONG.id))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(ONG.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.category").value(ONG.category.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value(ONG.url))
            .andDo(MockMvcResultHandlers.print())

        val finById = ONGRepository.findById(1)
    }
    @Test
    fun `test find category`() {
        var csvFile = CSVFile("src/main/kotlin/com/DoacaoSemFronteiras/ongs.csv")
        var lista = csvFile.ongs
        ONGRepository.saveAll(lista)
        val category = Category.valueOf("EDUCAÇÃO").toString().trim()

        mockMvc.perform(MockMvcRequestBuilders.get("/ONGs/category/${category}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].category").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].url").isString)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test create all ONGs`() {
        var csvFile = CSVFile("src/main/kotlin/com/DoacaoSemFronteiras/ongs.csv")
        var lista = csvFile.ongs
        val json = ObjectMapper().writeValueAsString(lista)
        ONGRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/ONGs/all")
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
        val category = Category.valueOf("MEIO_AMBIENTE")
        val ONG = ONG(name = "ONGTeste", category = category, url = "987654321")
        val json = ObjectMapper().writeValueAsString(ONG)
        ONGRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/ONGs")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(ONG.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.category").value(ONG.category.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value(ONG.url))
            .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(ONGRepository.findAll().isEmpty())
    }

    @Test
    fun `test update ONG`() {
        val csvFile = CSVFile("src/main/kotlin/com/DoacaoSemFronteiras/ongs.csv")
        val lista = csvFile.ongs
        ONGRepository.saveAll(lista)

        val newONG = ONGRepository.findById(1).get()
            .copy(name = "NovoNome", url = "https://localhost:8080")

        val json = ObjectMapper().writeValueAsString(newONG)
        mockMvc.perform(
            MockMvcRequestBuilders.put("/ONGs/${newONG.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(newONG.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.category").value(newONG.category.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value(newONG.url))
            .andDo(MockMvcResultHandlers.print())

        val findById = ONGRepository.findById(newONG.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals("NovoNome", findById.get().name)
    }

    @Test
    fun `test delete ONG`() {

        val deleteONG =
            ONGRepository.save(ONG(name = "ONGTeste", category = Category.valueOf("MEIO_AMBIENTE"), url = "987654321"))


        mockMvc.perform(
            MockMvcRequestBuilders.delete("/ONGs/${deleteONG.id}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

        val findById = ONGRepository.findById(deleteONG.id!!)
        Assertions.assertFalse(findById.isPresent)

    }

}
