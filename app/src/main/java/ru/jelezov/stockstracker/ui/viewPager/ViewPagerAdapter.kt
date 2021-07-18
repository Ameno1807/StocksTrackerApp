package ru.jelezov.stockstracker.ui.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.jelezov.stockstracker.ui.favouriteStock.FragmentFavouriteList
import ru.jelezov.stockstracker.ui.stock.FragmentStocksList

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentStocksList()
            }
            else -> {
                FragmentFavouriteList()
            }
        }
    }


}
