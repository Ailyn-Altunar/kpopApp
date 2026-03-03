package com.ailyn.kpopui.features.inventario.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.kpopui.features.inventario.domain.usescases.GetInventarioBuscarUseCase
import com.ailyn.kpopui.features.inventario.domain.usescases.GetInventarioListarUseCase
import com.ailyn.kpopui.features.inventario.presentation.screens.InventarioUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InventarioViewModel(
    private val listarUseCase: GetInventarioListarUseCase,
    private val buscarUseCase: GetInventarioBuscarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InventarioUIState())
    val uiState = _uiState.asStateFlow()

    init { cargarProductos() }

    fun cargarProductos() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            listarUseCase().fold(
                onSuccess = { lista -> _uiState.update { s -> s.copy(isLoading = false, inventario = lista, productos = lista) } },
                onFailure = { _uiState.update { s -> s.copy(isLoading = false, error = it.message) } }
            )
        }
    }

    fun buscar(query: String) {
        _uiState.update { it.copy(busqueda = query, isLoading = true) }
        viewModelScope.launch {
            val result = if (query.isBlank()) listarUseCase() else buscarUseCase(query)
            result.fold(
                onSuccess = { lista -> _uiState.update { s -> s.copy(isLoading = false, inventario = lista, productos = lista) } },
                onFailure = { _uiState.update { s -> s.copy(isLoading = false, error = it.message) } }
            )
        }
    }
}
