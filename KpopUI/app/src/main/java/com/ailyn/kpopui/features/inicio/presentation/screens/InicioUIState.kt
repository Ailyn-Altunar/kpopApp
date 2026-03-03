package com.ailyn.kpopui.features.inicio.presentation.screens

import com.ailyn.kpopui.features.inicio.domain.entities.Inicio

data class InicioUIState (
    val isLoading: Boolean = false,
    val resumen: Inicio? = null,
    val error: String? = null
    )