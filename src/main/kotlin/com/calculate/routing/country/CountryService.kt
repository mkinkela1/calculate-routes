package com.calculate.routing.country

interface CountryService {
    fun getCountryByCca3(cca3: String): Country?
    fun getCountryMap(): Map<String, Country>
}