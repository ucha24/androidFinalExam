package com.example.finalexamapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalexamapp.R
import com.example.finalexamapp.adapters.CommentAdapter
import com.example.finalexamapp.dto.CommentDTO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_comments.*


class CommentsActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val listOfComments: ArrayList<CommentDTO> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        val layoutManager = LinearLayoutManager(this)
        commentsRecycler.layoutManager = layoutManager

        val postID = intent.getStringExtra("POST_ID")

        addCommentAction.setOnClickListener {
            val intent = Intent(this, AddCommentActivity::class.java)
            intent.putExtra("POST_ID", postID)
            startActivity(intent)
            finish()
        }

        val commentsRef = database.getReference("comments").child(postID!!)

        commentsRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (commentInstance in snapshot.children) {
                        val comment = commentInstance.getValue(CommentDTO::class.java)
                        if (comment != null) {
                            listOfComments.add(comment)
                        }
                    }
                    val adapter = CommentAdapter(listOfComments)
                    commentsRecycler.adapter = adapter
                }
            }

        })

    }
}