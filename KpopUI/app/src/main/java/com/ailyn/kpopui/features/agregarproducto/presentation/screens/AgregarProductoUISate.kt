package com.ailyn.kpopui.features.agregarproducto.presentation.screens

data class AgregarProductoUISate(
    val isLoading: Boolean = false,
    val guardado: Boolean = false,
    val error: String? = null,
    val nombre: String = "",
    val grupo: String = "",
    val categoria: String = "",
    val precio: String = "",
    val stock: String = "",
    val descripcion: String = "",
    val imagenUrl: String? = null,
    // Listas cargadas desde la API
    val categorias: List<String> = emptyList(),
    val grupos: List<String> = emptyList(),
    val cargandoCatalogos: Boolean = false
)
