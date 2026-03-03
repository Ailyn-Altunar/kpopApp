package com.ailyn.kpopui.features.agregarproducto.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.kpopui.core.network.RetrofitClient
import com.ailyn.kpopui.core.network.api.CatalogoApi
import com.ailyn.kpopui.features.agregarproducto.domain.usescases.PostProductoUseCase
import com.ailyn.kpopui.features.agregarproducto.presentation.screens.AgregarProductoUISate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AgregarProductoViewModel(private val postProductoUseCase: PostProductoUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(AgregarProductoUISate())
    val uiState = _uiState.asStateFlow()

    private val catalogoApi: CatalogoApi by lazy {
        RetrofitClient.retrofit.create(CatalogoApi::class.java)
    }

    init { cargarCatalogos() }

    private fun cargarCatalogos() {
        _uiState.update { it.copy(cargandoCatalogos = true) }
        viewModelScope.launch {
            try {
                val categorias = catalogoApi.getCategorias().map { it.nombre }
                val grupos = catalogoApi.getGrupos().map { it.nombre }
                _uiState.update {
                    it.copy(
                        categorias = categorias,
                        grupos = grupos,
                        cargandoCatalogos = false,
                        // Pre-seleccionar el primero si la lista no está vacía
                        categoria = if (it.categoria.isBlank() && categorias.isNotEmpty()) categorias[0] else it.categoria,
                        grupo = if (it.grupo.isBlank() && grupos.isNotEmpty()) grupos[0] else it.grupo
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(cargandoCatalogos = false) }
            }
        }
    }

    fun onNombreChange(v: String)     = _uiState.update { it.copy(nombre = v) }
    fun onGrupoChange(v: String)      = _uiState.update { it.copy(grupo = v) }
    fun onCategoriaChange(v: String)  = _uiState.update { it.copy(categoria = v) }
    fun onPrecioChange(v: String)     = _uiState.update { it.copy(precio = v) }
    fun onStockChange(v: String)      = _uiState.update { it.copy(stock = v) }
    fun onDescripcionChange(v: String)= _uiState.update { it.copy(descripcion = v) }
    fun onImagenUrlChange(v: String)  = _uiState.update { it.copy(imagenUrl = v.ifBlank { null }) }

    fun guardar() {
        val s = _uiState.value
        val precio = s.precio.toDoubleOrNull() ?: 0.0
        val stock  = s.stock.toIntOrNull() ?: 0

        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            postProductoUseCase(
                s.nombre, s.grupo, s.categoria,
                precio, stock,
                s.descripcion.ifBlank { null },
                s.imagenUrl
            ).fold(
                onSuccess = { _uiState.update { st -> st.copy(isLoading = false, guardado = true) } },
                onFailure = { _uiState.update { st -> st.copy(isLoading = false, error = it.message) } }
            )
        }
    }
}
