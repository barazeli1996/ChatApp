package Fragment

import Adapter.RecyclerAdapter
import Model.User
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barazeli.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_friends.*

class FriendsFragment : Fragment() {
    private lateinit var adapterR:RecyclerAdapter
    private lateinit var auth: FirebaseAuth
    private var manager: LinearLayoutManager? =null
    val userList:ArrayList<User> = ArrayList()
    // private lateinit var firebaseUser: FirebaseUser
    private lateinit var reference: DatabaseReference
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view:View = inflater.inflate(R.layout.fragment_friends, container, false)

        container?.addView(view)
        return view
    }
    private fun readUser(){
        auth=FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = auth.currentUser
        val reference=FirebaseDatabase.getInstance().getReference("User")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (data:DataSnapshot in p0.children){
                    val user : User? = data.getValue<User>()
                    if (user!!.id!= firebaseUser!!.uid) {
                        userList.add(user)
                    }
                    }
                adapterR.submitList(userList)
                recycler.adapter=adapterR
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(context)
        adapterR=RecyclerAdapter()
        readUser()
    }
}

