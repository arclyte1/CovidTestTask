package com.example.covidtesttask.presentation.country_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.covidtesttask.R
import com.example.covidtesttask.presentation.ui.theme.SearchViewBackground
import com.example.covidtesttask.presentation.ui.theme.SearchViewText


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
        shape = RoundedCornerShape(15),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                color = SearchViewText
            )
        },
        singleLine = true,
        modifier = modifier.background(SearchViewBackground)
    )
}
