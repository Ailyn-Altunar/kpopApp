package com.ailyn.kpopui.features.inventario.data.datasource.api

import com.ailyn.kpopui.features.inventario.data.datasource.models.InventarioDto
import retrofit2.http.GET
import retrofit2.http.Query

interface JsonPlaceHolderInventario{
    @GET("api/productos")
    suspend fun getInvenario(@Query("busqueda") busqueda: String? = null): List<InventarioDto>
}