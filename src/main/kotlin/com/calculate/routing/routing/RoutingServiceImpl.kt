package com.calculate.routing.routing

import com.calculate.routing.country.Country
import com.calculate.routing.country.CountryService
import com.calculate.routing.routing.dto.DtoCalculateRouteResponse
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.BadRequest
import java.util.*

@Service
class RoutingServiceImpl(
    private val countryService: CountryService
) : RoutingService {
    override fun calculateRoute(origin: String, destination: String): List<String> {
        if (origin == destination) {
            return listOf(origin)
        }

        val queue: Queue<String> = LinkedList()
        val visited = mutableSetOf<String>()
        val distance = mutableMapOf<String, Int>()
        val backtrack = mutableMapOf<String, String> ()

        queue.add(origin)
        distance[origin] = 0
        backtrack[origin] = origin

        while (queue.isNotEmpty()) {
            val currentPosition = queue.poll()
            val currentCountry = countryService.getCountryByCca3(currentPosition) ?: continue
            visited.add(currentPosition)

            for (neighbor in currentCountry.borders) {
                if (visited.contains(neighbor)) continue

                if(!distance.containsKey(neighbor)) {
                    distance[neighbor] = distance[currentPosition]?.plus(1) ?: 1
                    backtrack[neighbor] = currentPosition;
                    queue.add(neighbor)
                }

                if (neighbor == destination) {
                    return getRoute(origin, destination, backtrack)
                }
            }
        }

        throw BadRequestException()
    }

    private fun getRoute(origin: String, destination: String, backtrack: MutableMap<String, String>): List<String> {
        val countries = mutableListOf<String> ()
        var currentCountry = destination

        while (currentCountry != origin) {
            countries.add(currentCountry)
            currentCountry = backtrack[currentCountry] ?: origin
        }

        countries.add(currentCountry)

        return countries.reversed()
    }
}