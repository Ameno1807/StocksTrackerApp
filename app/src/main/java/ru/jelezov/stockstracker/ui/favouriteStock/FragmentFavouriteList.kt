package ru.jelezov.stockstracker.ui.favouriteStock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.jelezov.stockstracker.databinding.FragmentFavouriteStocksBinding
import ru.jelezov.stockstracker.ui.stock.AdapterFragmentStocksList
import ru.jelezov.stockstracker.ui.stock.viewModel.FragmentStocksListViewModel

@AndroidEntryPoint
class FragmentFavouriteList: Fragment() {

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
        viewModel.loadFavouritesList()

        binding.swipeRefreshFavouriteLayout.setOnRefreshListener {
            viewModel.loadFavouritesList()
            binding.swipeRefreshFavouriteLayout.isRefreshing = false
        }

        binding.recyclerFavouriteStocks.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            val adapter = AdapterFragmentStocksList(viewModel)

            this.adapter = adapter

            loadDataToAdapter(adapter)
        }

    }

    private fun loadDataToAdapter(adapter: AdapterFragmentStocksList) {
        viewModel.favouriteStocks.observe(viewLifecycleOwner, { stocks ->
            adapter.submitList(stocks)
        })
    }
}
