package UI

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.random.Random

val colorList = listOf(Color.Red, Color.Black, Color.Blue, Color.Cyan, Color.LightGray, Color.DarkGray, Color.Gray, Color.Green, Color.Magenta, Color.Yellow)

data class PieChartData(var list : List<Float>)
@Composable
fun PieChart( pieChartData: PieChartData){

    Column {
        Column(Modifier.fillMaxSize()){
            Canvas(Modifier.fillMaxSize().padding(10.dp)){
                var startAngle = 270f
                val pieChartColorList = arrayListOf<Color>().apply { this.addAll(colorList) }
                pieChartData.list.forEachIndexed { index, fl ->
                    val angleToBeAdded = fl * 360
                    val colorIndex = Random.nextInt(pieChartColorList.size.minus(1))
                    val color = pieChartColorList.removeAt(colorIndex)
                    drawArc(color, startAngle, angleToBeAdded, useCenter = false, style = Stroke(width = 30.dp.toPx(), join = StrokeJoin.Round, cap = StrokeCap.Round))
                    startAngle = startAngle.plus(angleToBeAdded)
                }
            }
        }

//        Column(Modifier.weight(1f).fillMaxSize().background(Color.Blue.copy(alpha = 0.3f))){
//            Canvas(Modifier.fillMaxSize()){
//                var startAngle = 270f
//                val pieChartColorList = arrayListOf<Color>().apply { this.addAll(colorList) }
//                pieChartData.list.forEachIndexed { index, fl ->
//                    val angleToBeAdded = fl * 360
//                    val colorIndex = Random.nextInt(pieChartColorList.size.minus(1))
//                    val color = pieChartColorList.removeAt(colorIndex)
//                    drawArc(color, startAngle, angleToBeAdded, useCenter = true, style = Fill)
//                    startAngle = startAngle.plus(angleToBeAdded)
//                }
//            }
//        }
    }
}
