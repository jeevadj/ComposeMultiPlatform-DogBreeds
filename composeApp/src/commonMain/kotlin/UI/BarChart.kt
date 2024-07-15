package UI

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

data class BarChartData(
    var xAxisData : List<Float>,
    var yAxisData : List<Float>,
    var value : List<Float>,
)

@Composable
fun BarChart(chartData: BarChartData){
    var localDensity = LocalDensity.current
    var rect by remember { mutableStateOf<Rect?>(null) }
    var size by remember { mutableStateOf(Pair(0.dp,0.dp)) }
    Box(modifier = Modifier.fillMaxSize().onGloballyPositioned {
        rect = it.boundsInParent()
        size =  with(localDensity) { Pair(it.size.width.toDp(),it.size.height.toDp()) }
    }
    ){

        Row {
            val xSingleOffset = size.second.div(chartData.yAxisData.size)
            val ySingleOffset = size.first.div(chartData.xAxisData.size.plus(1))
            if(rect != null){
                Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.TopStart){
                    val chartSize = chartData.yAxisData.size
                    chartData.yAxisData.forEachIndexed { index , data ->
                        Text(text = data.toString(), modifier = Modifier.offset(y = xSingleOffset.times(chartSize.minus(index+1))), color = Color.Black)
                    }
                }
            }

            Column {
//                .background(Color.LightGray.copy(alpha = 0.4f))
                Canvas(modifier = Modifier.padding(horizontal = 10.dp)){
                    if(rect != null){
                        drawLine(Color.Black, rect!!.topLeft, rect!!.bottomLeft, strokeWidth = 5f)
                        drawLine(Color.Black, rect!!.bottomLeft, rect!!.bottomRight, strokeWidth = 5f)
                        val singleChartHeight = rect!!.height.div(chartData.value.size)
                        val singleChartWidth = rect!!.width.div(chartData.value.size)
                        println("Bow : singleChartHeight : $singleChartHeight singleChartWidth : $singleChartWidth")
                        chartData.value.forEachIndexed { index, fl ->
                            drawRect(Color.Red.copy(alpha = 0.5f), topLeft = Offset(y = singleChartHeight.times(fl), x = singleChartWidth.minus(20).times(index)), size = Size(width = singleChartWidth.minus(100), height =singleChartHeight.times(fl) ))
                        }
                    }
                }
                if(rect != null){
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomStart){
                        val chartSize = chartData.xAxisData.size
                        chartData.xAxisData.forEachIndexed { index , data ->
                            Text(text = data.toString(), modifier = Modifier.offset(x = ySingleOffset.times(index+1), y = size.second), color = Color.Black)
                        }
                    }
                }
            }


        }


    }
}

@Composable
fun BarChart2(chartData: BarChartData){
    var localDensity = LocalDensity.current
    var rect by remember { mutableStateOf<Rect?>(null) }
    var size by remember { mutableStateOf(Pair(0.dp,0.dp)) }
    var sizeList = mutableStateListOf<Dp>()
    var sizeFList = mutableStateListOf<Float>()

    Box(modifier = Modifier.fillMaxSize().onGloballyPositioned {
        rect = it.boundsInParent()
        size =  with(localDensity) { Pair(it.size.width.toDp(),it.size.height.toDp()) }
    }
    ){
        val ySingleOffset = size.second.div(chartData.yAxisData.size)
        val xSingleOffset = size.first.div(chartData.xAxisData.size.plus(1))
        println("Bow : sizeFList : ${sizeFList.size}")
        if(sizeFList.isNotEmpty() && sizeFList.size == chartData.xAxisData.size){

        }
        Row(verticalAlignment = Alignment.Bottom){

            if (rect != null) {
//                Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.TopStart) {
//                    val chartSize = chartData.yAxisData.size
//                    chartData.yAxisData.forEachIndexed { index, data ->
//                        Text(
//                            text = data.toString(),
//                            modifier = Modifier.offset(y = ySingleOffset.times(chartSize.minus(index + 1))),
//                            color = Color.Black
//                        )
//                    }
//                }

                LazyColumn(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceEvenly) {

                    itemsIndexed(chartData.yAxisData.reversed()){ index, yData ->
                        Text(
                            text = yData.toString(),
                            modifier = Modifier,
                            color = Color.Black
                        )
                    }
                    item {
                        Spacer(Modifier)
                    }
                }

                LazyRow(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceEvenly, contentPadding = PaddingValues(10.dp)) {
                    itemsIndexed(chartData.xAxisData){ index, xData ->
                        Column {
                            var tempSize by remember { mutableStateOf(Pair(0.dp,0.dp)) }
                            var tempRect by remember { mutableStateOf<Rect?>(null) }
                            if(tempRect != null){
                                Canvas(Modifier.fillMaxHeight(0.9f)){
                                    val singleChartHeight = rect!!.height.div(chartData.value.size)
                                    val singleChartWidth = rect!!.width.div(chartData.value.size)
                                    chartData.value.forEachIndexed { index, fl ->
                                        println("Bow : index = $index fl : $fl singleChartWidth : $singleChartWidth singleChartHeight : $singleChartHeight")
                                        drawRect(Color.Red.copy(alpha = 0.5f), topLeft = Offset(x = tempRect!!.left , y = tempRect!!.top ), size = Size(width = tempRect!!.width,height = singleChartHeight.times(fl) ))
                                    }
                                }
                            }
                            Text(
                                text = xData.toString(),
                                modifier = Modifier.fillMaxHeight(1f).onGloballyPositioned {

//                                    with(localDensity){ sizeList.add(index,it.boundsInParent().left.toDp())}
//                                    sizeFList.add(index,it.boundsInParent().left)
                                    tempRect = it.boundsInParent()
                                    tempSize = with(localDensity) { Pair(it.size.width.toDp(),it.size.height.toDp()) }
                                },
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}