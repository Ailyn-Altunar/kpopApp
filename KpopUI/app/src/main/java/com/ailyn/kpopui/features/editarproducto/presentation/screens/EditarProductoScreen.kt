package com.ailyn.kpopui.features.editarproducto.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import com.ailyn.kpopui.features.editarproducto.presentation.viewmodels.EditarProductoViewModel
import com.ailyn.kpopui.features.editarproducto.presentation.viewmodels.EditarProductoViewModelFactory
import com.ailyn.kpopui.ui.theme.KpopBackground
import com.ailyn.kpopui.ui.theme.KpopPurple
import com.ailyn.kpopui.ui.theme.KpopPurpleLight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarProductoScreen(
    productoId: Int,
    factory: EditarProductoViewModelFactory,
    onVolver: () -> Unit,
    onEliminado: () -> Unit
) {
    val vm: EditarProductoViewModel = viewModel(factory = factory)
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    var dropdownExpanded by remember { mutableStateOf(false) }
    // Modo: false = solo lectura (detalle), true = editando
    var modoEditar by remember { mutableStateOf(false) }

    LaunchedEffect(productoId) { vm.cargar(productoId) }
    LaunchedEffect(uiState.guardado) { if (uiState.guardado) onVolver() }
    LaunchedEffect(uiState.eliminado) { if (uiState.eliminado) onEliminado() }

    // Diálogo confirmación eliminar
    if (uiState.mostrarConfirmacionEliminar) {
        AlertDialog(
            onDismissRequest = vm::ocultarConfirmacionEliminar,
            title = { Text("¿Eliminar este producto?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = vm::eliminar,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) { Text("Eliminar permanentemente") }
            },
            dismissButton = {
                TextButton(onClick = vm::ocultarConfirmacionEliminar) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        containerColor = KpopBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (modoEditar) "Editar Producto" else "Detalles del Producto",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { if (modoEditar) modoEditar = false else onVolver() }) {
                        Icon(Icons.Default.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KpopPurple)
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator(color = KpopPurple) }

            uiState.error != null -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text(uiState.error ?: "", color = Color.Red) }

            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Imagen del producto
                if (!uiState.imagen.isNullOrBlank()) {
                    AsyncImage(
                        model = uiState.imagen,
                        contentDescription = uiState.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                if (!modoEditar) {
                    // ── MODO DETALLE (solo lectura) ──
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(uiState.nombre, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text(uiState.grupo, color = Color.Gray, fontSize = 14.sp)

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Categoría", color = Color.Gray, fontSize = 13.sp)
                                Spacer(Modifier.width(8.dp))
                                Surface(shape = RoundedCornerShape(20.dp), color = KpopPurpleLight) {
                                    Text(
                                        uiState.categoria,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                        color = KpopPurple,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Column {
                                Text("Precio", color = Color.Gray, fontSize = 13.sp)
                                Text(
                                    "$%.2f".format(uiState.precio.toDoubleOrNull() ?: 0.0),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    color = KpopPurple
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Stock Disponible", color = Color.Gray, fontSize = 13.sp)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    uiState.stock,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = KpopPurple
                                )
                                Text(" piezas", color = Color.Gray, fontSize = 13.sp)
                            }

                            if (!uiState.descripcion.isNullOrBlank()) {
                                Column {
                                    Text("Descripción", color = Color.Gray, fontSize = 13.sp)
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = KpopPurpleLight,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            uiState.descripcion,
                                            modifier = Modifier.padding(10.dp),
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Botones detalle
                    Button(
                        onClick = { modoEditar = true },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KpopPurple)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Editar Información", fontWeight = FontWeight.SemiBold)
                    }

                    OutlinedButton(
                        onClick = vm::mostrarConfirmacionEliminar,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                        border = ButtonDefaults.outlinedButtonBorder.copy()
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Eliminar Producto", fontWeight = FontWeight.SemiBold)
                    }

                } else {
                    // ── MODO EDITAR ──
                    OutlinedTextField(
                        value = uiState.nombre,
                        onValueChange = vm::onNombreChange,
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = KpopPurple,
                            focusedLabelColor = KpopPurple
                        )
                    )
                    OutlinedTextField(
                        value = uiState.grupo,
                        onValueChange = vm::onGrupoChange,
                        label = { Text("Grupo") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = KpopPurple,
                            focusedLabelColor = KpopPurple
                        )
                    )

                    // Categoría dropdown
                    ExposedDropdownMenuBox(
                        expanded = dropdownExpanded,
                        onExpandedChange = { dropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = uiState.categoria,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Categoría") },
                            trailingIcon = {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = KpopPurple)
                            },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = KpopPurple,
                                focusedLabelColor = KpopPurple
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false }
                        ) {
                            uiState.categorias.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat) },
                                    onClick = {
                                        vm.onCategoriaChange(cat)
                                        dropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = uiState.precio,
                        onValueChange = vm::onPrecioChange,
                        label = { Text("Precio") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = KpopPurple,
                            focusedLabelColor = KpopPurple
                        )
                    )
                    OutlinedTextField(
                        value = uiState.stock,
                        onValueChange = vm::onStockChange,
                        label = { Text("Stock") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = KpopPurple,
                            focusedLabelColor = KpopPurple
                        )
                    )
                    OutlinedTextField(
                        value = uiState.descripcion,
                        onValueChange = vm::onDescripcionChange,
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = KpopPurple,
                            focusedLabelColor = KpopPurple
                        )
                    )

                    if (uiState.error != null) {
                        Text(uiState.error ?: "", color = Color.Red)
                    }

                    Button(
                        onClick = vm::guardar,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        enabled = !uiState.isLoading,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KpopPurple)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = Color.White)
                        } else {
                            Icon(Icons.Default.Edit, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Guardar Cambios", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}