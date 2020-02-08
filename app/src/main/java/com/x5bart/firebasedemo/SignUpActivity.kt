package com.x5bart.firebasedemo

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        btnSign.setOnClickListener {
            signUpUser()

        }
    }

    private fun signUpUser() {
        if (etEMail.text.toString().isEmpty()) {
            etEMail.error = "Please enter valid email"
            etEMail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEMail.text.toString()).matches()) {
            etEMail.error = "Please enter valid email"
            etEMail.requestFocus()
            return
        }

        if (etPassword.text.toString().isEmpty()) {
            etPassword.error = "Please enter valid email"
            etPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(etEMail.text.toString(), etPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // ...
            }
    }
}
