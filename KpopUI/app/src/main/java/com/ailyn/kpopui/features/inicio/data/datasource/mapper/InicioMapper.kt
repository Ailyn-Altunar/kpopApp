package com.ailyn.kpopui.features.inicio.data.datasource.mapper

import com.ailyn.kpopui.features.inicio.data.datasource.models.InicioDto
import com.ailyn.kpopui.features.inicio.domain.entities.Inicio


fun InicioDto.toDomain(): Inicio = Inicio(
    totalProductos = this.totalProductos,
    totalStock = this.totalStock,
    porCategoria = this.porCategoria
)