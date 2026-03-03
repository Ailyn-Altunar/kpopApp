package com.ailyn.kpopui.features.inicio.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.kpopui.features.inicio.domain.usescases.GetInicioUseCase
import com.ailyn.kpopui.features.inicio.presentation.screens.InicioUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class InicioViewModel(private val getInicioUseCase: GetInicioUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(InicioUIState())
    val uiState = _uiState.asStateFlow()

    init { cargarResumen() }

    private fun cargarResumen() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            getInicioUseCase().fold(
                onSuccess = { _uiState.update { s -> s.copy(isLoading = false, resumen = it) } },
                onFailure = { _uiState.update { s -> s.copy(isLoading = false, error = it.message) } }
            )
        }
    }
}