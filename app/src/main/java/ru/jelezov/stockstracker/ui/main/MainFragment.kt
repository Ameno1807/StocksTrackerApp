package ru.jelezov.stockstracker.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
import ru.jelezov.stockstracker.R
import ru.jelezov.stockstracker.databinding.FragmentMainBinding
import ru.jelezov.stockstracker.ui.viewPager.ViewPagerAdapter

class MainFragment: Fragment() {

    private lateinit var mainTabsViewPager: ViewPager2
    private lateinit var mainStocksTabText: TextView
    private lateinit var mainFavouriteTabText: TextView


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainTabsViewPager = binding.viewpager
        mainStocksTabText = binding.textTabStocks
        mainFavouriteTabText = binding.textTabFavourite

        initTabs()

        binding.consSearch.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_fragmentSearch, null)
        }
    }

    private fun initTabs() {
        //add adapter for view pager
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        mainTabsViewPager.adapter = adapter
        mainTabsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //change color and text size when focused
                when (position) {
                    //when stocks tab in focus
                    0 -> {
                        mainTabsViewPager.offscreenPageLimit = OFFSCREEN_PAGE_LIMIT_DEFAULT
                        mainStocksTabText.setTextColor(Color.BLACK)
                        mainStocksTabText.textSize = 30f

                        mainFavouriteTabText.setTextColor(Color.GRAY)
                        mainFavouriteTabText.textSize = 20f
                    }
                    //when favourite tab in focus
                    else ->{
                        mainTabsViewPager.offscreenPageLimit = OFFSCREEN_PAGE_LIMIT_DEFAULT
                        mainFavouriteTabText.setTextColor(Color.BLACK)
                        mainFavouriteTabText.textSize = 30f


                        mainStocksTabText.setTextColor(Color.GRAY)
                        mainStocksTabText.textSize = 20f
                    }
                }
            }
        })

        mainStocksTabText.setOnClickListener {
            mainTabsViewPager.currentItem = 0
        }
        mainFavouriteTabText.setOnClickListener {
            mainTabsViewPager.currentItem = 1
        }
    }
}