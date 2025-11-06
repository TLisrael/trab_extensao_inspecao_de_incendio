package com.engenharia.inspecao.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.engenharia.inspecao.data.local.AppDatabase
import com.engenharia.inspecao.data.local.entity.InspecaoEntity
import com.engenharia.inspecao.data.repository.InspecaoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel - Camada de lógica de negócio da arquitetura MVVM
 * 
 * Esta classe gerencia o estado da UI e coordena as operações de dados
 * Sobrevive a mudanças de configuração (rotação de tela, etc)
 * Comunica-se com o Repository para acessar dados
 */
class InspecaoViewModel(application: Application) : AndroidViewModel(application) {
    
    // Repository para acesso aos dados
    private val repository: InspecaoRepository
    
    // Estado de todas as inspeções (histórico completo)
    val todasInspecoes: StateFlow<List<InspecaoEntity>>
    
    // Estado das últimas 5 inspeções (para tela inicial)
    val ultimasInspecoes: StateFlow<List<InspecaoEntity>>
    
    // Estado do total de inspeções
    val totalInspecoes: StateFlow<Int>
    
    // Estado privado para controlar o sucesso ao salvar
    private val _salvamentoSucesso = MutableStateFlow(false)
    val salvamentoSucesso: StateFlow<Boolean> = _salvamentoSucesso.asStateFlow()
    
    init {
        // Inicializa o banco de dados e o repository
        val dao = AppDatabase.obterDatabase(application).inspecaoDao()
        repository = InspecaoRepository(dao)
        
        // Converte os Flows do repository em StateFlows para a UI
        // StateFlow mantém sempre o último valor emitido
        todasInspecoes = repository.obterTodasInspecoes()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
        
        ultimasInspecoes = repository.obterUltimasInspecoes(5)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
        
        totalInspecoes = repository.contarInspecoes()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0
            )
    }
    
    /**
     * Salva uma nova inspeção no banco de dados
     * Executa em uma corrotina para não bloquear a UI
     * 
     * @param local - Local da inspeção
     * @param data - Data em timestamp
     * @param equipamentos - Equipamentos verificados
     * @param observacoes - Observações da inspeção
     */
    fun salvarInspecao(
        local: String,
        data: Long,
        equipamentos: String,
        observacoes: String
    ) {
        viewModelScope.launch {
            try {
                val inspecao = InspecaoEntity(
                    local = local,
                    data = data,
                    equipamentosVerificados = equipamentos,
                    observacoes = observacoes
                )
                repository.inserirInspecao(inspecao)
                _salvamentoSucesso.value = true
            } catch (e: Exception) {
                _salvamentoSucesso.value = false
            }
        }
    }
    
    /**
     * Reseta o estado de salvamento
     * Usado após navegar de volta ou mostrar mensagem de sucesso
     */
    fun resetarSalvamento() {
        _salvamentoSucesso.value = false
    }
}
