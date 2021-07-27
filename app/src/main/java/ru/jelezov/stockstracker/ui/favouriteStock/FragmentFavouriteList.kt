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
import ru.jelezov.stockstracker.ui.stock.viewModel.FragmentStocksListViewModel
import ru.jelezov.stockstracker.ui.viewPager.ViewPagerAdapter

@AndroidEntryPoint
class FragmentFavouriteList: Fragment() {

    private val viewModel: FragmentStocksListViewModel by viewModels()
    private var _binding: FragmentFavouriteStocksBinding? = null
    private val binding get() = _binding!!

    private val onStarClick: (stock: StocksData) -> Unit =  { stock ->
        viewModel.updateFavorite(stock)
    }

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

        viewModel.loadFavouritesList()

        binding.swipeRefreshFavouriteLayout.setOnRefreshListener {
            binding.swipeRefreshFavouriteLayout.isRefreshing = false
            viewModel.loadFavouritesList()
        }

        binding.recyclerFavouriteStocks.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            val adapter = AdapterFragmentStocksList(onStarClick)
            viewModel.stocks.observe(viewLifecycleOwner, { stocks ->
                adapter.submitList(stocks)
                adapter.notifyDataSetChanged()
                Log.e("Tag", "$stocks")
            })
            this.adapter = adapter
        }
    }
}
