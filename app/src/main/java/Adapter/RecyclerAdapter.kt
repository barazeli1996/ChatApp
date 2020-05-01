package Adapter

import Model.User
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.barazeli.chatapp.HomeActivity
import com.barazeli.chatapp.MessageActivity
import com.barazeli.chatapp.R
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.friends_row.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var users: List<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return UserViewHolder(
           LayoutInflater.from(parent.context)
               .inflate(R.layout.friends_row,parent,false)

       )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is UserViewHolder -> {
                holder.bind(users[position])
            }
        }

    }
    class UserViewHolder constructor(itemView:View): RecyclerView.ViewHolder(itemView)  {
        val img_freind : CircleImageView  = itemView.image_friend
        val username_friend : TextView = itemView.name_friend
        fun bind(user:User){
            username_friend.text = user.username
            if (user.imageURL == "default"){
                img_freind.setImageResource(R.drawable.bara1)
            } else{
                Glide.with(itemView.context).load(user.imageURL).into(img_freind)
            }
          //img_freind.setImageResource(R.drawable.bara1)
            itemView.setOnClickListener {
                val intent= Intent(itemView.context, MessageActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("id",user.id)
                itemView.context.startActivity(intent)
            }
        }
    }
    fun submitList(userList: List<User>){
        users=userList
    }

}