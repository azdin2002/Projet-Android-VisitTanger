package com.groupe10.visittanger.ui.language

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.groupe10.visittanger.R
import com.groupe10.visittanger.ui.theme.TangerGreen

@Composable
fun LanguageSelectorDialog(
    currentLang: String,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf("fr", "en", "ar")
    var selected by remember { mutableStateOf(currentLang) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.lang_select_title))
        },
        text = {
            Column {
                languages.forEach { code ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selected = code }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = LanguageManager.getLanguageFlag(code),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = LanguageManager.getLanguageNativeName(code),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        RadioButton(
                            selected = selected == code,
                            onClick = { selected = code },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = TangerGreen
                            )
                        )
                    }
                    if (code != "ar") HorizontalDivider()
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onLanguageSelected(selected) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = TangerGreen
                )
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
