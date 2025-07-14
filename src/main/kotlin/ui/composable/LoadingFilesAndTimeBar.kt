package ui.composable

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import ui.TotalPresenter
import ui.Utils
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun showLoadingFilesAndTimeBar(
    totalPresenter: TotalPresenter,
    currentTheme: Theme
) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ) {
        if(totalPresenter.isLoadingFiles) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(500.dp)
                    .align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val percent = "${(totalPresenter.totalProgressState * 100).toInt()}%"
                    Text(
                        text = "${totalPresenter.titleOperation} $percent",
                        color = if(currentTheme == Theme.BLACK) Color.White else Color.Black,
                        fontSize = 10.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier.width(100.dp)
                        ){
                            val textProgress = "${Utils.formatSizeToString(totalPresenter.allCopiedBytes)}/${Utils.formatSizeToString(totalPresenter.totalBytes)}"
                            Text(
                                modifier = Modifier.align(Alignment.CenterEnd).padding(end = 10.dp),
                                text = textProgress,
                                fontSize = 10.sp,
                                color = if(currentTheme == Theme.BLACK) Color.White else Color.Black
                            )
                        }
                        LinearProgressIndicator(
                            modifier = Modifier
                                .width(300.dp)
                                .height(9.dp)
                                .clip(shape = RoundedCornerShape(10.dp)),
                            progress = totalPresenter.totalProgressState,
                            color = if(currentTheme == Theme.BLACK) Color.Yellow else Color(140, 0, 255),
                            backgroundColor = if(currentTheme == Theme.BLACK) Color.Yellow.copy(alpha = 0.2f) else Color(140, 0, 255).copy(alpha = 0.2f)
                        )
                        Box(
                            modifier = Modifier.width(100.dp)
                        ){
                            val textProgressNumber = "${totalPresenter.countFilesCopied}/${totalPresenter.totalCountFiles}"
                            Text(
                                modifier = Modifier.align(Alignment.CenterStart).padding(start = 10.dp),
                                text = textProgressNumber,
                                fontSize = 10.sp,
                                color = if(currentTheme == Theme.BLACK) Color.White else Color.Black
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Spacer(Modifier.width(100.dp))
                        LinearProgressIndicator(
                            modifier = Modifier
                                .width(300.dp)
                                .height(3.dp)
                                .clip(shape = RoundedCornerShape(10.dp)),
                            progress = totalPresenter.progressState,
                            color = if(currentTheme == Theme.BLACK) Color.Yellow else Color(140, 0, 255),
                            backgroundColor = if(currentTheme == Theme.BLACK) Color.Yellow.copy(alpha = 0.2f) else Color(140, 0, 255).copy(alpha = 0.2f)
                        )
                        Spacer(Modifier.width(100.dp))
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        val fileName = if(totalPresenter.currentFileCoping.length > 50) "${totalPresenter.currentFileCoping.take(47)}..." else totalPresenter.currentFileCoping
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = fileName,
                            fontSize = 10.sp,
                            color = if(currentTheme == Theme.BLACK) Color.White else Color.Black
                        )
                    }
                }
            }
        }
        else
        {
            val timeFlow = remember { MutableStateFlow(LocalTime.now()) }
            val currentTime by timeFlow.collectAsState()

            val dateFlow = remember { MutableStateFlow(LocalDate.now()) }
            val currentDate by dateFlow.collectAsState()

            val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTime = currentTime.format(formatterTime)

            val formatterDate = DateTimeFormatter.ofPattern("d MMMM")
            val formattedDate = currentDate.format(formatterDate)

            LaunchedEffect(Unit) {
                while(true) {
                    delay(1000)
                    timeFlow.value = LocalTime.now()
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                .align(Alignment.Center)
            ){
                Text(
                    text = formattedTime,
                    color = if(currentTheme == Theme.BLACK) Color.White else Color.Black,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 15.sp
                )
                Text(
                    text = formattedDate,
                    color = if(currentTheme == Theme.BLACK) Color.White else Color.Black,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 15.sp
                )
            }
        }
    }
}