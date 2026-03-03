package com.ailyn.kpopui.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ailyn.kpopui.features.agregarproducto.di.AgregarProductoProvider
import com.ailyn.kpopui.features.agregarproducto.presentation.screens.AgregarProductoScreen
import com.ailyn.kpopui.features.editarproducto.di.EditarProductoProvider
import com.ailyn.kpopui.features.editarproducto.presentation.screens.EditarProductoScreen
import com.ailyn.kpopui.features.inicio.di.InicioProvider
import com.ailyn.kpopui.features.inicio.presentation.screens.InicioScreen
import com.ailyn.kpopui.features.inventario.di.InventarioProvider
import com.ailyn.kpopui.features.inventario.presentation.screens.InventarioScreen

object Routes {
    const val INICIO = "inicio"
    const val INVENTARIO = "inventario"
    const val AGREGAR_PRODUCTO = "agregarproducto"
    const val EDITAR_PRODUCTO = "editarproducto/{id}"
    fun editarProducto(id: Int) = "editarproducto/$id"
}

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    val inicioProvider = InicioProvider()
    val inventarioProvider = InventarioProvider()
    val agregarProvider = AgregarProductoProvider()
    val editarProvider = EditarProductoProvider()

    NavHost(
        navController = navController,
        startDestination = Routes.INICIO
    ) {
        composable(Routes.INICIO) {
            InicioScreen(
                factory = inicioProvider.viewModelFactory,
                onIrInventario = { navController.navigate(Routes.INVENTARIO) }
            )
        }

        composable(Routes.INVENTARIO) {
            InventarioScreen(
                factory = inventarioProvider.viewModelFactory,
                onProductoClick = { id -> navController.navigate(Routes.editarProducto(id)) },
                onAgregarClick = { navController.navigate(Routes.AGREGAR_PRODUCTO) }
            )
        }

        composable(Routes.AGREGAR_PRODUCTO) {
            AgregarProductoScreen(
                factory = agregarProvider.viewModelFactory,
                onVolver = { navController.popBackStack() },
                onGuardado = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.EDITAR_PRODUCTO,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            EditarProductoScreen(
                productoId = id,
                factory = editarProvider.viewModelFactory,
                onVolver = { navController.popBackStack() },
                onEliminado = {
                    navController.navigate(Routes.INVENTARIO) {
                        popUpTo(Routes.INVENTARIO) { inclusive = true }
                    }
                }
            )
        }
    }
}