package viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import repositories.DogRepo

data class ListScreenUiState(
    var listStatus : ListStatus = ListStatus.Initiated,
    var map : HashMap<String, ArrayList<String>> = hashMapOf()
)
class ListViewModel(val dogRepo: DogRepo) : ViewModel() {

    private val _listState = MutableStateFlow(ListScreenUiState())
    val listUiState = _listState.asStateFlow()

    fun updateListStatus(listStatus: ListStatus){
        _listState.update {
            it.copy(listStatus = listStatus)
        }
    }
    fun loadListFromApi(){
        updateListStatus(ListStatus.Loading)
        dogRepo.getDogBreeds { loadedMap ->
            _listState.update {
                it.copy(listStatus = ListStatus.Loaded, map = loadedMap )
            }
        }
    }
}

enum class ListStatus {
    Initiated, Loading, Loaded
}