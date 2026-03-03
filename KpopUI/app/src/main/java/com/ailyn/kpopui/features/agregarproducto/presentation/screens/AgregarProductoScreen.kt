package com.ailyn.kpopui.features.agregarproducto.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ailyn.kpopui.features.agregarproducto.presentation.viewmodels.AgregarProductoViewModel
import com.ailyn.kpopui.features.agregarproducto.presentation.viewmodels.AgregarProductoViewModelFactory
import com.ailyn.kpopui.ui.theme.KpopBackground
import com.ailyn.kpopui.ui.theme.KpopPurple
import com.ailyn.kpopui.ui.theme.KpopPurpleLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarProductoScreen(
    factory: AgregarProductoViewModelFactory,
    onVolver: () -> Unit,
    onGuardado: () -> Unit
) {
    val vm: AgregarProductoViewModel = viewModel(factory = factory)
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    var dropdownCategoriaExpanded by remember { mutableStateOf(false) }
    var dropdownGrupoExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.guardado) {
        if (uiState.guardado) onGuardado()
    }

    Scaffold(
        containerColor = KpopBackground,
        topBar = {
            TopAppBar(
                title = { Text("Agregar Producto", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KpopPurple)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Preview imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(KpopPurpleLight),
                contentAlignment = Alignment.Center
            ) {
                if (!uiState.imagenUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = uiState.imagenUrl,
                        contentDescription = "Preview imagen",
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Add, null, tint = KpopPurple, modifier = Modifier.size(42.dp))
                        Text("Agregar imagen", color = KpopPurple, fontSize = 13.sp)
                    }
                }
            }

            // URL imagen
            OutlinedTextField(
                value = uiState.imagenUrl ?: "",
                onValueChange = vm::onImagenUrlChange,
                label = { Text("URL de imagen (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KpopPurple, focusedLabelColor = KpopPurple
                )
            )

            // Nombre
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = vm::onNombreChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KpopPurple, focusedLabelColor = KpopPurple
                )
            )

            // Grupo — dropdown dinámico
            ExposedDropdownMenuBox(
                expanded = dropdownGrupoExpanded,
                onExpandedChange = { if (!uiState.cargandoCatalogos) dropdownGrupoExpanded = it }
            ) {
                OutlinedTextField(
                    value = if (uiState.cargandoCatalogos) "Cargando..." else uiState.grupo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Grupo") },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, null, tint = KpopPurple)
                    },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = KpopPurple, focusedLabelColor = KpopPurple
                    )
                )
                ExposedDropdownMenu(
                    expanded = dropdownGrupoExpanded,
                    onDismissRequest = { dropdownGrupoExpanded = false }
                ) {
                    uiState.grupos.forEach { grupo ->
                        DropdownMenuItem(
                            text = { Text(grupo) },
                            onClick = {
                                vm.onGrupoChange(grupo)
                                dropdownGrupoExpanded = false
                            }
                        )
                    }
                }
            }

            // Categoría — dropdown dinámico
            ExposedDropdownMenuBox(
                expanded = dropdownCategoriaExpanded,
                onExpandedChange = { if (!uiState.cargandoCatalogos) dropdownCategoriaExpanded = it }
            ) {
                OutlinedTextField(
                    value = if (uiState.cargandoCatalogos) "Cargando..." else uiState.categoria,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, null, tint = KpopPurple)
                    },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = KpopPurple, focusedLabelColor = KpopPurple
                    )
                )
                ExposedDropdownMenu(
                    expanded = dropdownCategoriaExpanded,
                    onDismissRequest = { dropdownCategoriaExpanded = false }
                ) {
                    uiState.categorias.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                vm.onCategoriaChange(cat)
                                dropdownCategoriaExpanded = false
                            }
                        )
                    }
                }
            }

            // Precio
            OutlinedTextField(
                value = uiState.precio,
                onValueChange = vm::onPrecioChange,
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KpopPurple, focusedLabelColor = KpopPurple
                )
            )

            // Stock
            OutlinedTextField(
                value = uiState.stock,
                onValueChange = vm::onStockChange,
                label = { Text("Stock") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KpopPurple, focusedLabelColor = KpopPurple
                )
            )

            // Descripción
            OutlinedTextField(
                value = uiState.descripcion,
                onValueChange = vm::onDescripcionChange,
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KpopPurple, focusedLabelColor = KpopPurple
                )
            )

            if (uiState.error != null) {
                Text(uiState.error ?: "", color = Color.Red)
            }

            Button(
                onClick = vm::guardar,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                enabled = !uiState.isLoading && !uiState.cargandoCatalogos,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KpopPurple)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = Color.White)
                } else {
                    Text("Confirmar + Guardar", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
            }
        }
    }
}
