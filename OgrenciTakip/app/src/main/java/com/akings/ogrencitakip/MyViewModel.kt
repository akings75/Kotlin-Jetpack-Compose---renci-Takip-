package com.akings.ogrencitakip

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@SuppressLint("MutableCollectionMutableState", "LogNotTimber")
@HiltViewModel
class MyViewModel
@Inject
constructor(private val repository: BookRepository,
            savedStateHandle: SavedStateHandle) :ViewModel() {

    private val _state: MutableState<TextFieldState> = mutableStateOf(TextFieldState())
    val state: State<TextFieldState>
        get() = _state

    init {
        Log.d("BookDetailViewModel", "SavedStateHandle...")
        savedStateHandle.get<String>("bookId")?.let { bookId ->
            Log.d("BookDetailViewModel", "BookId: $bookId")
            getBook(bookId)
            println("init getBook çağrıldı")

        }
    }

        fun addNewBook(kitap: String, ders: String) {
            val book = Book(
                id = UUID.randomUUID().toString(),
                kitap = kitap,
                ders = ders
            )
            repository.addBook(book)
        }

        fun updateBook(newKitap: String,newDers: String ) {
            if (state.value.book == null) {
                _state.value = TextFieldState(error = "Book is null")
                return
            }
            val bookEdited = state.value.book!!.copy(kitap = newKitap, ders = newDers)
            repository.updateBook1(bookEdited.id, bookEdited)
        }

        private fun getBook(bookId: String) {
            println("MyViewModel getBook çağrıldı")
            repository.getBookId(bookId).onEach { result ->
                when(result) {
                    is Result.Error ->{
                        _state.value = TextFieldState(error = result.message ?: "Unexpected error")
                    }
                    is Result.Loading -> {
                        _state.value = TextFieldState(isLoading = true)
                    }
                    is Result.Success -> {
                        _state.value = TextFieldState(book = result.data)
                    }


                }
            }.launchIn(viewModelScope)


        }

}