package com.ailyn.kpopui.features.agregarproducto.domain.usescases

import com.ailyn.kpopui.features.agregarproducto.domain.entities.ProductoCreado
import com.ailyn.kpopui.features.agregarproducto.domain.repositories.AgregarProductoRepository

class PostProductoUseCase(private val repository: AgregarProductoRepository) {
    suspend operator fun invoke(
        nombre: String,
        grupo: String,
        categoria: String,
        precio: Double,
        stock: Int,
        descripcion: String?,
        imagenUrl: String? = null
    ): Result<ProductoCreado> = try {
        if (nombre.isBlank() || grupo.isBlank() || categoria.isBlank())
            Result.failure(Exception("Nombre, grupo y categoría son obligatorios"))
        else
            Result.success(repository.crearProducto(nombre, grupo, categoria, precio, stock, descripcion, imagenUrl))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
