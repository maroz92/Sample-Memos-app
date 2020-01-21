package pl.mrozok.myapplication.infrastructure

import android.app.Application

class NotesApplication : Application() {

    val graph: Graph by lazy {
        ProductionGraph(this)
    }

}
