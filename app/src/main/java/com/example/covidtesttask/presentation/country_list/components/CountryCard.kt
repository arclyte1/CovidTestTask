package com.example.covidtesttask.presentation.country_list.components

import android.icu.text.CompactDecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covidtesttask.R
import com.example.covidtesttask.presentation.country_list.model.CountryItem
import com.murgupluoglu.flagkit.FlagKit


@Composable
fun CountryCard(
    country: CountryItem,
    modifier: Modifier = Modifier
) {
    var drawableId = FlagKit.getResId(
        context = LocalContext.current,
        flagName = country.code
    )
    if (drawableId == 0) drawableId = R.drawable.unknown_flag
    Card(
        modifier = modifier
    ) {
        Column {
            Image(
                painter = painterResource(
                    id = drawableId
                ),
                contentDescription = "Flag",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = country.displayName,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
            CountryCardStatistic(
                title = "Confirmed",
                total = country.totalConfirmed,
                new = country.newConfirmed,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
            CountryCardStatistic(
                title = "Recovered",
                total = country.totalRecovered,
                new = country.newRecovered,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
            CountryCardStatistic(
                title = "Deaths",
                total = country.totalDeaths,
                new = country.newDeaths,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
fun CountryCardStatistic(
    title: String,
    total: Int,
    new: Int,
    modifier: Modifier = Modifier,
) {
    val numberFormat = CompactDecimalFormat.getInstance(
        LocalConfiguration.current.locales[0],
        CompactDecimalFormat.CompactStyle.SHORT
    )
    Row(
        modifier = modifier
    ) {
        Text(text = title, fontSize = 14.sp)
        Text(
            text = "${numberFormat.format(total)} (+${numberFormat.format(new)})",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            fontSize = 14.sp
        )
    }
}