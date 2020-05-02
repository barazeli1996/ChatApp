package com.barazeli.chatapp

import Model.Message
import Model.User
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.toolbar
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity() {
      private lateinit var msgs : ArrayList<Message>
      private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        linearLayoutManager = LinearLayoutManager(this)
        recycler_msg.layoutManager = linearLayoutManager
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
                readMessage(firebaseUser.uid, userID!!)


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
        val manager=LinearLayoutManager(this)
        manager.stackFromEnd=true
        recycler_msg.layoutManager=manager
        recycler_msg.setHasFixedSize(true)
        msgs= ArrayList()


    }
    private fun sendMessage(sender:String,receiver:String,msg:String){
        val root=FirebaseDatabase.getInstance().reference
        val hashMap=HashMap<String, Any>()
        hashMap["sender"] = sender
        hashMap["receiver"] = receiver
        hashMap["msg"] = msg
        root.child("Chat").push().setValue(hashMap)

    }
    private fun readMessage(my_id:String,user_id:String){
        val reference=FirebaseDatabase.getInstance().getReference("Chat")
        reference.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                msgs.clear()
             for (data in p0.children ){
                 val msg=data.getValue(Message::class.java)
                 if ((msg?.receiver==my_id && msg.sender==user_id )
                     ||
                     (msg?.sender==my_id && msg.receiver==user_id)){
                     msgs.add(msg)
                 }

             }

            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}
