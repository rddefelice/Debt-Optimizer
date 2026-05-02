package com.rddefelice.debtoptimizer.ui.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rddefelice.debtoptimizer.domain.model.Debt
import com.rddefelice.debtoptimizer.domain.model.DebtType
import com.rddefelice.debtoptimizer.domain.repository.DebtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddAccountUiState(
    val name: String = "",
    val balance: String = "",
    val apr: String = "",
    val remainingTerm: String = "",
    val minimumPayment: String = "",
    val type: DebtType = DebtType.CREDIT_CARD,
    val isSaved: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val repository: DebtRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddAccountUiState())
    val uiState: StateFlow<AddAccountUiState> = _uiState.asStateFlow()

    fun onNameChange(newValue: String) = _uiState.update { it.copy(name = newValue) }
    fun onBalanceChange(newValue: String) = _uiState.update { it.copy(balance = newValue) }
    fun onAprChange(newValue: String) = _uiState.update { it.copy(apr = newValue) }
    fun onTermChange(newValue: String) = _uiState.update { it.copy(remainingTerm = newValue) }
    fun onMinPaymentChange(newValue: String) = _uiState.update { it.copy(minimumPayment = newValue) }
    fun onTypeChange(newValue: DebtType) = _uiState.update { it.copy(type = newValue) }

    fun saveDebt() {
        val state = _uiState.value
        val debt = try {
            Debt(
                name = state.name,
                balance = state.balance.toDouble(),
                apr = state.apr.toDouble() / 100.0,
                remainingTermMonths = if (state.type == DebtType.CREDIT_CARD) {
                    state.remainingTerm.toIntOrNull()
                } else {
                    state.remainingTerm.toInt()
                },
                minimumPayment = state.minimumPayment.toDouble(),
                type = state.type
            )
        } catch (e: Exception) {
            _uiState.update { it.copy(error = "Invalid input. Please check your numbers.") }
            return
        }

        viewModelScope.launch {
            repository.insertDebt(debt)
            _uiState.update { it.copy(isSaved = true) }
        }
    }
}