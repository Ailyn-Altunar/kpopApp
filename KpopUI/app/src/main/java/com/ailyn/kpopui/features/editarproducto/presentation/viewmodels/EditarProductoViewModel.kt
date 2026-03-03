package com.ailyn.kpopui.features.editarproducto.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.kpopui.core.network.RetrofitClient
import com.ailyn.kpopui.core.network.api.CatalogoApi
import com.ailyn.kpopui.features.editarproducto.domain.usescases.GetEditarProducteUseCase
import com.ailyn.kpopui.features.editarproducto.domain.usescases.DeleteEditarProductoUseCase
import com.ailyn.kpopui.features.editarproducto.domain.usescases.PutEditarProductoUseCase
import com.ailyn.kpopui.features.editarproducto.presentation.screens.EditarProductoUIState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditarProductoViewModel(
    private val getDetalleUseCase: GetEditarProducteUseCase,
    private val editarUseCase: PutEditarProductoUseCase,
    private val eliminarUseCase: DeleteEditarProductoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditarProductoUIState())
    val uiState = _uiState.asStateFlow()

    private val catalogoApi: CatalogoApi by lazy {
        RetrofitClient.retrofit.create(CatalogoApi::class.java)
    }

    fun cargar(id: Int) {
        _uiState.update { it.copy(isLoading = true, cargandoCatalogos = true, error = null) }
        viewModelScope.launch {
            // Cargamos producto y catálogos en paralelo
            val productoDeferred  = async { getDetalleUseCase(id) }
            val categoriasDeferred = async { runCatching { catalogoApi.getCategorias().map { it.nombre } } }
            val gruposDeferred     = async { runCatching { catalogoApi.getGrupos().map { it.nombre } } }

            val productoResult  = productoDeferred.await()
            val categorias = categoriasDeferred.await().getOrDefault(emptyList())
            val grupos     = gruposDeferred.await().getOrDefault(emptyList())

            productoResult.fold(
                onSuccess = { p ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            cargandoCatalogos = false,
                            idProducto  = p.idProducto,
                            nombre      = p.nombre,
                            grupo       = p.grupo,
                            categoria   = p.categoria,
                            precio      = p.precio.toString(),
                            stock       = p.stock.toString(),
                            descripcion = p.descripcion ?: "",
                            imagen      = p.imagen,
                            categorias  = categorias,
                            grupos      = grupos
                        )
                    }
                },
                onFailure = { _uiState.update { s -> s.copy(isLoading = false, cargandoCatalogos = false, error = it.message) } }
            )
        }
    }

    fun onNombreChange(v: String)      = _uiState.update { it.copy(nombre = v) }
    fun onGrupoChange(v: String)       = _uiState.update { it.copy(grupo = v) }
    fun onCategoriaChange(v: String)   = _uiState.update { it.copy(categoria = v) }
    fun onPrecioChange(v: String)      = _uiState.update { it.copy(precio = v) }
    fun onStockChange(v: String)       = _uiState.update { it.copy(stock = v) }
    fun onDescripcionChange(v: String) = _uiState.update { it.copy(descripcion = v) }

    fun mostrarConfirmacionEliminar()  = _uiState.update { it.copy(mostrarConfirmacionEliminar = true) }
    fun ocultarConfirmacionEliminar()  = _uiState.update { it.copy(mostrarConfirmacionEliminar = false) }

    fun guardar() {
        val s = _uiState.value
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            editarUseCase(
                s.idProducto, s.nombre, s.grupo, s.categoria,
                s.precio.toDoubleOrNull() ?: 0.0,
                s.stock.toIntOrNull() ?: 0,
                s.descripcion.ifBlank { null }
            ).fold(
                onSuccess = { _uiState.update { st -> st.copy(isLoading = false, guardado = true) } },
                onFailure = { _uiState.update { st -> st.copy(isLoading = false, error = it.message) } }
            )
        }
    }

    fun eliminar() {
        val id = _uiState.value.idProducto
        _uiState.update { it.copy(isLoading = true, mostrarConfirmacionEliminar = false) }
        viewModelScope.launch {
            eliminarUseCase(id).fold(
                onSuccess = { _uiState.update { s -> s.copy(isLoading = false, eliminado = true) } },
                onFailure = { _uiState.update { s -> s.copy(isLoading = false, error = it.message) } }
            )
        }
    }
}
