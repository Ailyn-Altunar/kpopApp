package com.ailyn.kpopui.features.inventario.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ailyn.kpopui.features.inventario.domain.usescases.GetInventarioBuscarUseCase
import com.ailyn.kpopui.features.inventario.domain.usescases.GetInventarioListarUseCase

class InventarioViewModelFactory(
    private val listarUseCase: GetInventarioListarUseCase,
    private val buscarUseCase: GetInventarioBuscarUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventarioViewModel(listarUseCase, buscarUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}