package screens

import UI.BarChart
import UI.BarChart2
import UI.BarChartData
import UI.PieChart
import UI.PieChartData
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChartScreen(){
    Box(modifier = Modifier.fillMaxSize().padding(10.dp), contentAlignment = Alignment.Center) {
//        BarChart2(
//            BarChartData(
//                xAxisData = listOf(1f,2f,3f,4f,5f,6f,7f,8f,9f,10f),
//                yAxisData = listOf(1f,2f,3f,4f,5f,6f,7f,8f,9f,10f),
//                value = listOf(4f,4f,4f,4f,4f,4f,4f,4f,4f,4f)
//            )
//        )
        Box(Modifier.fillMaxHeight(0.5f)){
            PieChart(PieChartData(list = listOf(0.3f, 0.4f, 0.15f, 0.15f)))
        }
    }
}