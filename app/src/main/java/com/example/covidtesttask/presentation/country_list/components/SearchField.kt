package com.example.covidtesttask.presentation.country_list.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun SearchField(
    searchText: String,
    performSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = { text ->
            performSearch(text)
        },
        shape = RoundedCornerShape(50),
        placeholder = {
            Text(text = "Search...", color = Color.Gray)
        },
        modifier = modifier
    )
}
