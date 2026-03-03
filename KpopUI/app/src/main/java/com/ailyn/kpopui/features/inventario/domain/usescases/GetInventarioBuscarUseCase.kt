package com.ailyn.kpopui.features.inventario.domain.usescases

import com.ailyn.kpopui.features.inventario.domain.entities.Inventario
import com.ailyn.kpopui.features.inventario.domain.repositories.InventarioRepository

class GetInventarioBuscarUseCase(private val repository: InventarioRepository) {
    suspend operator fun invoke(busqueda: String): Result<List<Inventario>> = try {
        Result.success(repository.getProductos(busqueda))
    } catch (e: Exception) {
        Result.failure(e)
    }
}