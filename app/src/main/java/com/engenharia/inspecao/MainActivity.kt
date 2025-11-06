package com.engenharia.inspecao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.engenharia.inspecao.presentation.viewmodel.InspecaoViewModel
import com.engenharia.inspecao.ui.navigation.AppNavigation
import com.engenharia.inspecao.ui.theme.InspecaoTheme

/**
 * MainActivity - Ponto de entrada do aplicativo
 * 
 * Activity principal que configura o Jetpack Compose e
 * inicia a navegação do aplicativo
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Habilita exibição de ponta a ponta (edge-to-edge)
        enableEdgeToEdge()
        
        // Define o conteúdo da Activity usando Compose
        setContent {
            // Aplica o tema personalizado (Branco e Vermelho)
            InspecaoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Cria/obtém instância do ViewModel
                    // O ViewModel sobrevive a mudanças de configuração
                    val viewModel: InspecaoViewModel = viewModel()
                    
                    // Inicia a navegação do aplicativo
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}
