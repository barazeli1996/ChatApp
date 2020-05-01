package com.barazeli.chatapp

import Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var auth: FirebaseAuth?=null
    var firebaseUser:FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth=FirebaseAuth.getInstance()
        login_login.setOnClickListener {
            val email:String=email_edit.text.toString()
            val password:String=password_edit.text.toString()
         if (validateEmail()||validatePassword()){
             signIn(email,password)
         }else{
             Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
         }
        }
        forget_password.setOnClickListener{
            Toast.makeText(this,"Forget Password..",Toast.LENGTH_SHORT).show()

        }
        register_login.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        
    }
    private fun validateEmail() :Boolean {
       val email:String=email_edit.text.toString()
        return if (email.isEmpty()){
            email_edit.error="Require..."
            false
        }else{
            true
        }
    }
    private fun validatePassword() :Boolean {
        val email:String=password_edit.text.toString()
        return if (email.isEmpty()){
            password_edit.error="Require..."
            false
        }else{
            true
        }
    }
    private fun signIn(email:String, password:String) {
      //  val database = Firebase.database
        auth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
                 finish()
            }else{
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
                
            }

        }
    }

    override fun onStart() {
        super.onStart()
        firebaseUser=auth?.currentUser
        if (firebaseUser!=null){
            val intent=Intent(this,HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
