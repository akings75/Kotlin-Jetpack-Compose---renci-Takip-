package com.akings.ogrencitakip


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TextFieldScreen(
    navController: NavHostController,
    state: TextFieldState,
    updateBook: (String, String) -> Unit,
    addNewBook: (String, String) -> Unit
)
{
    Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {

        var kitap by rememberSaveable(state.book?.kitap){ mutableStateOf(state.book?.kitap?:"") }
        var ders by rememberSaveable(state.book?.ders){ mutableStateOf(state.book?.ders?:"") }

        TextField(value =kitap , onValueChange = { kitap = it }, label = { Text(text = "Kitap Adı")} )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(value = ders, onValueChange = { ders = it }, label = { Text(text = "Ders Adı")} )
        Spacer(modifier = Modifier.padding(4.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                addNewBook(kitap,ders)
                navController.navigate(Destinations.LazyColumnScreen)
            }
        ){
            Text(text = "Save")
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                updateBook(kitap,ders)
                navController.navigate(Destinations.LazyColumnScreen)
            }
        ){
            Text(text = "Update")
        }


    }

}

private fun showToast(context: Context, msg:String) {
    Toast.makeText(
        context,
        msg,
        Toast.LENGTH_LONG
    ).show()

}





fun <T> SnapshotStateList<T>.updateList(newList:List<T>) {
    clear()
    addAll(newList)
}

