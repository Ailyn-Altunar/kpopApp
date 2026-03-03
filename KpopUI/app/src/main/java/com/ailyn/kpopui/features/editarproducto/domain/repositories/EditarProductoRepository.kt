package com.ailyn.kpopui.features.editarproducto.domain.repositories

import com.ailyn.kpopui.features.editarproducto.domain.entities.ProductoDetalle

interface EditarProductoRepository {
    suspend fun getProducto(id: Int): ProductoDetalle
    suspend fun editarProducto(
        id: Int,
        nombre: String,
        grupo: String,
        categoria: String,
        precio: Double,
        stock: Int,
        descripcion: String?
    ): ProductoDetalle
    suspend fun eliminarProducto(id: Int)
}