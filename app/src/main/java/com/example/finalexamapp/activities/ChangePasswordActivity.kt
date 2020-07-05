package com.example.finalexamapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalexamapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {

    private  val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        changePasswordBtn.setOnClickListener {
            val changedPassword = newPassword.text.toString()
            val confirmNewPassword = confirmPassword.text.toString()

            if (changedPassword.isNotEmpty() && confirmNewPassword.isNotEmpty()) {
                if (changedPassword == confirmNewPassword) {
                    auth.currentUser?.updatePassword(changedPassword)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Password was changed!",
                                    Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Something went wrong!",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Passwords don't match!",
                        Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields!",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}