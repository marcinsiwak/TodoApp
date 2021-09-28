package pl.msiwak.todoapp.util.helpers

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes stringId: Int): String
    fun getDrawable(iconId: Int): Drawable?
    fun getColor(@ColorRes colorResId: Int): Int
}