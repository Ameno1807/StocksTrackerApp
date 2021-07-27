package ru.jelezov.stockstracker.ui.stock.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.repository.Repository
import javax.inject.Inject

@HiltViewModel
class FragmentStocksListViewModel @Inject constructor(
    private val repository: Repository
    ) : ViewModel() {

    val stocks: MutableLiveData<List<StocksData>> = MutableLiveData()

    fun loadStocks() {
        viewModelScope.launch(Dispatchers.IO) {
           stocks.postValue(repository.loadStocks())
        }
    }

    fun loadFavouritesList() {
        viewModelScope.launch(Dispatchers.IO) {
            stocks.postValue(repository.loadFavouritesList())
        }
    }

    fun updateFavorite(stock: StocksData) =
        viewModelScope.launch(Dispatchers.IO) { repository.updateFavorite(stock) }

}