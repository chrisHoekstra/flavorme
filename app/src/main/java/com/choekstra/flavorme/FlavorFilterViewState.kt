package com.choekstra.flavorme

import com.choekstra.flavorme.FlavorTag.*

data class FlavorFilterViewState(
    val flavors: List<Flavor>,
    val flavorFilters: List<FlavorFilter>,
)

data class Flavor(
    val name: String,
    val tags: Set<FlavorTag>,
)

data class FlavorFilter(
    val name: String,
    val tag: FlavorTag?,
    val checked: Boolean,
    val enabled: Boolean,
)

enum class FlavorTag {
    Chocolate,
    Coffee,
    Fruit,
}

val allFlavors = listOf(
    Flavor("Vanilla", setOf()),
    Flavor("Chocolate", setOf(Chocolate)),
    Flavor("Strawberry", setOf(Fruit)),
    Flavor("Blueberry", setOf(Fruit)),
    Flavor("Cherry Garcia", setOf(Chocolate, Fruit)),
    Flavor("Mint Chocolate Chip", setOf(Chocolate)),
    Flavor("Chocolate Covered Strawberries", setOf(Chocolate, Fruit)),
    Flavor("Coffee Espresso", setOf(Coffee)),
    Flavor("Chocolate Coffee Espresso", setOf(Chocolate, Coffee)),
    Flavor("Chocolate Raspberry Coffee Espresso", setOf(Chocolate, Coffee, Fruit)),
    Flavor("Vanilla Bean Coffee Espresso", setOf(Coffee)),
    Flavor("Lemoncello", setOf(Fruit)),
    Flavor("Salted Caramel", setOf()),
)

val initialFlavorFilters = listOf(
    FlavorFilter(
        name = "Chocolate",
        tag = Chocolate,
        checked = false,
        enabled = true,
    ),
    FlavorFilter(
        name = "Coffee",
        tag = Coffee,
        checked = false,
        enabled = true,
    ),
    FlavorFilter(
        name = "Fruit",
        tag = Fruit,
        checked = false,
        enabled = true,
    ),
    FlavorFilter(
        name = "None of the above",
        tag = null,
        checked = false,
        enabled = true,
    ),
)
