package com.pulse.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF0D0B13)
private val SurfaceDark = Color(0xFF191522)
private val Purple = Color(0xFF9670FF)
private val Muted = Color(0xFF9992A8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PulseApp()
        }
    }
}

@Composable
fun PulseApp() {
    var selected by remember { mutableIntStateOf(0) }

    val titles = listOf(
        "Главная", "Gem Bot", "RSS Bot", "Аккаунты", "Настройки"
    )

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Background,
            surface = SurfaceDark,
            primary = Purple
        )
    ) {
        Scaffold(
            containerColor = Background,
            bottomBar = {
                NavigationBar(
                    containerColor = SurfaceDark,
                    contentColor = Color.White
                ) {
                    val icons = listOf(
                        Icons.Rounded.Home,
                        Icons.Rounded.Diamond,
                        Icons.Rounded.Inventory2,
                        Icons.Rounded.People,
                        Icons.Rounded.Settings
                    )

                    titles.forEachIndexed { index, title ->
                        NavigationBarItem(
                            selected = selected == index,
                            onClick = { selected = index },
                            icon = {
                                Icon(icons[index], contentDescription = title)
                            },
                            label = { Text(title, fontSize = 9.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Purple,
                                selectedTextColor = Purple,
                                indicatorColor = Purple.copy(alpha = 0.15),
                                unselectedIconColor = Muted,
                                unselectedTextColor = Muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(padding),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        "PULSE",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        titles[selected],
                        color = Muted,
                        fontSize = 14.sp
                    )
                }

                when (selected) {
                    0 -> {
                        item {
                            CardBlock {
                                Text(
                                    "Сервис не подключён",
                                    color = Color.White,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Здесь будет отображаться состояние системы",
                                    color = Muted,
                                    fontSize = 13.sp
                                )
                                Spacer(Modifier.height(8.dp))
                                StatusLabel("Ожидает подключения")
                            }
                        }
                        item {
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                StatCard("0", "Алмазы", Modifier.weight(1f))
                                StatCard("0", "Аккаунты", Modifier.weight(1f))
                            }
                        }
                        item {
                            Text(
                                "Модули",
                                color = Color.White,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        item {
                            ModuleCard("Gem Bot", "Сбор алмазов", Icons.Rounded.Diamond) {
                                selected = 1
                            }
                        }
                        item {
                            ModuleCard("RSS Bot", "Сбор ресурсов", Icons.Rounded.Inventory2) {
                                selected = 2
                            }
                        }
                    }

                    1, 2 -> {
                        item {
                            CardBlock {
                                Text(
                                    titles[selected],
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    if (selected == 1) "Сбор алмазов" else "Сбор ресурсов",
                                    color = Muted
                                )
                                Spacer(Modifier.height(8.dp))
                                StatusLabel("Ожидает подключения")
                            }
                        }
                        item {
                            StatCard("0", "Сегодня собрано", Modifier.fillMaxWidth())
                        }
                    }

                    3 -> {
                        item {
                            CardBlock {
                                Icon(
                                    Icons.Rounded.People,
                                    contentDescription = null,
                                    tint = Purple,
                                    modifier = Modifier.size(36.dp)
                                )
                                Text(
                                    "Аккаунтов пока нет",
                                    color = Color.White,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Добавленные игровые аккаунты появятся здесь",
                                    color = Muted
                                )
                            }
                        }
                    }

                    4 -> {
                        item {
                            CardBlock {
                                Text("Настройки PULSE", color = Color.White, fontSize = 20.sp)
                                Text("Версия приложения: 1.0", color = Muted)
                                Text("Тема: тёмная", color = Muted)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardBlock(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = content
        )
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(value, color = Purple, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(label, color = Muted, fontSize = 13.sp)
        }
    }
}

@Composable
fun StatusLabel(text: String) {
    Surface(
        color = Purple.copy(alpha = 0.14f),
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text,
            color = Purple,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
        )
    }
}

@Composable
fun ModuleCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Purple, modifier = Modifier.size(30.dp))
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                Text(description, color = Muted, fontSize = 12.sp)
            }
            Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = Muted)
        }
    }
}
