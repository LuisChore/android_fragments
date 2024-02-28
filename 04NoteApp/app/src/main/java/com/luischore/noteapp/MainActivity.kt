package com.luischore.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.luischore.noteapp.database.NoteDatabase
import com.luischore.noteapp.databinding.ActivityMainBinding
import com.luischore.noteapp.repository.NoteRepository
import com.luischore.noteapp.viewmodel.NoteViewModel
import com.luischore.noteapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val db = NoteDatabase(this)
        val repository = NoteRepository(db)
        val viewModelProviderFactory = NoteViewModelFactory(application,repository)
        viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(NoteViewModel::class.java)
    }
}