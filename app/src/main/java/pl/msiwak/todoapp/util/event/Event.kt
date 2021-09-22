package pl.msiwak.todoapp.util.event

class Event<out T>(private val content: T) {

    fun peekContent(): T = content
}