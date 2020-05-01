package com.barazeli.chatapp

import Adapter.ViewPagerAdapter
import Fragment.ChatFragment
import Fragment.FriendsFragment
import Fragment.StatusFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
          private lateinit var auth: FirebaseAuth

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_home)
        this.setSupportActionBar(this.toolbar)
        this.supportActionBar?.title="ChatApp"
        this.toolbar.setTitleTextColor(R.color.white)
        val viewPagerAdapter=ViewPagerAdapter(this.supportFragmentManager)
        viewPagerAdapter.addFragment(FriendsFragment(),"Friends")
        viewPagerAdapter.addFragment(ChatFragment(),"Chat")
        viewPagerAdapter.addFragment(StatusFragment(),"Status")
        this.view_pager.adapter=viewPagerAdapter
        this.tabs.setupWithViewPager(this.view_pager)
        this.auth = FirebaseAuth.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         this.menuInflater.inflate(R.menu.main_menu,menu)
    
    return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
               when(item?.itemId) {

                   R.id.logout -> {
                       this.auth.signOut()
                       this.startActivity (Intent(this, MainActivity::class.java))
                       this.finish()
                   }
                   R.id.search -> {
                      Toast.makeText(this,"Searching.. ",Toast.LENGTH_SHORT).show()
                   }
                   R.id.groups -> {
                       Toast.makeText(this,"Groups.. ",Toast.LENGTH_SHORT).show()

                   }
                   R.id.profile -> {
                       Toast.makeText(this,"Profile.. ",Toast.LENGTH_SHORT).show()

                   }
                   R.id.settings -> {
                       Toast.makeText(this,"Setting.. ",Toast.LENGTH_SHORT).show()

                   }
               }
        return super.onOptionsItemSelected(item)
    }
    /*
    override fun onStart() {
        super.onStart()
        if (this.auth != null){
         val intent=Intent(this,HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
            this.finish()

        }
    }
    
     */
}
