package com.ailyn.kpopui.features.editarproducto.data.datasource.models

import com.google.gson.annotations.SerializedName

data class EditarProductoDto(
    val idProducto: Int = 0,
    val nombre: String = "",
    val grupo: String = "",
    val categoria: String = "",
    val precio: Double = 0.0,
    val stock: Int = 0,
    val descripcion: String? = null,
    @SerializedName("imagen_url")
    val imagen: String? = null
)
