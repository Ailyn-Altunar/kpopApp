package com.ailyn.kpopui.features.inventario.domain.repositories

import com.ailyn.kpopui.features.inventario.domain.entities.Inventario

interface InventarioRepository {
    suspend fun getProductos(busqueda: String?): List<Inventario>
}
