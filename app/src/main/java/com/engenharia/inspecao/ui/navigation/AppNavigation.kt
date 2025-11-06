package com.engenharia.inspecao.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.engenharia.inspecao.presentation.viewmodel.InspecaoViewModel
import com.engenharia.inspecao.ui.screens.TelaHistorico
import com.engenharia.inspecao.ui.screens.TelaInicial
import com.engenharia.inspecao.ui.screens.TelaNovaInspecao

/**
 * Rotas de navegação do aplicativo
 */
sealed class Tela(val rota: String, val titulo: String, val icone: ImageVector) {
    object Inicio : Tela("inicio", "Início", Icons.Default.Home)
    object NovaInspecao : Tela("nova_inspecao", "Nova", Icons.Default.Add)
    object Historico : Tela("historico", "Histórico", Icons.Default.History)
}

/**
 * Componente principal de navegação do aplicativo
 * Configura a barra de navegação inferior e o NavHost
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(viewModel: InspecaoViewModel) {
    val navController = rememberNavController()
    
    // Lista de telas para a barra de navegação
    val telas = listOf(
        Tela.Inicio,
        Tela.NovaInspecao,
        Tela.Historico
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sistema de Inspeção",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val destinoAtual = navBackStackEntry?.destination
                
                telas.forEach { tela ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = tela.icone,
                                contentDescription = tela.titulo
                            )
                        },
                        label = { Text(tela.titulo) },
                        selected = destinoAtual?.hierarchy?.any { it.route == tela.rota } == true,
                        onClick = {
                            navController.navigate(tela.rota) {
                                // Remove todas as telas da pilha até o início
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Evita múltiplas cópias da mesma tela
                                launchSingleTop = true
                                // Restaura o estado ao reselecionar a tela
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        // Host de navegação - define as rotas e as telas
        NavHost(
            navController = navController,
            startDestination = Tela.Inicio.rota,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Rota: Tela Inicial
            composable(Tela.Inicio.rota) {
                TelaInicial(
                    viewModel = viewModel,
                    onNovaInspecaoClick = {
                        navController.navigate(Tela.NovaInspecao.rota)
                    }
                )
            }
            
            // Rota: Nova Inspeção
            composable(Tela.NovaInspecao.rota) {
                TelaNovaInspecao(
                    viewModel = viewModel,
                    onVoltarClick = {
                        navController.navigate(Tela.Inicio.rota) {
                            popUpTo(Tela.Inicio.rota) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            
            // Rota: Histórico
            composable(Tela.Historico.rota) {
                TelaHistorico(viewModel = viewModel)
            }
        }
    }
}
