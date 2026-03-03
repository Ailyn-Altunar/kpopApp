package com.ailyn.kpopui.features.inicio.data.datasource.api

import com.ailyn.kpopui.features.inicio.data.datasource.models.InicioDto
import retrofit2.http.GET

interface JsonPlaceHolderInicioApi   {
    @GET("api/productos/resumen")
    suspend fun getInicio(): InicioDto
}