package com.ailyn.kpopui.features.agregarproducto.data.datasource.models

data class ProductoCreadoDto(
    val idProducto: Int,
    val nombre: String,
    val grupo: String,
    val categoria: String,
    val precio: Double,
    val stock: Int,
    val descripcion: String?,
    val imagen: String?
)