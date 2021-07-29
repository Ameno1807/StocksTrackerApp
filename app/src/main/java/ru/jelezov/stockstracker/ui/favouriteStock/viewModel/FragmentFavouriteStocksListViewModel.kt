package ru.jelezov.stockstracker.ui.favouriteStock.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.repository.Repository
import javax.inject.Inject

@HiltViewModel
class FragmentFavouriteStocksListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val readAllFavourite = repository.readAllFavourite().asLiveData()

    fun updateFavorite(stock: StocksData) =
        viewModelScope.launch(Dispatchers.IO) { repository.updateFavorite(stock) }

}