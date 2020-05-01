package Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {
    private val fragments: MutableList<Fragment> = ArrayList()
    private val titles: MutableList<String> = ArrayList()


    override fun getItem(position: Int): Fragment {
      return fragments[position]

    }

    override fun getCount(): Int {
     return fragments.size
    }
    fun addFragment(fragment: Fragment,string: String){
        fragments.add(fragment)
        titles.add(string)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}