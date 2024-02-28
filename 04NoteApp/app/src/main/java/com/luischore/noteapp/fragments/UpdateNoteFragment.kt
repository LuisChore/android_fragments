package com.luischore.noteapp.fragments

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.luischore.noteapp.MainActivity
import com.luischore.noteapp.R
import com.luischore.noteapp.adapter.NoteAdapter
import com.luischore.noteapp.databinding.FragmentHomeBinding
import com.luischore.noteapp.databinding.FragmentNewNoteBinding
import com.luischore.noteapp.databinding.FragmentUpdateNoteBinding
import com.luischore.noteapp.model.Note
import com.luischore.noteapp.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note){

    //binding
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!
    //ViewModel
    private lateinit var viewModel: NoteViewModel

    private lateinit var currentNote: Note
    //
    private val args: UpdateNoteFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        currentNote = args.note!!

        binding.etNoteTitleUpdate.setText(currentNote.noteTitle)
        binding.etNoteBodyUpdate.setText(currentNote.noteBody)

        binding.fabDone.setOnClickListener {
            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()
            val id = currentNote.id
            if(title.isNotEmpty()){
                val note = Note(id,title,body)
                viewModel.updateNote(note)
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }else{
                Toast.makeText(
                    context,
                    "Enter data",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("You want to delete this note? ")
            setPositiveButton("Delete"){_,_ ->
                viewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete->{
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}