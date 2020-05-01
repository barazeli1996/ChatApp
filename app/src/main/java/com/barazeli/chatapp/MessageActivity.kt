package com.barazeli.chatapp

import Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.toolbar
import kotlinx.android.synthetic.main.activity_message.*
import java.util.*
import kotlin.collections.HashMap

class MessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        setSupportActionBar(toolbar)
        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        val intent= intent
        val userID:String?=intent.getStringExtra("id")
        val firebaseUser= FirebaseAuth.getInstance().currentUser!!
        val reference=FirebaseDatabase.getInstance()
            .getReference("User").child(userID.toString())
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val user=p0.getValue(User::class.java)

                if (user != null) {
                    user_name_friend_toolbar.text=user.username
                }
                if (user?.imageURL == "default"){
                    image_friend_msg_toolbar.setImageResource(R.drawable.bara1)
                } else{
                    Glide.with(this@MessageActivity).load(user?.imageURL).into(image_friend_msg_toolbar)
                }


            }
            override fun onCancelled(p0: DatabaseError) {
            }

        })
        send_btn.setOnClickListener {
            val message=write_msg.text.toString()
            if (message.isEmpty()){
                Toast.makeText(this,"Empty Message",Toast.LENGTH_SHORT).show()
            }else{
                if (userID != null) {
                    sendMessage(firebaseUser.uid,userID,message)
                }
            }
            write_msg.setText("")
        }
    }
    private fun sendMessage(sender:String,receiver:String,msg:String){
        val root=FirebaseDatabase.getInstance().reference
        val hashMap=HashMap<String, Any>()
        hashMap["sender"] = sender
        hashMap["receiver"] = receiver
        hashMap["msg"] = msg
        root.child("Chat").push().setValue(hashMap)

    }
}
