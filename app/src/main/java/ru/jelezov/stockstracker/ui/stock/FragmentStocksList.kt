package ru.jelezov.stockstracker.ui.stock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.jelezov.stockstracker.databinding.FragmentStocksListBinding
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.ui.stock.viewModel.FragmentStocksListViewModel

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

        binding.recyclerStocks.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            val adapter = AdapterFragmentStocksList(this@FragmentStocksList)
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
        viewModel.loadStocks()
    }

}