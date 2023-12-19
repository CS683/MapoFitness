package com.example.mapofitness.common.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.mapofitness.theme.Dark
import com.example.mapofitness.theme.DarkGray
import com.example.mapofitness.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onQueryChanged: (String) -> Unit, searchQuery: String) {
    TextField(
        value = searchQuery,
        onValueChange = onQueryChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = DarkGray,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text("Search activities") },
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Dark, // Set your desired background color here
            // You can also customize other parts like textColor, cursorColor etc.
            cursorColor = White,
            focusedIndicatorColor = Color.Transparent, // Hide the underline when focused
            unfocusedIndicatorColor = Color.Transparent // Hide the underline when not focused
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
    )
}
