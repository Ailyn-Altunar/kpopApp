package com.ailyn.kpopui.features.editarproducto.di

import com.ailyn.kpopui.core.network.RetrofitClient
import com.ailyn.kpopui.features.editarproducto.data.datasource.api.JsonPlaceHolderEditarProductoApp
import com.ailyn.kpopui.features.editarproducto.data.repositories.EditarProductoRepositoryImpl
import com.ailyn.kpopui.features.editarproducto.domain.usescases.GetEditarProducteUseCase
import com.ailyn.kpopui.features.editarproducto.domain.usescases.DeleteEditarProductoUseCase
import com.ailyn.kpopui.features.editarproducto.domain.usescases.PutEditarProductoUseCase
import com.ailyn.kpopui.features.editarproducto.presentation.viewmodels.EditarProductoViewModelFactory

class EditarProductoProvider {
    private val api: JsonPlaceHolderEditarProductoApp by lazy {
        RetrofitClient.retrofit.create(JsonPlaceHolderEditarProductoApp::class.java)
    }
    private val repository by lazy { EditarProductoRepositoryImpl(api) }
    private val getDetalleUseCase by lazy { GetEditarProducteUseCase(repository) }
    private val editarUseCase by lazy { PutEditarProductoUseCase(repository) }
    private val eliminarUseCase by lazy { DeleteEditarProductoUseCase(repository) }
    val viewModelFactory by lazy { EditarProductoViewModelFactory(getDetalleUseCase, editarUseCase, eliminarUseCase) }
}
