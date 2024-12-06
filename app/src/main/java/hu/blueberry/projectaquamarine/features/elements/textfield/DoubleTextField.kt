package hu.blueberry.projectaquamarine.features.elements.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DoubleTextField(
    startValue: Double,
    setValue: (Double) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    var value by rememberSaveable { mutableStateOf(startValue.toString()) }
    var isError by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = { newText ->
            value = newText
            try {
                setValue(newText.toDouble())
                isError = false
            } catch (_: NumberFormatException) {
                isError = true
            }
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Only a valid format of a Double is accepted!",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (isError)
                Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
        },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier,
        leadingIcon = leadingIcon,
        singleLine = true,
    )
}

@Preview
@Composable
fun DoubleTextFieldPreview() {
    var number by rememberSaveable { mutableDoubleStateOf(2.0) }
    DoubleTextField(number, { number = it }, "Text", leadingIcon = { Icon(Icons.Filled.ShoppingCart, "Change", tint = MaterialTheme.colorScheme.primary) })
}