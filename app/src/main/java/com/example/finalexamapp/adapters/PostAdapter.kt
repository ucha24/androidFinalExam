package com.example.finalexamapp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalexamapp.dto.PostDTO
import com.example.finalexamapp.R
import com.example.finalexamapp.activities.CommentsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class PostAdapter(private var posts: ArrayList<PostDTO> )
    : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val auth = FirebaseAuth.getInstance()
        private val database = FirebaseDatabase.getInstance().reference

        @SuppressLint("SetTextI18n")
        fun bind(post: PostDTO) {
            itemView.postAuthor.text = "Author: ${post.email}"
            itemView.userPostText.text = post.postText
            itemView.postedTime.text = "Date: ${post.postTime?.substring(0, 18)}"
            Picasso.get().load(post.imageURL).into(itemView.userPostImage)

            var likesCount = 0
            var dislikesCount = 0

            val likesRef = database.child("likes").child(post.postID!!)
            likesRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        likesCount = snapshot.children.count()
                        itemView.likeCountView.text = likesCount.toString()
                    } else {
                        itemView.likeCountView.text ="0"
                    }
                }
            })

            val dislikesRef = database.child("dislikes").child(post.postID!!)
            dislikesRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        dislikesCount = snapshot.children.count()
                        itemView.dislikeCountView.text = dislikesCount.toString()
                    } else {
                        itemView.dislikeCountView.text = "0"
                    }
                }

            })


            itemView.goToCommentsIcn.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, CommentsActivity::class.java)
                intent.putExtra("POST_ID", post.postID)
                context.startActivity(intent)
            }

            itemView.postLikeBtn.setOnClickListener {
                database.child("dislikes").child(post.postID!!)
                    .child(auth.currentUser!!.uid).removeValue()

                database.child("likes").child(post.postID!!)
                    .child(auth.currentUser!!.uid).setValue(UUID.randomUUID().toString())
            }

            itemView.postDislikeBtn.setOnClickListener {
                database.child("likes").child(post.postID!!)
                    .child(auth.currentUser!!.uid).removeValue()

                database.child("dislikes").child(post.postID!!)
                    .child(auth.currentUser!!.uid).setValue(UUID.randomUUID().toString())
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(v)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

}