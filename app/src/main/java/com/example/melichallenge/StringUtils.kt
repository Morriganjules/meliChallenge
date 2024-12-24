package com.example.melichallenge

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun String.encodeString(): String {
    return if (this.endsWith(" ")) {
        this.trimEnd().replace(" ", "%20")
    } else {
        this.replace(" ", "%20")
    }
}

fun String.addCurrency(currency: String): String {
    return when(currency){
        "ARS" -> "$ $this"
        "USD" ->  "USD $this"
        else -> "$ $this"
    }
}

fun Float.formatToLocalizedString(): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        decimalSeparator = ',' // Cambia el separador decimal a coma
        groupingSeparator = '.' // Cambia el separador de miles a punto
    }
    val formatter = DecimalFormat("#,##0.00", symbols)
    return formatter.format(this)
}
