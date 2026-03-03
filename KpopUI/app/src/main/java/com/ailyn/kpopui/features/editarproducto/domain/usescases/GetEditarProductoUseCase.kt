package com.ailyn.kpopui.features.editarproducto.domain.usescases

import com.ailyn.kpopui.features.editarproducto.domain.entities.ProductoDetalle
import com.ailyn.kpopui.features.editarproducto.domain.repositories.EditarProductoRepository

class GetEditarProducteUseCase(private val repository: EditarProductoRepository) {
    suspend operator fun invoke(id: Int): Result<ProductoDetalle> = try {
        Result.success(repository.getProducto(id))
    } catch (e: Exception) {
        Result.failure(e)
    }
}