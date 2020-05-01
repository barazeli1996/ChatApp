package Adapter

import Model.User
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.barazeli.chatapp.R
import de.hdodenhof.circleimageview.CircleImageView
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
                holder.bind(users.get(position))
            }
        }

    }
    class UserViewHolder constructor(itemView:View): RecyclerView.ViewHolder(itemView)  {
        val img_freind : CircleImageView  = itemView.image_friend
        val username_friend : TextView = itemView.name_friend
        fun bind(user:User){
            username_friend.setText(user.username)
            img_freind.setImageResource(R.drawable.bara1)
            
        }
    }
    fun submitList(userList: List<User>){
        users=userList
    }

}