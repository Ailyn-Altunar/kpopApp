package com.ailyn.kpopui.features.inicio.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ailyn.kpopui.features.inicio.presentation.viewmodels.InicioViewModel
import com.ailyn.kpopui.features.inicio.presentation.viewmodels.InicioViewModelFactory
import com.ailyn.kpopui.ui.theme.KpopPurple
import com.ailyn.kpopui.ui.theme.KpopPurpleLight
import com.ailyn.kpopui.ui.theme.KpopBackground

// Mapea nombre de categoría a ícono (solo íconos del paquete default)
private fun iconoParaCategoria(nombre: String): ImageVector = when {
    nombre.lowercase().contains("album") ||
            nombre.lowercase().contains("álbum") -> Icons.Default.MusicNote
    nombre.lowercase().contains("light") ||
            nombre.lowercase().contains("stick") -> Icons.Default.Star
    else                                  -> Icons.Default.Inventory2
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    factory: InicioViewModelFactory,
    onIrInventario: () -> Unit
) {
    val vm: InicioViewModel = viewModel(factory = factory)
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = KpopBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "KPOP Store",
                        color = KpopPurple,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = KpopBackground
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(color = KpopPurple)
                uiState.error != null -> Text(uiState.error ?: "Error", color = Color.Red)
                else -> Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Tarjeta "Bienvenido"
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Text(
                            text = "Bienvenido",
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 14.dp),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                    }

                    // Cards de categorías con ícono
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        uiState.resumen?.porCategoria?.entries?.take(2)?.forEach { (categoria, cantidad) ->
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .background(KpopPurpleLight, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = iconoParaCategoria(categoria),
                                            contentDescription = categoria,
                                            tint = KpopPurple,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                    Text(
                                        text = "$cantidad",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 28.sp,
                                        color = KpopPurple
                                    )
                                    Text(
                                        text = if (categoria.endsWith("s")) categoria else "${categoria}s",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    // Botón ir al inventario
                    Button(
                        onClick = onIrInventario,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KpopPurple)
                    ) {
                        Text(
                            "Al inventario →",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}