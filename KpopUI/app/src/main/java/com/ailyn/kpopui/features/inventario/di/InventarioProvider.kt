package com.ailyn.kpopui.features.inventario.di

import com.ailyn.kpopui.core.network.RetrofitClient
import com.ailyn.kpopui.features.inventario.data.datasource.api.JsonPlaceHolderInventario
import com.ailyn.kpopui.features.inventario.data.repositories.InventarioRepositoryImpl
import com.ailyn.kpopui.features.inventario.domain.usescases.GetInventarioBuscarUseCase
import com.ailyn.kpopui.features.inventario.domain.usescases.GetInventarioListarUseCase
import com.ailyn.kpopui.features.inventario.presentation.viewmodels.InventarioViewModelFactory

class InventarioProvider {
    private val api: JsonPlaceHolderInventario by lazy {
        RetrofitClient.retrofit.create(JsonPlaceHolderInventario::class.java)
    }
    private val repository by lazy { InventarioRepositoryImpl(api) }
    private val listarUseCase by lazy { GetInventarioListarUseCase(repository) }
    private val buscarUseCase by lazy { GetInventarioBuscarUseCase(repository) }
    val viewModelFactory by lazy { InventarioViewModelFactory(listarUseCase, buscarUseCase) }
}
