
package com.akings.ogrencitakip
import android.annotation.SuppressLint
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository
@Inject
constructor() {
    @SuppressLint("StaticFieldLeak")
    val db = FirebaseFirestore.getInstance()

    fun getBooks(books: SnapshotStateList<Book>) {
        println("getBooks çağrıldı")
        db.collection("books").get().addOnSuccessListener {
            books.updateList(it.toObjects(Book::class.java))
        }.addOnFailureListener {
            books.updateList(listOf())
        }
    }

    fun addBook(book: Book) {
        try {

            val veri = db.collection("books")
                .document(book.id) // firebase te document'in ID sini bizim model sınıfımızın id'sine
            // eşitledim böylece silme işlemi için "db.collection("books").document(book.id).delete()" kodunu kullanarak silme işlemini yapabildim
            veri.set(book) // set işlemi sayesinde de kullanıcıdan alınan verileri database kaydetmeyi başardık

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getBookId(bookId: String): Flow<Result<Book>> = flow {
        println("getBookId çağrıldı")
        try {
            emit(Result.Loading<Book>())
            val book = db.collection("books")
                .whereGreaterThanOrEqualTo("id", bookId)
                .get()
                .await()
                .toObjects(Book::class.java)
                .first()

            emit(Result.Success<Book>(data = book))
            println("getBookId:$book")

        } catch (e: Exception) {
            emit(Result.Error<Book>(message = e.localizedMessage ?: "Error Desconocido"))
        }

    }

    fun updateBook1(bookId: String, book: Book) {
        try {
            val map = mapOf(
                "kitap" to book.kitap,
                "ders" to book.ders
            )

            db.collection("books").document(bookId).update(map)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteBook(book: Book, books: SnapshotStateList<Book>) {
        try {
            //books.removeAt(books.indexOf(book))
            books.remove(book)
            db.collection("books").document(book.id).delete()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
