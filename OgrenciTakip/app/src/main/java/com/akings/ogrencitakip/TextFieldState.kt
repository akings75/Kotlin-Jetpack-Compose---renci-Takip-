package com.akings.ogrencitakip

data class TextFieldState(val isLoading: Boolean = false,
                               val book: Book? = null,
                               val error: String = "")