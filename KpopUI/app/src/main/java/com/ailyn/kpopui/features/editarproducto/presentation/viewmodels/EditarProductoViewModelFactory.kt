package com.ailyn.kpopui.features.editarproducto.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ailyn.kpopui.features.editarproducto.domain.usescases.GetEditarProducteUseCase
import com.ailyn.kpopui.features.editarproducto.domain.usescases.DeleteEditarProductoUseCase
import com.ailyn.kpopui.features.editarproducto.domain.usescases.PutEditarProductoUseCase

class EditarProductoViewModelFactory(
    private val getDetalleUseCase: GetEditarProducteUseCase,
    private val editarUseCase: PutEditarProductoUseCase,
    private val eliminarUseCase: DeleteEditarProductoUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditarProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditarProductoViewModel(getDetalleUseCase, editarUseCase, eliminarUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}