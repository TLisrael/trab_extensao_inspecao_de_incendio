package com.engenharia.inspecao.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.engenharia.inspecao.data.local.entity.InspecaoEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) para operações de banco de dados com Inspeções
 * 
 * Interface que define todas as operações de acesso aos dados de inspeções
 * O Room gera automaticamente a implementação dessas funções
 */
@Dao
interface InspecaoDao {
    
    /**
     * Insere uma nova inspeção no banco de dados
     * @param inspecao - Objeto InspecaoEntity a ser inserido
     * @return O ID da inspeção inserida
     */
    @Insert
    suspend fun inserirInspecao(inspecao: InspecaoEntity): Long
    
    /**
     * Busca todas as inspeções ordenadas da mais recente para a mais antiga
     * @return Flow com lista de todas as inspeções (atualiza automaticamente)
     */
    @Query("SELECT * FROM inspecoes ORDER BY data DESC")
    fun obterTodasInspecoes(): Flow<List<InspecaoEntity>>
    
    /**
     * Busca as últimas N inspeções
     * @param limite - Número máximo de inspeções a retornar
     * @return Flow com lista das últimas inspeções
     */
    @Query("SELECT * FROM inspecoes ORDER BY data DESC LIMIT :limite")
    fun obterUltimasInspecoes(limite: Int): Flow<List<InspecaoEntity>>
    
    /**
     * Conta o total de inspeções registradas
     * @return Flow com o número total de inspeções
     */
    @Query("SELECT COUNT(*) FROM inspecoes")
    fun contarInspecoes(): Flow<Int>
}
