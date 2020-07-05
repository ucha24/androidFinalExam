package com.example.finalexamapp.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.example.finalexamapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_create_post.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class CreatePostActivity : AppCompatActivity() {

    private val imageName = UUID.randomUUID().toString() + ".jpg"

    private val postID = UUID.randomUUID().toString()

    private val auth = FirebaseAuth.getInstance()

    private var isChooseImageClicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        choosePostImageBtn.setOnClickListener {
            isChooseImageClicked = true
            chooseImageClicked(it)
        }

        postPostBtn.setOnClickListener {
            postClicked(it)
        }

    }

    private fun getPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }


    private fun chooseImageClicked(view: View) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            getPhoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                postImageView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

    private fun postClicked(view: View) {

        if (!isChooseImageClicked) {
            postImageView.setImageResource(R.mipmap.blank_image)
        }

        postImageView.isDrawingCacheEnabled = true
        postImageView.buildDrawingCache()
        val bitmap = (postImageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = FirebaseStorage.getInstance().reference
            .child("images").child(imageName).putBytes(data)

        uploadTask.addOnFailureListener {
            Toast.makeText(this, "Upload has failed!", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            var downloadUrl: String

            FirebaseStorage.getInstance()
                .reference.child("images/$imageName").downloadUrl.addOnCompleteListener {
                    downloadUrl = it.result.toString()

                    val database = FirebaseDatabase.getInstance().reference

                    val postTime: String = SimpleDateFormat("yyyy-MM-dd - HH:mm.SSS",
                        Locale.getDefault()).format(Date())

                    val postMap: Map<String, String> =
                        mapOf("email" to auth.currentUser!!.email.toString(),
                            "imageURL" to downloadUrl,
                            "postText" to postTextView.text.toString(),
                            "postID" to postID,
                            "postTime" to postTime)

                      database.child("posts").child(postID).setValue(postMap)

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                }
        }

    }
}