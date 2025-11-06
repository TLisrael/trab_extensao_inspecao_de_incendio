package com.engenharia.inspecao.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.engenharia.inspecao.presentation.viewmodel.InspecaoViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Tela de Nova Inspeção - Formulário para registrar uma inspeção
 * 
 * Permite ao técnico inserir todas as informações da inspeção
 * e salvar no banco de dados local
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaNovaInspecao(
    viewModel: InspecaoViewModel,
    onVoltarClick: () -> Unit
) {
    // Estados dos campos do formulário
    var local by remember { mutableStateOf("") }
    var equipamentos by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }
    
    // Estado de salvamento
    val salvamentoSucesso by viewModel.salvamentoSucesso.collectAsState()
    
    // Mostra diálogo de sucesso e volta para tela inicial
    LaunchedEffect(salvamentoSucesso) {
        if (salvamentoSucesso) {
            viewModel.resetarSalvamento()
            onVoltarClick()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Cabeçalho
        Text(
            text = "Nova Inspeção",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Preencha os dados da inspeção de segurança",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Card com o formulário
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Campo: Local
                Text(
                    text = "Local da Inspeção",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = local,
                    onValueChange = { local = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ex: Edifício Central - 3º Andar") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Local"
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Campo: Data (mostra data atual)
                Text(
                    text = "Data da Inspeção",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                val dataAtual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    .format(Date())
                OutlinedTextField(
                    value = dataAtual,
                    onValueChange = { },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Data"
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Campo: Equipamentos Verificados
                Text(
                    text = "Equipamentos Verificados",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = equipamentos,
                    onValueChange = { equipamentos = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = { 
                        Text("Ex: Extintor CO2, Hidrante, Alarme de Incêndio, Saída de Emergência") 
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    maxLines = 4
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Campo: Observações
                Text(
                    text = "Observações",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = observacoes,
                    onValueChange = { observacoes = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = { 
                        Text("Ex: Todos os equipamentos estão em conformidade. Extintor próximo ao vencimento.") 
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    maxLines = 6
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Botão Salvar
        Button(
            onClick = {
                // Validação simples
                if (local.isNotBlank() && equipamentos.isNotBlank()) {
                    viewModel.salvarInspecao(
                        local = local.trim(),
                        data = System.currentTimeMillis(),
                        equipamentos = equipamentos.trim(),
                        observacoes = observacoes.trim()
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = local.isNotBlank() && equipamentos.isNotBlank()
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Salvar",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "SALVAR INSPEÇÃO",
                style = MaterialTheme.typography.titleMedium
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Botão Cancelar
        OutlinedButton(
            onClick = onVoltarClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "CANCELAR",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
