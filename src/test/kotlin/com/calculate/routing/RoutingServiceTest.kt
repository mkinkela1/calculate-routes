package com.calculate.routing

import com.calculate.routing.country.Country
import com.calculate.routing.country.CountryService
import com.calculate.routing.routing.RoutingService
import org.apache.coyote.BadRequestException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RoutingServiceImplIntegrationTest @Autowired constructor(
    private val routingService: RoutingService
) {

    @Test
    fun `should calculate correct route between two countries`() {
        // Arrange
        val origin = "CZE"
        val destination = "ITA"
        val expectedRoute = listOf("CZE", "AUT", "ITA")

        // Act
        val result = routingService.calculateRoute(origin, destination)

        // Assert
        assertEquals(expectedRoute, result)
    }

    @Test
    fun `should handle cases with no route found`() {
        // Arrange
        val origin = "CZE"
        val destination = "XXX" // Example of a non-existent route

        // Act & Assert
        try {
            routingService.calculateRoute(origin, destination)
        } catch (e: Exception) {
            assert(e is BadRequestException)
        }
    }
}