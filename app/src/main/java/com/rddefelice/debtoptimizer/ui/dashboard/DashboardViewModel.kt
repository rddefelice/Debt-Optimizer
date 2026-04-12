package com.rddefelice.debtoptimizer.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rddefelice.debtoptimizer.domain.model.Debt
import com.rddefelice.debtoptimizer.domain.repository.DebtRepository
import com.rddefelice.debtoptimizer.domain.usecase.DebtSummary
import com.rddefelice.debtoptimizer.domain.usecase.GetDebtSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDebtSummary: GetDebtSummary,
    private val repo: DebtRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.observeDebts().collect { debts ->
                val summary = getDebtSummary()
                _uiState.update { it.copy(debts = debts, summary = summary) }
            }
        }
    }
}

data class DashboardUiState(
    val debts: List<Debt> = emptyList(),
    val summary: DebtSummary? = null,
    val isLoading: Boolean = false
)

private fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
    while (true) {
        val prevValue = value
        val nextValue = function(prevValue)
        if (compareAndSet(prevValue, nextValue)) {
            return
        }
    }
}