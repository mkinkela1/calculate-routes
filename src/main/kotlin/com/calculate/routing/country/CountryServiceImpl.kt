package com.calculate.routing.country

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import org.springframework.stereotype.Service
import java.net.URI

@Service
class CountryServiceImpl: CountryService {
    private val DATA_URL = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json"
    private val countryMap = mutableMapOf<String, Country>()

    init {
        val mapper = jacksonObjectMapper().apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
        val url = URI.create(DATA_URL).toURL()
        val jsonData = url.readText()
        val countries = mapper.readValue(jsonData, object: TypeReference<List<Country>>() {})

        for(country in countries) {
            countryMap[country.cca3] = country;
        }
    }

    override fun getCountryByCca3(cca3: String): Country? {
        return countryMap[cca3]
    }

    override fun getCountryMap(): Map<String, Country> {
        return countryMap
    }
}