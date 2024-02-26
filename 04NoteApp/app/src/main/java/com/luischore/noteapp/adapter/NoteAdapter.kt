package com.luischore.noteapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.luischore.noteapp.databinding.NoteItemBinding
import com.luischore.noteapp.fragments.HomeFragment
import com.luischore.noteapp.model.Note
import kotlin.random.Random

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(val binding:NoteItemBinding):RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
             return oldItem.id == newItem.id &&
                     oldItem.noteBody == newItem.noteBody &&
                     oldItem.noteTitle == newItem.noteTitle
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = differ.currentList[position]
        holder.binding.tvNoteTitle.text = currentNote.noteTitle
        holder.binding.tvNoteBody.text = currentNote.noteBody

        val random = Random
        val color = Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )

        holder.binding.ibColor.setBackgroundColor(color)
        holder.itemView.setOnClickListener {
           // val direction = HomeFragmentDirections
        }
    }

}