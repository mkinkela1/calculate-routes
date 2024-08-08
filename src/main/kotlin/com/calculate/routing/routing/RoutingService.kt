package com.calculate.routing.routing

interface RoutingService {
    fun calculateRoute(origin: String, destination: String): List<String>
}