package com.ailyn.kpopui.features.inicio.di

import com.ailyn.kpopui.core.network.RetrofitClient
import com.ailyn.kpopui.features.inicio.data.datasource.api.JsonPlaceHolderInicioApi
import com.ailyn.kpopui.features.inicio.data.repositories.InicioRepositoryImpl
import com.ailyn.kpopui.features.inicio.domain.usescases.GetInicioUseCase
import com.ailyn.kpopui.features.inicio.presentation.viewmodels.InicioViewModelFactory

class InicioProvider {
    private val api: JsonPlaceHolderInicioApi by lazy {
        RetrofitClient.retrofit.create(JsonPlaceHolderInicioApi::class.java)
    }
    private val repository by lazy { InicioRepositoryImpl(api) }
    private val useCase by lazy { GetInicioUseCase(repository) }
    val viewModelFactory by lazy { InicioViewModelFactory(useCase) }
}
