package com.ailyn.kpopui.features.editarproducto.domain.entities

data class ProductoDetalle(
    val idProducto: Int,
    val nombre: String,
    val grupo: String,
    val categoria: String,
    val precio: Double,
    val stock: Int,
    val descripcion: String?,
    val imagen: String?
)