package ru.jelezov.stockstracker.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.jelezov.stockstracker.databinding.FragmentSearchBinding
import ru.jelezov.stockstracker.model.PopularRequestGenerated
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.ui.stock.StocksListener
import ru.jelezov.stockstracker.ui.stock.viewModel.FragmentStocksListViewModel

@AndroidEntryPoint
class FragmentSearch: Fragment(), StocksListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragmentStocksListViewModel by viewModels()
    private var showSuggestions = true

    private val onSuggestionClickListener: (suggestion: CharSequence) -> Unit = { suggestion ->
       binding.fieldSearch.setQuery(suggestion, false)
        showSuggestions = false
        updateVisibility()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fieldSearch.showKeyboard()

        binding.popularRequestsRecycler.apply {
            this.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            val adapter = AdapterFragmentSearchPopularRequest(onSuggestionClickListener)
            this.adapter = adapter
            adapter.submitList(PopularRequestGenerated.addPopularRequests())
        }
        binding.stocksRecycler.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            val adapter = AdapterFragmentSearch(this@FragmentSearch)
            viewModel.stocks.observe(viewLifecycleOwner, {
                adapter.addStocks(it)
            })
            val animator = itemAnimator
            if (animator is DefaultItemAnimator) {
                animator.supportsChangeAnimations = false
            }
            this.adapter = adapter
        }

        binding.fieldSearch.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.fieldSearch.queryHint = ""
                val inputMethodManager = requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            } else {
                val inputMethodManager = requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.fieldSearch.windowToken, 0)
                binding.fieldSearch.queryHint = "Find company or ticker"
            }
        }

        binding.fieldSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchStocks(query)
                showSuggestions = false
                updateVisibility()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                Log.e("Tag", "InputText -> $newText")
                viewModel.searchStocks(newText)
                if (newText.isEmpty()) {
                    showSuggestions = true; updateVisibility()
                }
                return true
            }
        })
      clickSearchButton()

    }

    private fun updateVisibility() {
        binding.popularRequestsRecycler.visibility = if (showSuggestions) View.VISIBLE else View.GONE
        binding.popularRequests.visibility = if (showSuggestions) View.VISIBLE else View.GONE
    }

    private fun clickSearchButton(){

        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    fun View.showKeyboard() {
        this.requestFocus()
    }


   override fun click(stocksData: StocksData) {
       viewModel.updateFavorite(stocksData.copy(isFavourite = !stocksData.isFavourite))
    }



}

