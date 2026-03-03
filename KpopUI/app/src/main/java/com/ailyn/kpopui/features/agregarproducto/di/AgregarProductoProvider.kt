package com.ailyn.kpopui.features.agregarproducto.di

import com.ailyn.kpopui.core.network.RetrofitClient
import com.ailyn.kpopui.features.agregarproducto.data.datasource.api.JsonPlaceHolderAgregarInventarioApp
import com.ailyn.kpopui.features.agregarproducto.data.repositories.AgregarProductoRepositoryImpl
import com.ailyn.kpopui.features.agregarproducto.domain.usescases.PostProductoUseCase
import com.ailyn.kpopui.features.agregarproducto.presentation.viewmodels.AgregarProductoViewModelFactory

class AgregarProductoProvider {
    private val api: JsonPlaceHolderAgregarInventarioApp by lazy {
        RetrofitClient.retrofit.create(JsonPlaceHolderAgregarInventarioApp::class.java)
    }
    private val repository by lazy { AgregarProductoRepositoryImpl(api) }
    private val useCase by lazy { PostProductoUseCase(repository) }
    val viewModelFactory by lazy { AgregarProductoViewModelFactory(useCase) }
}
