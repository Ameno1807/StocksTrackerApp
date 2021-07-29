package ru.jelezov.stockstracker.ui.favouriteStock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.jelezov.stockstracker.R
import ru.jelezov.stockstracker.databinding.FragmentFavouriteStocksBinding
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.ui.favouriteStock.viewModel.FragmentFavouriteStocksListViewModel
import ru.jelezov.stockstracker.ui.stock.AdapterFragmentStocksList
import ru.jelezov.stockstracker.ui.stock.StocksListener
import ru.jelezov.stockstracker.utils.StockItemDecoration

@AndroidEntryPoint
class FragmentFavouriteList: Fragment(), StocksListener {

    private val stocksFavouriteListViewModel: FragmentFavouriteStocksListViewModel by viewModels()
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
            stocksFavouriteListViewModel.readAllFavourite
        }
        decorateStockList(binding.recyclerFavouriteStocks)
        binding.recyclerFavouriteStocks.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            val adapter = AdapterFragmentStocksList(this@FragmentFavouriteList)
            stocksFavouriteListViewModel.readAllFavourite.observe(viewLifecycleOwner, { stocks ->
                adapter.addStocks(stocks)
            })
            val animator = itemAnimator
            if (animator is DefaultItemAnimator) {
                animator.supportsChangeAnimations = false
            }
            hasFixedSize()
            this.adapter = adapter
        }
    }

    private fun decorateStockList(recyclerView: RecyclerView) {
        recyclerView.apply {

            val alterBackground = ResourcesCompat
                .getDrawable(resources, R.drawable.bg_light_shape, context.theme)!!
            val mainBackground = ResourcesCompat
                .getDrawable(resources, R.drawable.bg_dark_shape, context.theme)!!

            val decoration =
                StockItemDecoration(alterBackground, mainBackground)
            addItemDecoration(decoration)
        }
    }

    override fun click(stocksData: StocksData) {
        stocksFavouriteListViewModel.updateFavorite(stocksData.copy(isFavourite = !stocksData.isFavourite))
    }

}
