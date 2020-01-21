package pl.mrozok.myapplication.presentation.tools

import androidx.appcompat.app.AppCompatActivity
import pl.mrozok.myapplication.infrastructure.Graph
import pl.mrozok.myapplication.infrastructure.NotesApplication

abstract class BaseActivity : AppCompatActivity() {

    protected fun graph(): Graph = (application as NotesApplication).graph

}