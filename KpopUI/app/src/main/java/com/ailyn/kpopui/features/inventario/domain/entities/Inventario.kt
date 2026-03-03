package com.ailyn.kpopui.features.inventario.domain.entities

data class Inventario (
    val idProducto: Int,
    val nombre: String,
    val grupo: String,
    val categoria: String,
    val precio: Double,
    val stock: Int,
    val descripcion: String?,
    val imagen: String?
)