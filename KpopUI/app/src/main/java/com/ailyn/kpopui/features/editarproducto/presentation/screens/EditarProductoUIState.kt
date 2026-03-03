package com.ailyn.kpopui.features.editarproducto.presentation.screens

data class EditarProductoUIState(
    val isLoading: Boolean = false,
    val guardado: Boolean = false,
    val eliminado: Boolean = false,
    val error: String? = null,
    val idProducto: Int = 0,
    val nombre: String = "",
    val grupo: String = "",
    val categoria: String = "",
    val precio: String = "",
    val stock: String = "",
    val descripcion: String = "",
    val imagen: String? = null,
    val mostrarConfirmacionEliminar: Boolean = false,
    // Catálogos dinámicos
    val categorias: List<String> = emptyList(),
    val grupos: List<String> = emptyList(),
    val cargandoCatalogos: Boolean = false
)
