package com.barazeli.chatapp

import Adapter.MessageAdapter
import Model.Message
import Model.User
import android.content.Intent
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
import org.jetbrains.anko.imageResource

class MessageActivity : AppCompatActivity() {

    private lateinit var msgs:ArrayList<Message>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapterMsg:MessageAdapter
     private lateinit var i:Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        // get extras from register act
         i= intent
        val userID : String?=intent.getStringExtra("id")
        msgs= ArrayList()
        // set recycler options
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd=true
        recycler_msg.setHasFixedSize(true)
        recycler_msg.layoutManager = linearLayoutManager



       // toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }




        // instantiate firebase items
        val firebaseUser= FirebaseAuth.getInstance().currentUser!!
        val reference=FirebaseDatabase.getInstance().getReference("User").child(userID.toString())


              //  set toolbar views in firebase database
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user=snapshot.getValue(User::class.java)
                if (user != null) {
                    user_name_friend_toolbar.text= user.username
                }
                if (user != null) {
                    if (user.imageURL =="default"){
                        image_friend_msg_toolbar.imageResource=R.drawable.bara1
                    }else{
                        Glide.with(this@MessageActivity)
                            .load(user.imageURL).into(image_friend_msg_toolbar)
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }

        })

        // send message btn

        send_btn.setOnClickListener {
            val message=write_msg.text.toString()
            if (message.isEmpty()){
                Toast.makeText(this,"Empty Message",Toast.LENGTH_SHORT).show()
            }else{
                sendMessage(firebaseUser.uid,userID.toString(),message)
            }
            write_msg.setText("")
        }

         // display chat list

        readMessage(firebaseUser.uid,userID.toString())


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
                 adapterMsg= MessageAdapter(msgs)
                 recycler_msg.adapter=adapterMsg
             }


            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}
