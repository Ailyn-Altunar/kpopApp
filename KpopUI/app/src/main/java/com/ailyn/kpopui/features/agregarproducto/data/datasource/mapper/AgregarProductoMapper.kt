package com.ailyn.kpopui.features.agregarproducto.data.datasource.mapper

import com.ailyn.kpopui.features.agregarproducto.data.datasource.models.ProductoCreadoDto
import com.ailyn.kpopui.features.agregarproducto.domain.entities.ProductoCreado

fun ProductoCreadoDto.toDomain(): ProductoCreado = ProductoCreado(
    idProducto = this.idProducto,
    nombre     = this.nombre
)
