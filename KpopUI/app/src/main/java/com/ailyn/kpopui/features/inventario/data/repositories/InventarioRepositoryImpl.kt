package com.ailyn.kpopui.features.inventario.data.repositories

import com.ailyn.kpopui.features.inventario.data.datasource.api.JsonPlaceHolderInventario
import com.ailyn.kpopui.features.inventario.data.datasource.mapper.toDomain
import com.ailyn.kpopui.features.inventario.domain.entities.Inventario
import com.ailyn.kpopui.features.inventario.domain.repositories.InventarioRepository

class InventarioRepositoryImpl(private val api: JsonPlaceHolderInventario) : InventarioRepository {
    override suspend fun getProductos(busqueda: String?): List<Inventario> =
        api.getInvenario(busqueda).map { it.toDomain() }
}