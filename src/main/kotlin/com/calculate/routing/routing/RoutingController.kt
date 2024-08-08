package com.calculate.routing.routing

import com.calculate.routing.routing.dto.DtoCalculateRouteResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/routing")
class RoutingController(
    private val routingService: RoutingService,
    private val mapper: RoutingMapper
) {
    @GetMapping("/{origin}/{destination}")
    fun getRoute(
        @PathVariable("origin") origin: String,
        @PathVariable("destination") destination: String
    ): DtoCalculateRouteResponse {
        val countries =  routingService.calculateRoute(origin, destination)
        return mapper.toCalculateRouteDto(countries)
    }
}