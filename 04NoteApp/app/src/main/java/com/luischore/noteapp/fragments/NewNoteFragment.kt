package com.luischore.noteapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.luischore.noteapp.MainActivity
import com.luischore.noteapp.R
import com.luischore.noteapp.adapter.NoteAdapter
import com.luischore.noteapp.databinding.FragmentHomeBinding
import com.luischore.noteapp.databinding.FragmentNewNoteBinding
import com.luischore.noteapp.model.Note
import com.luischore.noteapp.viewmodel.NoteViewModel

class NewNoteFragment : Fragment(R.layout.fragment_new_note){

    //binding
    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!
    //ViewModel
    private lateinit var viewModel: NoteViewModel
    //Adapter
    private lateinit var mView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        mView = view
    }

    private fun saveNote(view: View){
        val noteTitle = binding.etNoteTitle.text.toString().trim()
        val noteBody = binding.etNoteBody.text.toString().trim()
        if(noteTitle.isNotEmpty() && noteBody.isNotEmpty()){
            val note = Note(0,noteTitle,noteBody)
            viewModel.addNote(note)
            Toast.makeText(mView.context,"Note Saved successfully",Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        }else{
            Toast.makeText(
                mView.context,
                "Enter data",
                Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_new_note,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save->{
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}