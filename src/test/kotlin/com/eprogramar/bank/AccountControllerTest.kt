package com.eprogramar.bank

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

@SpringBootTest
@AutoConfigureMockMvc
class ONGControllerTest {

    @Autowired lateinit var mockMvc: MockMvc

    @Autowired lateinit var ONGRepository: ONGRepository

    @Test
    fun `test find all`() {
        var csvFile = CSVFile("src/main/kotlin/com/eprogramar/bank/ongs.csv")
        var lista = csvFile.retornaMutable()
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
        var csvFile = CSVFile("src/main/kotlin/com/eprogramar/bank/ongs.csv")
        var lista = csvFile.retornaMutable()
        ONGRepository.saveAll(lista)
//        val ONG = ONGRepository.save(ONG(name = "Test", category = "123", url = "987654321"))

        mockMvc.perform(MockMvcRequestBuilders.get("/ONGs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk)
//                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(ONG.id))
//                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(ONG.name))
//                .andExpect(MockMvcResultMatchers.jsonPath("\$.category").value(ONG.category))
//                .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value(ONG.url))
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test create all ONGs`() {
        var csvFile = CSVFile("src/main/kotlin/com/eprogramar/bank/ongs.csv")
        var lista = csvFile.retornaMutable()
        val json = ObjectMapper().writeValueAsString(lista)
        ONGRepository.deleteAll()
        mockMvc.perform(MockMvcRequestBuilders.post("/ONGs/all")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(ONGRepository.findAll().isEmpty())
    }

    @Test
    fun `test create ONG`() {
        val ONG = ONG(name = "ONGTeste", category = "Meio Ambiente", url = "987654321")
        val json = ObjectMapper().writeValueAsString(ONG)
        ONGRepository.deleteAll()
        mockMvc.perform(MockMvcRequestBuilders.post("/ONGs")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(ONG.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.category").value(ONG.category))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value(ONG.url))
                .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(ONGRepository.findAll().isEmpty())
    }

    @Test
    fun `test update ONG`() {
        var csvFile = CSVFile("src/main/kotlin/com/eprogramar/bank/ongs.csv")
        var lista = csvFile.retornaMutable()
        ONGRepository.saveAll(lista)

//        val ONG = ONGRepository
//                .save(ONG(name = "Test", category = "123", url = "987654321"))
//                .copy(name = "Updated")
        val json = ObjectMapper().writeValueAsString(lista)
        mockMvc.perform(MockMvcRequestBuilders.put("/ONGs/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)
//                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(ONG.name))
//                .andExpect(MockMvcResultMatchers.jsonPath("\$.category").value(ONG.category))
//                .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value(ONG.url))
                .andDo(MockMvcResultHandlers.print())

        val findById = ONGRepository.findById(1!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals("teste Update", findById.get().name)
    }

    @Test
    fun `test delete ONG`() {
        val ONG = ONGRepository
                .save(ONG(name = "Test", category = "123", url = "987654321"))
        mockMvc.perform(MockMvcRequestBuilders.delete("/ONGs/${ONG.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val findById = ONGRepository.findById(ONG.id!!)
        Assertions.assertFalse(findById.isPresent)
    }

}