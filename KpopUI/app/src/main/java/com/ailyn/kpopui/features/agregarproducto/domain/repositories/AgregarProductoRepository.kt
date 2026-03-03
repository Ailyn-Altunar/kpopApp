package com.ailyn.kpopui.features.agregarproducto.domain.repositories

import com.ailyn.kpopui.features.agregarproducto.domain.entities.ProductoCreado

interface AgregarProductoRepository {
    suspend fun crearProducto(
        nombre: String,
        grupo: String,
        categoria: String,
        precio: Double,
        stock: Int,
        descripcion: String?,
        imagenUrl: String? = null
    ): ProductoCreado
}
