package com.example.finalexamapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalexamapp.R
import com.example.finalexamapp.adapters.PostAdapter
import com.example.finalexamapp.dto.PostDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val listOfPosts: ArrayList<PostDTO> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        postsRecycler.layoutManager = layoutManager

        val ref = database.getReference("posts").orderByChild("postTime")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (postInstance in snapshot.children.reversed()) {
                        val post = postInstance.getValue(PostDTO::class.java)
                        if (post != null) {
                            listOfPosts.add(post)
                        }
                    }
                    val adapter = PostAdapter(listOfPosts)
                    postsRecycler.adapter = adapter
                }
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else if (item.itemId == R.id.changePassword) {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        } else if (item.itemId == R.id.createPost) {
            startActivity(Intent(this, CreatePostActivity::class.java))
            finish()
        } else if (item.itemId == R.id.covid19Center) {
            startActivity(Intent(this, CovidSummaryActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    
}