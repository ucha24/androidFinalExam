package com.example.finalexamapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalexamapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_comment.*


class AddCommentActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)

        val postID = intent.getStringExtra("POST_ID")

        addCommentBtn.setOnClickListener {
            val commentMap: Map<String, String> =
                mapOf("commentAuthor" to auth.currentUser!!.email.toString(),
                    "commentText" to addCommentText.text.toString())

            database.child("comments").child(postID!!).push().setValue(commentMap)

            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra("POST_ID", postID)
            startActivity(intent)
            finish()
        }

    }
}

