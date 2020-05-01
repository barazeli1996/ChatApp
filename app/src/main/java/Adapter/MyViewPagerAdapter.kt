package Adapter

import Fragment.ChatFragment
import Fragment.FriendsFragment
import Fragment.StatusFragment
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyViewPagerAdapter constructor(manager: FragmentManager): FragmentPagerAdapter(manager) {
    private val fragments: MutableList<Fragment> = ArrayList()
    private val titles: List<String> = listOf<String>("CHATS","STATUS","CALLS")



    override fun getItem(position: Int): Fragment {
        //return fragments[position]
        return when(position)
        {
            0 ->  FriendsFragment()
            1 -> ChatFragment()
            2 -> StatusFragment()
            else -> FriendsFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]

    }

}