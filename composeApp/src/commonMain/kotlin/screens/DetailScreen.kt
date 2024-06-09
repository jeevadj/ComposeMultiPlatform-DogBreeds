package screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import koinViewModel
import kotlinproject2.composeapp.generated.resources.Res
import kotlinproject2.composeapp.generated.resources.close_24px
import org.jetbrains.compose.resources.Resource
import org.jetbrains.compose.resources.painterResource
import viewmodel.DetailUiState
import viewmodel.DetailViewModel
import viewmodel.ImageUiState

@Composable
fun DetailScreen(breed : String?, navigateUp : () -> Unit){
    val detailModel = koinViewModel<DetailViewModel>()
    val uiState by detailModel.uiState.collectAsState()
    println(uiState.imageState)
    Box(Modifier.fillMaxWidth().height(56.dp)){
        IconButton(onClick = {
            navigateUp.invoke()
        }){
            Icon(painter = painterResource(Res.drawable.close_24px), contentDescription = "")
        }
    }

    when(uiState.imageState){
        is ImageUiState.INIT -> {
            detailModel.loadImage(breed)
        }
        is ImageUiState.LOADING -> {
            Dialog(onDismissRequest = {}){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Text("Loading $breed")
                }
            }
        }
        is ImageUiState.LOAD_IMAGE -> {
            val url = (uiState.imageState as ImageUiState.LOAD_IMAGE).imageUrl
            Box(Modifier.fillMaxSize()){
                Box(Modifier.fillMaxWidth().height(56.dp).align(Alignment.TopCenter)){
                    Text(text = breed ?: "No Breed", modifier = Modifier.align(Alignment.Center), fontSize = 20.sp, textAlign = TextAlign.Center)

                }
                Text(text = url, modifier = Modifier.align(Alignment.BottomCenter), fontSize = 20.sp, textAlign = TextAlign.Center)
                KamelImage(modifier = Modifier.align(Alignment.Center), resource = asyncPainterResource(url), contentDescription = "")
            }
        }
        is ImageUiState.ERROR -> {
            Box(Modifier.fillMaxSize()){
                Text(text = "Error Loading image for $breed", modifier = Modifier.align(Alignment.Center), fontSize = 20.sp, color = Color.LightGray)
            }
        }
    }
}