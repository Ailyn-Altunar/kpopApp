package com.ailyn.kpopui.features.inventario.data.datasource.mapper

import com.ailyn.kpopui.features.inventario.data.datasource.models.InventarioDto
import com.ailyn.kpopui.features.inventario.domain.entities.Inventario


fun InventarioDto.toDomain(): Inventario =Inventario(
    idProducto = this.idProducto,
    nombre = this.nombre,
    grupo = this.grupo,
    categoria = this.categoria,
    precio = this.precio,
    stock = this.stock,
    descripcion = this.descripcion,
    imagen = this.imagen
)