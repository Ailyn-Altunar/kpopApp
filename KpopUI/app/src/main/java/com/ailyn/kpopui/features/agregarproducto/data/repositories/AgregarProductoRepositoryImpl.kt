package com.ailyn.kpopui.features.agregarproducto.data.repositories

import com.ailyn.kpopui.features.agregarproducto.data.datasource.api.JsonPlaceHolderAgregarInventarioApp
import com.ailyn.kpopui.features.agregarproducto.data.datasource.models.AgregarProductoDto
import com.ailyn.kpopui.features.agregarproducto.domain.entities.ProductoCreado
import com.ailyn.kpopui.features.agregarproducto.domain.repositories.AgregarProductoRepository
import com.ailyn.kpopui.features.editarproducto.data.datasource.mapper.toDomain

class AgregarProductoRepositoryImpl(private val api: JsonPlaceHolderAgregarInventarioApp) : AgregarProductoRepository {
    override suspend fun crearProducto(
        nombre: String, grupo: String, categoria: String,
        precio: Double, stock: Int, descripcion: String?, imagenUrl: String?
    ): ProductoCreado = api.crearProducto(
        AgregarProductoDto(nombre, grupo, categoria, precio, stock, descripcion, imagenUrl)
    ).let { ProductoCreado(it.idProducto, it.nombre) }
}
