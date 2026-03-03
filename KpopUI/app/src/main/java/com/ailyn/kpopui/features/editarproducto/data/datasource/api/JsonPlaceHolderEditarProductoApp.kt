package com.ailyn.kpopui.features.editarproducto.data.datasource.api

import com.ailyn.kpopui.features.editarproducto.data.datasource.models.EditarProductoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface JsonPlaceHolderEditarProductoApp {
    @GET("api/productos/{id}")
    suspend fun getProducto(@Path("id") id: Int): EditarProductoDto

    @PUT("api/productos/{id}")
    suspend fun editarProducto(@Path("id") id: Int, @Body body: EditarProductoDto): EditarProductoDto

    @DELETE("api/productos/{id}")
    suspend fun eliminarProducto(@Path("id") id: Int)
}
