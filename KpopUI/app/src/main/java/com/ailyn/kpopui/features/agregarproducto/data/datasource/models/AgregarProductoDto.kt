package com.ailyn.kpopui.features.agregarproducto.data.datasource.models

import com.google.gson.annotations.SerializedName

data class AgregarProductoDto(
    val nombre: String,
    val grupo: String,
    val categoria: String,
    val precio: Double,
    val stock: Int,
    val descripcion: String?,
    @SerializedName("imagen_url")
    val imagenUrl: String? = null
)
