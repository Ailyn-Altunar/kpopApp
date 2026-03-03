package com.ailyn.kpopui.features.inicio.data.repositories

import com.ailyn.kpopui.features.inicio.data.datasource.api.JsonPlaceHolderInicioApi
import com.ailyn.kpopui.features.inicio.data.datasource.mapper.toDomain
import com.ailyn.kpopui.features.inicio.domain.entities.Inicio
import com.ailyn.kpopui.features.inicio.domain.repositories.InicioRepository

class InicioRepositoryImpl(
    private val api: JsonPlaceHolderInicioApi
) : InicioRepository {
    override suspend fun getInicio(): Inicio = api.getInicio().toDomain()
}