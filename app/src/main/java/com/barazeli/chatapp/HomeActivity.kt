package com.barazeli.chatapp

import Adapter.MyViewPagerAdapter
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
        val viewPagerAdapter= MyViewPagerAdapter(this.supportFragmentManager)
        view_pager.adapter=viewPagerAdapter
        tabs.setupWithViewPager(view_pager)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         this.menuInflater.inflate(R.menu.main_menu,menu)
    
    return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
               when(item.itemId) {

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

    
}
