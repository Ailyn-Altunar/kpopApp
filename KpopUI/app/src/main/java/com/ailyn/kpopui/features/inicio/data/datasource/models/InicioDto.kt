package com.ailyn.kpopui.features.inicio.data.datasource.models

data class InicioDto (
    val totalProductos: Int,
    val totalStock: Int,
    val porCategoria: Map<String, Int>
)