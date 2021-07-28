package ru.jelezov.stockstracker.ui.favouriteStock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify
import ru.jelezov.stockstracker.databinding.FragmentFavouriteStocksBinding
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.ui.main.MainFragment
import ru.jelezov.stockstracker.ui.stock.AdapterFragmentStocksList
import ru.jelezov.stockstracker.ui.stock.StocksListener
import ru.jelezov.stockstracker.ui.stock.viewModel.FragmentStocksListViewModel
import ru.jelezov.stockstracker.ui.viewPager.ViewPagerAdapter

@AndroidEntryPoint
class FragmentFavouriteList: Fragment(), StocksListener {

    private val viewModel: FragmentStocksListViewModel by viewModels()
    private var _binding: FragmentFavouriteStocksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteStocksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.swipeRefreshFavouriteLayout.setOnRefreshListener {
            binding.swipeRefreshFavouriteLayout.isRefreshing = false
            viewModel.loadFavouritesList()
        }

        binding.recyclerFavouriteStocks.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            val adapter = AdapterFragmentStocksList(this@FragmentFavouriteList)
            viewModel.stocks.observe(viewLifecycleOwner, { stocks ->
                adapter.addStocks(stocks)
                Log.e("Tag", "Stocks -> ${stocks}")
            })
            Log.e("Tag", "StocksViewModel -> ${viewModel.stocks}")
            this.adapter = adapter
        }
    }

    override fun click(stocksData: StocksData) {
        viewModel.updateFavorite(stocksData.copy(isFavourite = !stocksData.isFavourite))
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavouritesList()
    }
}
