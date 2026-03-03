package com.ailyn.kpopui.features.agregarproducto.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ailyn.kpopui.features.agregarproducto.domain.usescases.PostProductoUseCase

class AgregarProductoViewModelFactory(
    private val postProductoUseCase: PostProductoUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgregarProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgregarProductoViewModel(postProductoUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
