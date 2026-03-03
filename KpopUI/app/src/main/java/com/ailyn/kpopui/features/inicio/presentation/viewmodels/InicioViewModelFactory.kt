package com.ailyn.kpopui.features.inicio.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ailyn.kpopui.features.inicio.domain.usescases.GetInicioUseCase

class InicioViewModelFactory(private val getInicioUseCase: GetInicioUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InicioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InicioViewModel(getInicioUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}