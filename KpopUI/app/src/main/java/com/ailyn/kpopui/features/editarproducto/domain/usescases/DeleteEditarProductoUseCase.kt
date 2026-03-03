package com.ailyn.kpopui.features.editarproducto.domain.usescases

import com.ailyn.kpopui.features.editarproducto.domain.repositories.EditarProductoRepository

class DeleteEditarProductoUseCase(private val repository: EditarProductoRepository) {
    suspend operator fun invoke(id: Int): Result<Unit> = try {
        repository.eliminarProducto(id)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}