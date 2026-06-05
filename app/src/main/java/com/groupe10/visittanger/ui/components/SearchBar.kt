package com.groupe10.visittanger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.groupe10.visittanger.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TangerSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Search the white city..."
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { 
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge,
                color = StitchOutline
            ) 
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = StitchOutline
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = StitchOutline
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .border(1.dp, StitchSurfaceVariant, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = StitchSurfaceContainerLow,
            unfocusedContainerColor = StitchSurfaceContainerLow,
            disabledContainerColor = StitchSurfaceContainerLow,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = StitchPrimary
        ),
        textStyle = MaterialTheme.typography.bodyLarge,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun TangerSearchBarPreview() {
    TangerSearchBar(query = "", onQueryChange = {})
}
