package com.ailyn.kpopui.features.editarproducto.data.repositories

import com.ailyn.kpopui.features.editarproducto.data.datasource.api.JsonPlaceHolderEditarProductoApp
import com.ailyn.kpopui.features.editarproducto.data.datasource.mapper.toDomain
import com.ailyn.kpopui.features.editarproducto.data.datasource.models.EditarProductoDto
import com.ailyn.kpopui.features.editarproducto.domain.entities.ProductoDetalle
import com.ailyn.kpopui.features.editarproducto.domain.repositories.EditarProductoRepository

class EditarProductoRepositoryImpl(private val api: JsonPlaceHolderEditarProductoApp) : EditarProductoRepository {
    override suspend fun getProducto(id: Int): ProductoDetalle =
        api.getProducto(id).toDomain()

    override suspend fun editarProducto(
        id: Int, nombre: String, grupo: String, categoria: String,
        precio: Double, stock: Int, descripcion: String?
    ): ProductoDetalle = api.editarProducto(
        id, EditarProductoDto(0, nombre, grupo, categoria, precio, stock, descripcion)
    ).toDomain()

    override suspend fun eliminarProducto(id: Int) =
        api.eliminarProducto(id)
}
