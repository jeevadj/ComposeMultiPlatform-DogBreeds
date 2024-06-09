import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlinproject2.composeapp.generated.resources.Res
import kotlinproject2.composeapp.generated.resources.compose_multiplatform
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.KoinContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.koinApplication
import org.koin.mp.KoinPlatform
import org.koin.mp.KoinPlatformTools
import screens.DetailScreen
import viewmodel.CommonViewModel
import viewmodel.ListStatus
import viewmodel.ListViewModel

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            val navController = rememberNavController()
            val commonViewModel = koinViewModel<CommonViewModel>()

            NavHost(navController = navController, startDestination = "ListScreen") {
                composable("ListScreen") {
                    ListScreen(onItemClicked = { breedName ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("breed",breedName)
                        navController.navigate("ViewBreed")
                    })
                }

                composable("ViewBreed"){
                    val breed = navController.previousBackStackEntry?.savedStateHandle?.get<String>("breed")
                    DetailScreen(breed){
                        navController.previousBackStackEntry?.savedStateHandle?.remove<String>("breed")
                        navController.navigateUp()
                    }
                }
            }

        }
    }


}

@Composable
fun ListScreen(onItemClicked : (String) -> Unit) {

    val screenModel = koinViewModel<ListViewModel>()
    val uiState by screenModel.listUiState.collectAsState()

    when (uiState.listStatus) {
        ListStatus.Initiated -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(onClick = {
                    screenModel.loadListFromApi()
                }) {
                    Text("Fetch Data")
                }
            }
        }

        ListStatus.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        ListStatus.Loaded -> {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(Modifier.fillMaxWidth().height(56.dp), contentAlignment = Alignment.Center) {
                    Text("Dog Breeds", color = Color.Gray)
                }
                LazyColumn(Modifier.padding(10.dp)) {
                    items(uiState.map.keys.toList().sorted()) { breed ->
                        val subBreeds = uiState.map[breed]
                        var showParagraph by remember { mutableStateOf(false) }
                        Card(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 5.dp), elevation = 2.dp
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier.fillParentMaxWidth().heightIn(min = 50.dp)
                                        .clickable {
                                            if(subBreeds.isNullOrEmpty()){
                                                onItemClicked.invoke(breed)
                                            } else {
                                                showParagraph = showParagraph.not()
                                            }

                                        }) {
                                    Text(
                                        text = breed,
                                        modifier = Modifier.align(Alignment.Center),
                                        textAlign = TextAlign.Center
                                    )
                                    if (subBreeds.isNullOrEmpty().not()) {
                                        Image(
                                            imageVector = Icons.Filled.ArrowDropDown,
                                            modifier = Modifier.padding(10.dp)
                                                .align(Alignment.CenterEnd),
                                            contentDescription = ""
                                        )
                                    }
                                }

                                AnimatedVisibility(showParagraph) {
                                    Divider()
                                    Column(
                                        Modifier.fillParentMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        subBreeds?.forEachIndexed { index, subBreed ->
                                            Box(
                                                modifier = Modifier.fillParentMaxWidth()
                                                    .heightIn(min = 50.dp)
                                                    .background(Color.LightGray.copy(alpha = 0.6f))
                                                    .clickable {
                                                        onItemClicked.invoke("$breed/$subBreed")
                                                    }
                                            ) {
                                                Text(
                                                    text = subBreed,
                                                    modifier = Modifier.align(Alignment.Center),
                                                    textAlign = TextAlign.Center
                                                )
                                                if (index != uiState.map[breed]?.lastIndex) {
                                                    Divider(modifier = Modifier.align(Alignment.BottomCenter))
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

        }
    }


}

@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}