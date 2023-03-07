package com.example.covidtesttask.presentation.country_details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CasesRow(
    title: String,
    total: Int,
    new: Int
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 8.dp)) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$total (+$new)",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            fontSize = 16.sp
        )
    }
}