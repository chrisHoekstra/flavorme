package com.choekstra.flavorme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.choekstra.flavorme.ui.theme.FlavorMeTheme

class MainActivity : ComponentActivity() {

    private val viewModel: FlavorFilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FlavorMeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Screen(viewModel)
                }
            }
        }
    }
}

@Composable
fun Screen(
    viewModel: FlavorFilterViewModel
) {
    val state = viewModel.liveData.observeAsState(viewModel.liveData.value)

    state.value?.let { currentState ->
        FlavorAndFlavorFilters(currentState) { isChecked, checkedFilter ->
            viewModel.filterChecked(
                isChecked,
                checkedFilter
            )
        }
    }
}

@Composable
fun FlavorAndFlavorFilters(
    viewState: FlavorFilterViewState,
    onFilterChecked: (Boolean, FlavorFilter) -> Unit,
) = LazyColumn {
    items(viewState.flavorFilters, key = { "filter-{$it.name}" }) { filter ->
        FilterRow(filter, onFilterChecked)
    }

    item {
        Divider(
            Modifier.padding(vertical = 8.dp)
        )
    }

    items(viewState.flavors, key = { "flavor-{$it.name}" }) { flavor ->
        FlavorRow(flavor)
    }
}

@Composable
fun FilterRow(
    filter: FlavorFilter,
    onChecked: (Boolean, FlavorFilter) -> Unit,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 8.dp),
    horizontalArrangement = Arrangement.End,
) {
    Text(
        text = filter.name,
        color = when {
            filter.enabled && filter.checked -> MaterialTheme.colors.secondary
            else -> Color.Black
        },
        modifier = Modifier.alpha(alpha = if (filter.enabled) ContentAlpha.high else ContentAlpha.disabled)
    )
    Checkbox(
        checked = filter.checked,
        enabled = filter.enabled,
        onCheckedChange = { checked -> onChecked(checked, filter) }
    )
}

@Composable
fun FlavorRow(
    flavor: Flavor
) = Text(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 8.dp),
    text = flavor.name
)

/*
*
* Previews
*
*/

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlavorMeTheme {
        FlavorAndFlavorFilters(
            FlavorFilterViewState(
                flavors = allFlavors,
                flavorFilters = initialFlavorFilters
            )
        ) { _, _ -> }
    }
}
