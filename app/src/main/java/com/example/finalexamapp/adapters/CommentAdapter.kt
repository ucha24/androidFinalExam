package com.example.finalexamapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalexamapp.R
import com.example.finalexamapp.dto.CommentDTO
import kotlinx.android.synthetic.main.comment_item.view.*


class CommentAdapter(private var comments: ArrayList<CommentDTO> )
    : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(comment: CommentDTO) {

            itemView.commentAuthor.text = "Author: ${comment.commentAuthor}"
            itemView.commentTextView.text = comment.commentText

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(v)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

}
