package com.ailyn.kpopui.features.inicio.domain.repositories

import com.ailyn.kpopui.features.inicio.domain.entities.Inicio

interface InicioRepository {
    suspend fun getInicio(): Inicio
}