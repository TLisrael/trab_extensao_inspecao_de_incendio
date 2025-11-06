package com.engenharia.inspecao.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.engenharia.inspecao.data.local.dao.InspecaoDao
import com.engenharia.inspecao.data.local.entity.InspecaoEntity

/**
 * Banco de Dados principal do aplicativo usando Room
 * 
 * Esta classe configura o banco de dados SQLite local que armazena
 * todas as inspeções de segurança offline no dispositivo
 */
@Database(
    entities = [InspecaoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    // Fornece acesso ao DAO de inspeções
    abstract fun inspecaoDao(): InspecaoDao
    
    companion object {
        // Singleton - garante uma única instância do banco de dados
        @Volatile
        private var INSTANCIA: AppDatabase? = null
        
        /**
         * Obtém a instância única do banco de dados
         * @param context - Contexto da aplicação
         * @return Instância do AppDatabase
         */
        fun obterDatabase(context: Context): AppDatabase {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "inspecao_database" // Nome do arquivo do banco de dados
                ).build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}
