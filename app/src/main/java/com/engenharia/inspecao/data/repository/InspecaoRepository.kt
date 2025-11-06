package com.engenharia.inspecao.data.repository

import com.engenharia.inspecao.data.local.dao.InspecaoDao
import com.engenharia.inspecao.data.local.entity.InspecaoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository - Camada intermediária entre o ViewModel e a fonte de dados
 * 
 * Esta classe implementa o padrão Repository da arquitetura MVVM
 * Ela abstrai o acesso aos dados e pode combinar múltiplas fontes (local, remoto)
 * Atualmente usa apenas o banco de dados local (Room)
 */
class InspecaoRepository(private val inspecaoDao: InspecaoDao) {
    
    /**
     * Obtém todas as inspeções do banco de dados
     * @return Flow que emite a lista de inspeções sempre que há mudanças
     */
    fun obterTodasInspecoes(): Flow<List<InspecaoEntity>> {
        return inspecaoDao.obterTodasInspecoes()
    }
    
    /**
     * Obtém as últimas inspeções limitadas por quantidade
     * @param limite - Número máximo de inspeções a retornar
     * @return Flow com lista das últimas inspeções
     */
    fun obterUltimasInspecoes(limite: Int): Flow<List<InspecaoEntity>> {
        return inspecaoDao.obterUltimasInspecoes(limite)
    }
    
    /**
     * Conta o total de inspeções registradas
     * @return Flow com o número total
     */
    fun contarInspecoes(): Flow<Int> {
        return inspecaoDao.contarInspecoes()
    }
    
    /**
     * Insere uma nova inspeção no banco de dados
     * Função suspensa - deve ser chamada de uma corrotina
     * @param inspecao - Objeto InspecaoEntity a ser inserido
     * @return ID da inspeção inserida
     */
    suspend fun inserirInspecao(inspecao: InspecaoEntity): Long {
        return inspecaoDao.inserirInspecao(inspecao)
    }
}
