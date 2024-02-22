package com.luischore.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.luischore.noteapp.model.Note
import com.luischore.noteapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    val app: Application,
    private val repository: NoteRepository):
    AndroidViewModel(app)
{
    fun addNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun getAllNotes() = repository.getAllNotes()

    fun searchNote(query: String?) = repository.searchNote(query)

}