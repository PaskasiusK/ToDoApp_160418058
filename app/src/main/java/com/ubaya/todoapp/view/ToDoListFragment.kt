package com.ubaya.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.todoapp.R
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.viewmodel.ListToDoViewModel
import kotlinx.android.synthetic.main.fragment_to_do_list.*


class ToDoListFragment : Fragment() {

    private lateinit var viewModel:ListToDoViewModel
    private val todoListAdapter  = ToDoListAdapter(arrayListOf(),   { item -> doClick(item) })
        fun doClick(item:Any) {
//            viewModel.clearTask(item as Todo)
            viewModel.updateTodoDone(item as Todo)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListToDoViewModel::class.java)
        viewModel.refresh()
        recViewToDo.layoutManager = LinearLayoutManager(context)
        recViewToDo.adapter = todoListAdapter
        fabToDo.setOnClickListener {
            val action = ToDoListFragmentDirections.actionCreateToDo()
            Navigation.findNavController(it).navigate(action)


        }
        observeViewModel()

    }
    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            todoListAdapter.updateTodoList(it)
            if(it.isEmpty()) {
                txtempty.visibility = View.VISIBLE
            } else {
                txtempty.visibility = View.GONE
            }
        })
    }


}