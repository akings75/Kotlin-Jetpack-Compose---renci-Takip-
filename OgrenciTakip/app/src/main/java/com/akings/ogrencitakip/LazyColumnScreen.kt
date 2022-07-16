package com.akings.ogrencitakip

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddToHomeScreen
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LazyColumnScreen(onItemClick: (String) -> Unit,navigateToBookTextField: () -> Unit,){
    val context= LocalContext.current
    val books= remember { mutableStateListOf(Book()) }

    BookRepository().getBooks(books)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToBookTextField,
                backgroundColor = Color.Red,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
            }
        }
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(books) { book ->
                    BookListItem(book = book, onItemClick = onItemClick, books = books)
                }
            }
        }
    }

}

@Composable
fun BookListItem(book:Book, books: SnapshotStateList<Book>, onItemClick: (String) -> Unit) {

    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .clickable {onItemClick(book.id)
                println("clickable:$book.id")
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp
    ) {
        val color= listOf(
            Color(0xFFA98FF3),
            Color(0xFFDDBBA1),
            Color(0xFF6390F0),
            Color(0xFFF7D02C),
            Color(0xFF7AC74C)
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(
                color.random()

            )) {

            Row(modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = book.kitap, fontSize = 24.sp)
                Spacer(modifier = Modifier.padding(6.dp))
                Text(text = book.ders,fontSize = 24.sp)
                Spacer(modifier = Modifier.padding(6.dp))
                IconButton(
                    onClick = {
                        BookRepository().deleteBook(book,books)
                    },

                    ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete item",
                        tint = Color.White
                    )
                }

            }

        }
    }

}
