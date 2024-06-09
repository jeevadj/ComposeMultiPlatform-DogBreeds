package viewmodel

import androidx.compose.animation.fadeIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import networkhelpers.RandomImageResult
import networkhelpers.responses.RandomImageResponse
import repositories.DogRepo

sealed class ImageUiState {
    data object INIT : ImageUiState()
    data object LOADING : ImageUiState()
    data class LOAD_IMAGE(var imageUrl : String) : ImageUiState()
    data object ERROR : ImageUiState()
}

data class DetailUiState(
    var imageState : ImageUiState = ImageUiState.INIT,
    var showLoader : Boolean = false
)

class DetailViewModel(val dogRepo: DogRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    fun loadImage(breed : String?){
        println("load image ; $breed")
        if(breed == null){
            _uiState.update { it.copy(imageState = ImageUiState.ERROR, showLoader = false) }
        } else {
            _uiState.update { it.copy(showLoader = true, imageState = ImageUiState.LOADING) }
            println("load image ; loading")
            CoroutineScope(SupervisorJob()).launch {
                val result = dogRepo.getDogImage(breed)
                println("load image ; loading : $result")
                when(result){
                    is RandomImageResult.onFailed -> _uiState.update { it.copy(imageState = ImageUiState.ERROR, showLoader = false) }
                    is RandomImageResult.OnSuccess -> _uiState.update { it.copy(imageState = ImageUiState.LOAD_IMAGE(result.randomImageResponse.message), showLoader = false) }
                }
            }
        }

    }

    override fun onCleared() {
        println("OnCleared detailViewModel ${uiState.value}")
        clear()
        println("OnCleared detailViewModel ${uiState.value}")
        super.onCleared()
        println("OnCleared detailViewModel ${uiState.value}")

    }

    fun clear(){
        _uiState.update { DetailUiState() }
    }
}