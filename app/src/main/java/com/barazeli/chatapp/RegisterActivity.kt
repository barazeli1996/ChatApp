package com.barazeli.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    var auth:FirebaseAuth?=null
     var firebaseUser:FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth= FirebaseAuth.getInstance()
        register_register.setOnClickListener {
            val username:String=username_edit.text.toString()
            val email:String=email_edit_register.text.toString()
            val password:String=password_edit_register.text.toString()
            if (validateUserName()||validateEmail()||validatePassword()){
                register(username,email,password)
            }
        }
    }
    private fun register(username:String,email:String,password:String){
        auth?.createUserWithEmailAndPassword(email,password)?.addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                firebaseUser=auth?.currentUser
                val userID: String? =firebaseUser?.uid
                val reference:DatabaseReference=FirebaseDatabase.getInstance().getReference("User")
                    .child(userID.toString())
                val map=HashMap<String,String>()
                map["id"] = userID.toString()
                map["username"] = username
                map["imageURL"] = "default"
                reference.setValue(map).addOnCompleteListener {
                    if (task.isSuccessful){
                        val intent=Intent(this,HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    } else{
                        Toast.makeText(this,"Can't add this user",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"Authentication Failed..",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validateEmail() :Boolean {
        val email:String=email_edit_register.text.toString()
        return if (email.isEmpty()){
            email_edit_register.error="Require..."
            false
        }else{
            true
        }
    }
    private fun validatePassword() :Boolean {
        val email:String=password_edit_register.text.toString()
        return if (email.isEmpty()){
            password_edit_register.error="Require..."
            false
        }else{
            true
        }
    }
    private fun validateUserName() :Boolean {
        val email:String=username_edit.text.toString()
        return if (email.isEmpty()){
            username_edit.error="Require..."
            false
        }else{
            true
        }
    }
}
