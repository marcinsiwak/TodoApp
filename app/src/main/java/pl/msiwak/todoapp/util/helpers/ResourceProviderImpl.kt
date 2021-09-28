package pl.msiwak.todoapp.util.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ResourceProviderImpl constructor(private val context: Context) : ResourceProvider {

    override fun getString(stringId: Int): String = context.getString(stringId)


    override fun getDrawable(iconId: Int): Drawable? = ContextCompat.getDrawable(context, iconId)

    override fun getColor(colorResId: Int): Int = ContextCompat.getColor(context, colorResId)
}