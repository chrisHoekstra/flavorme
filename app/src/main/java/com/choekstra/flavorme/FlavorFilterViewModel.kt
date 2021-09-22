package com.choekstra.flavorme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlavorFilterViewModel : ViewModel() {

    val liveData = MutableLiveData(initialViewState())

    private fun initialViewState() = FlavorFilterViewState(
        flavors = allFlavors,
        flavorFilters = initialFlavorFilters
    )

    fun filterChecked(isChecked: Boolean, checkedFilter: FlavorFilter) {
        liveData.value?.let { currentState ->
            val noneOfTheAboveWasChecked = checkedFilter.isNoneOfTheAbove() && isChecked

            val newFilters = currentState.flavorFilters.map { filter ->
                if (checkedFilter.tag == filter.tag) {
                    filter.copy(
                        checked = isChecked
                    )
                } else {
                    filter.copy(
                        enabled = !noneOfTheAboveWasChecked
                    )
                }
            }

            val checkedFilterTags = newFilters
                .filter { filter -> filter.checked }
                .map { filter -> filter.tag }

            val filteredFlavors = allFlavors.filter { flavor ->
                if (noneOfTheAboveWasChecked) {
                    flavor.tags.isEmpty()
                } else {
                    flavor.tags.containsAll(checkedFilterTags)
                }
            }

            liveData.value = currentState.copy(
                flavorFilters = newFilters,
                flavors = filteredFlavors
            )
        }
    }
}

fun FlavorFilter.isNoneOfTheAbove() = tag == null
