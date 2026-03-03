package com.ailyn.kpopui.features.agregarproducto.data.datasource.api

import com.ailyn.kpopui.features.agregarproducto.data.datasource.models.AgregarProductoDto
import com.ailyn.kpopui.features.editarproducto.data.datasource.models.EditarProductoDto
import retrofit2.http.Body
import retrofit2.http.POST

interface JsonPlaceHolderAgregarInventarioApp {
    @POST("api/productos")
    suspend fun crearProducto(@Body body: AgregarProductoDto): EditarProductoDto
}
