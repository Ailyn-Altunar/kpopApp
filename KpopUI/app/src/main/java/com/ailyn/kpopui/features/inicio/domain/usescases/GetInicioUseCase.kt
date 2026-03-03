package com.ailyn.kpopui.features.inicio.domain.usescases

import com.ailyn.kpopui.features.inicio.domain.entities.Inicio
import com.ailyn.kpopui.features.inicio.domain.repositories.InicioRepository

class GetInicioUseCase(private val repository: InicioRepository) {
    suspend operator fun invoke(): Result<Inicio> = try {
        Result.success(repository.getInicio())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
