package com.ailyn.kpopui.core.network.api

import com.ailyn.kpopui.core.network.models.CatalogoDto
import retrofit2.http.GET

interface CatalogoApi {
    @GET("api/categorias")
    suspend fun getCategorias(): List<CatalogoDto>

    @GET("api/grupos")
    suspend fun getGrupos(): List<CatalogoDto>
}
