package pl.msiwak.todoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.msiwak.todoapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}