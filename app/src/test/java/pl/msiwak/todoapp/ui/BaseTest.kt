package pl.msiwak.todoapp.ui

import org.junit.After
import org.junit.Before
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

abstract class BaseTest {

    protected val MOCKED_STRING = "MOCKED_STRING"

    @Before
    open fun setup() = MockitoAnnotations.initMocks(this
    )

    @After
    open fun tearDown() = Unit

    protected fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    protected fun <T> any(c: Class<T>): T {
        Mockito.any<T>()
        return uninitialized()
    }

    protected fun <T> ArgumentCaptor<T>.kCapture(): T {
        capture()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

}