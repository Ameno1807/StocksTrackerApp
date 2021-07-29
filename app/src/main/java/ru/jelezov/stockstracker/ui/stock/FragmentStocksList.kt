package ru.jelezov.stockstracker.ui.stock

import android.os.Bundle
import android.util.Log
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
import ru.jelezov.stockstracker.databinding.FragmentStocksListBinding
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.ui.stock.viewModel.FragmentStocksListViewModel
import ru.jelezov.stockstracker.utils.StockItemDecoration

@AndroidEntryPoint
class FragmentStocksList: Fragment(), StocksListener {

    private val viewModel: FragmentStocksListViewModel by viewModels()
    private var _binding: FragmentStocksListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStocksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadStocks()
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.loadStocks()
        }

        decorateStockList(binding.recyclerStocks)
        binding.recyclerStocks.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            val adapter = AdapterFragmentStocksList(this@FragmentStocksList)
            viewModel.stocks.observe(viewLifecycleOwner, { stocks ->
                adapter.addStocks(stocks)
            })
            Log.e("Tag", "StocksViewModel -> ${viewModel.stocks}")
            val animator = itemAnimator
            if (animator is DefaultItemAnimator) {
                animator.supportsChangeAnimations = false
            }
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
            hasFixedSize()
        }
    }

    override fun click(stocksData: StocksData) {
        viewModel.updateFavorite(stocksData.copy(isFavourite = !stocksData.isFavourite))

    }

   override fun onResume() {
        super.onResume()
        viewModel.loadStocks()
    }

}