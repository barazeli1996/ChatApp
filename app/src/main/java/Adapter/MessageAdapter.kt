package Adapter

import Model.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barazeli.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.right_chat.view.*

class MessageAdapter(private val msgs : ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.MsgHolder>() {
    private val LEFT_MSG=0
    private val RIGHT_MSG=1
    class MsgHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        private var view:View=itemView
        companion object{
        }
        private var rightMsg=view.right_msg_row
      //  private var left_msg=view.left_msg_row
        fun bindMsg(msg:Message){
            this.rightMsg.text=msg.msg
//            this.left_msg.text=msg.msg
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgHolder {
        return if (viewType==RIGHT_MSG){
            val view=LayoutInflater.from(parent.context)
                .inflate(R.layout.right_chat,parent,false)
            MsgHolder(view)
        }else{
            val view=LayoutInflater.from(parent.context)
                .inflate(R.layout.left_chat,parent,false)
            MsgHolder(view)
        }
    }
    override fun onBindViewHolder(holder: MsgHolder, position: Int) {
       val msgItem=msgs[position]
        holder.bindMsg(msgItem)
    }
    override fun getItemCount(): Int {
        return msgs.size
    }
    override fun getItemViewType(position: Int): Int {
        val firebaseUser=FirebaseAuth.getInstance().currentUser
        return if (msgs[position].sender==firebaseUser?.uid){
            RIGHT_MSG
        }else{
            LEFT_MSG
        }

    }
}