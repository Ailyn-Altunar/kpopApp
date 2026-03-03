package com.ailyn.kpopui.features.editarproducto.domain.usescases

import com.ailyn.kpopui.features.editarproducto.domain.entities.ProductoDetalle
import com.ailyn.kpopui.features.editarproducto.domain.repositories.EditarProductoRepository

class PutEditarProductoUseCase(private val repository: EditarProductoRepository) {
    suspend operator fun invoke(
        id: Int,
        nombre: String,
        grupo: String,
        categoria: String,
        precio: Double,
        stock: Int,
        descripcion: String?
    ): Result<ProductoDetalle> = try {
        Result.success(repository.editarProducto(id, nombre, grupo, categoria, precio, stock, descripcion))
    } catch (e: Exception) {
        Result.failure(e)
    }
}