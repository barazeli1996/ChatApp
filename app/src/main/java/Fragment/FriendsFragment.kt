package Fragment

import Adapter.FriendsAdapter
import Model.User
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.barazeli.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_friends.*

class FriendsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
     private lateinit var adapterFriends:FriendsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var userList:ArrayList<User>
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        userList=ArrayList()
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }
    private fun readUser(){
        auth=FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = auth.currentUser
        val reference=FirebaseDatabase.getInstance().getReference("User")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data:DataSnapshot in dataSnapshot.children){
                    val user : User? = data.getValue(User::class.java)

                    if (user?.id!= firebaseUser!!.uid) {
                        if (user != null) {
                            userList.add(user)
                        }
                    }
                      adapterFriends= FriendsAdapter(userList)
                      recycler_friend.adapter=adapterFriends
                    }

            }
        })
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        linearLayoutManager= LinearLayoutManager(context)
        recycler_friend.layoutManager =linearLayoutManager
        readUser()
    }
    
}

