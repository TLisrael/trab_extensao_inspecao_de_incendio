package com.engenharia.inspecao.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade que representa uma Inspeção de Segurança no banco de dados local
 * 
 * Esta classe define a estrutura da tabela "inspecoes" no Room Database
 * Cada inspeção registra informações sobre verificações de equipamentos contra incêndio
 */
@Entity(tableName = "inspecoes")
data class InspecaoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Local onde foi realizada a inspeção (ex: "Edifício Central - 3º Andar")
    val local: String,
    
    // Data da inspeção no formato timestamp (milissegundos)
    val data: Long,
    
    // Lista de equipamentos verificados separados por vírgula
    // Ex: "Extintor CO2, Hidrante, Alarme de Incêndio"
    val equipamentosVerificados: String,
    
    // Observações sobre a inspeção (conformidades, pendências, etc)
    val observacoes: String
)
