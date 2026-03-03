package com.ailyn.kpopui.features.editarproducto.data.datasource.mapper

import com.ailyn.kpopui.features.editarproducto.data.datasource.models.EditarProductoDto
import com.ailyn.kpopui.features.editarproducto.domain.entities.ProductoDetalle

fun EditarProductoDto.toDomain(): ProductoDetalle = ProductoDetalle(
    idProducto  = this.idProducto,
    nombre      = this.nombre,
    grupo       = this.grupo,
    categoria   = this.categoria,
    precio      = this.precio,
    stock       = this.stock,
    descripcion = this.descripcion,
    imagen      = this.imagen
)
