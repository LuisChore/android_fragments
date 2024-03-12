package com.luischore.noteapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.luischore.noteapp.MainActivity
import com.luischore.noteapp.R
import com.luischore.noteapp.adapter.NoteAdapter
import com.luischore.noteapp.databinding.FragmentHomeBinding
import com.luischore.noteapp.model.Note
import com.luischore.noteapp.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home) , SearchView.OnQueryTextListener{

    //binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    //ViewModel
    private lateinit var viewModel: NoteViewModel
    //Adapter
    private lateinit var adapter: NoteAdapter

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
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        mView = view
        setUpRecyclerView()
        binding.fabAddNote.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_homeFragment_to_newNoteFragment
            )
        }
    }

    private fun setUpRecyclerView() {
        adapter = NoteAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter


        activity?.let {
            viewModel.getAllNotes().observe(viewLifecycleOwner, Observer {
                adapter.differ.submitList(it)
                updateUI(it)
            }
            )
        }
        
    }

    private fun updateUI(note: List<Note>?) {
        if (note != null) {
            if(note.isNotEmpty()){
                binding.cardView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }else{
                binding.cardView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)
        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //searchNote(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchNote(newText)
        }
        return true
    }

    private fun searchNote(query: String?) {
        val searchQuery = "%$query%"
        viewModel.searchNote(searchQuery).observe(this){
            list -> adapter.differ.submitList(list)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}