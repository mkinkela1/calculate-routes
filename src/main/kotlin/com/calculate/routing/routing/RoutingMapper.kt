package com.calculate.routing.routing

import com.calculate.routing.routing.dto.DtoCalculateRouteResponse
import org.springframework.stereotype.Component

@Component
class RoutingMapper {
    fun toCalculateRouteDto(countries: List<String>): DtoCalculateRouteResponse {
        return DtoCalculateRouteResponse(countries)
    }
}