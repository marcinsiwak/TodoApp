package pl.msiwak.todoapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class EditTaskData(
    val position: Int,
    val task: Task?
): Parcelable