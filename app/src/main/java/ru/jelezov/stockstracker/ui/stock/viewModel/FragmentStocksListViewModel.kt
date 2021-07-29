package ru.jelezov.stockstracker.ui.stock.viewModel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
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

    fun updateFavorite(stock: StocksData) =
        viewModelScope.launch(Dispatchers.IO) { repository.updateFavorite(stock) }

    fun searchStocks(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stocks.postValue(repository.searchStocks(query))
        }
    }


}