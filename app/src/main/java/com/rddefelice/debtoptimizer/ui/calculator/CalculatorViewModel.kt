package com.rddefelice.debtoptimizer.ui.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rddefelice.debtoptimizer.domain.model.Debt
import com.rddefelice.debtoptimizer.domain.repository.DebtRepository
import com.rddefelice.debtoptimizer.domain.usecase.CalculateAvalanche
import com.rddefelice.debtoptimizer.domain.usecase.CalculateSnowball
import com.rddefelice.debtoptimizer.domain.usecase.PayoffResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class CalculatorMethod {
    SNOWBALL, AVALANCHE
}

data class CalculatorUiState(
    val extraPayment: Double = 0.0,
    val snowballResult: PayoffResult? = null,
    val avalancheResult: PayoffResult? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val repo: DebtRepository,
    private val calculateSnowball: CalculateSnowball,
    private val calculateAvalanche: CalculateAvalanche
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun setExtraPayment(amount: Double) {
        _uiState.update { it.copy(extraPayment = amount) }
    }

    fun calculate() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val debts = repo.getAllDebts()
            val state = _uiState.value
            
            val snowball = calculateSnowball.execute(debts, state.extraPayment)
            val avalanche = calculateAvalanche.execute(debts, state.extraPayment)
            
            _uiState.update { it.copy(
                snowballResult = snowball,
                avalancheResult = avalanche,
                isLoading = false
            ) }
        }
    }
}