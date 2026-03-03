package com.ailyn.kpopui.features.inicio.domain.entities

data class Inicio(
    val totalProductos: Int,
    val totalStock: Int,
    val porCategoria: Map<String, Int>
)