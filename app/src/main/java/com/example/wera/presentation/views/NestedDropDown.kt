package com.example.wera.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text


@Composable
fun DependentDropdowns() {
    var selectedCountry by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("") }

    // Define lists of countries and cities
    val countries = listOf("Select a country", "USA", "Canada", "UK")
    val citiesByCountry = mapOf(
        "USA" to listOf("Select a city", "New York", "Los Angeles", "Chicago"),
        "Canada" to listOf("Select a city", "Toronto", "Vancouver", "Montreal"),
        "UK" to listOf("Select a city", "London", "Manchester", "Edinburgh")
    )

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // First Dropdown for Countries
        Dropdown(
            items = countries,
            selectedItem = selectedCountry,
            onItemSelected = { newCountry ->
                selectedCountry = newCountry
                // Reset the selected city when changing the country
                selectedCity = ""
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Second Dropdown for Cities, dependent on the selected country
        Dropdown(
            items = citiesByCountry[selectedCountry] ?: listOf("Select a city"),
            selectedItem = selectedCity,
            onItemSelected = { newCity ->
                selectedCity = newCity
            }
        )
    }
}

@Composable
fun Dropdown(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        BasicTextField(
            value = selectedItem,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            readOnly = true
        )

        if (expanded) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {item}, onClick = {
                            onItemSelected(item)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

