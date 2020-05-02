package Adapter

import Model.User
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.barazeli.chatapp.MessageActivity
import com.barazeli.chatapp.R
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.friends_row.view.*

class FriendsAdapter(private val users : ArrayList<User>) : RecyclerView.Adapter<FriendsAdapter.FriendHolder>() {
    class FriendHolder(itemView : View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener {
        private var view:View=itemView
        private var user:User?=null
        private var name_friend: TextView =view.name_friend
        private var img_friend: CircleImageView =view.image_friend
        
        init {
            view.setOnClickListener(this)
        }
        companion object{
        }
        override fun onClick(v: View?) {

        }
         fun bindFriend(user: User){
             this.name_friend.text=user.username
             if (user.imageURL == "default"){
                 this.img_friend.setImageResource(R.drawable.bara1)
             } else{
                 Glide.with(itemView.context).load(user.imageURL).into(this.img_friend)
             }
             view.setOnClickListener {
                 val context=itemView.context
                 val intent=Intent(context,MessageActivity::class.java)
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
                 intent.putExtra("id",user.id)
                 context.startActivity(intent)
             }
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        val inflatedView=LayoutInflater.from(parent.context)
            .inflate(R.layout.friends_row,parent,false)
        return FriendHolder(inflatedView)
    }

    override fun getItemCount(): Int {
       return  users.size
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        val itemUser=users[position]
        holder.bindFriend(itemUser)

    }
}