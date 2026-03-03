package com.ailyn.kpopui.features.agregarproducto.domain.entities

data class AgregarProducto(
    val nombre: String,
    val grupo: String,
    val categoria: String,
    val precio: Double,
    val stock: Int,
    val descripcion: String?
)