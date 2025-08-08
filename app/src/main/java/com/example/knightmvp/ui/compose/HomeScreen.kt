package com.example.knightmvp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun HomeScreen(
    onAboutClick: () -> Unit,
    onSettingsClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    var isLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val presenter = remember { HomePresenter() }
    val view = remember {
        object : HomeView {
            override fun showLoading(loading: Boolean) { isLoading = loading }
            override fun showMessage(msg: String) { message = msg; error = null }
            override fun showError(err: String) { error = err; message = "" }
            override fun navigateToSecond() { /* больше не используется */ }
        }
    }

    LaunchedEffect(Unit) { presenter.attachView(view) }
    DisposableEffect(Unit) { onDispose { presenter.detachView() } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // --- TopBar с кнопкой меню ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Меню"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Главная", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        // --- Weather Card ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Левая часть: температура и иконка
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Cloud,
                        contentDescription = "Облачно",
                        tint = Color(0xFFfbc02d),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "36",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(text = "°C", fontSize = 24.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Вероятность осадков: 0%", fontSize = 14.sp, color = Color.Gray)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.WaterDrop,
                        contentDescription = "Влажность",
                        tint = Color(0xFF42a5f5),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Влажность: 19%", fontSize = 14.sp, color = Color.Gray)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Air,
                        contentDescription = "Ветер",
                        tint = Color(0xFF90a4ae),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Ветер: 5 м/с", fontSize = 14.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            // Правая часть: дата, время, описание
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "Погода", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "четверг 11:00", fontSize = 16.sp, color = Color.Gray)
                Text(text = "Переменная облачность", fontSize = 16.sp, color = Color.Black)
            }
        }
        // --- End Weather Card ---

        Spacer(modifier = Modifier.height(32.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text(text = error!!, color = Color.Red)
        } else {
            Text(text = message)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onAboutClick) {
                Text("О нас")
            }
            Button(onClick = onSettingsClick) {
                Text("Настройки")
            }
        }
    }
}
