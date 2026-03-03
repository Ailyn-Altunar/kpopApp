package com.ailyn.kpopui.features.inventario.presentation.screens

import com.ailyn.kpopui.features.inventario.domain.entities.Inventario

data class InventarioUIState(
    val isLoading: Boolean = false,
    val inventario: List<Inventario> = emptyList(),
    val productos: List<Inventario> = emptyList(),
    val error: String? = null,
    val busqueda: String = ""
)
