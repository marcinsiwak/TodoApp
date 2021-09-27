package pl.msiwak.todoapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val title: String? = "",
    val description: String? = "",
    val iconUrl: String? = "",
    val creationDate: String? = ""
): Parcelable