package com.ubaya.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListToDoViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    private var job = Job()
    fun refresh(){
        launch{
            val db = buildDb(getApplication())
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun clearTask(todo:Todo){
        launch{
            val db = buildDb(getApplication())
            db.todoDao().deleteTodo(todo)
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun updateTodoDone(todo:Todo){
        launch {
            val db = buildDb(getApplication())
            db.todoDao().updatedone(todo.uuid)
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }
    override val coroutineContext: CoroutineContext
        get() =  job + Dispatchers.Main
}