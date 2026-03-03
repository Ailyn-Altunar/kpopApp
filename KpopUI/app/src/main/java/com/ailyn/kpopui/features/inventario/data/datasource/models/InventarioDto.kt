package com.ailyn.kpopui.features.inventario.data.datasource.models

data class InventarioDto (
    val idProducto: Int,
    val nombre: String,
    val grupo: String,
    val categoria: String,
    val precio: Double,
    val stock: Int,
    val descripcion: String?,
    val imagen: String?
)