package com.engenharia.inspecao.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Esquema de cores do tema - Branco e Vermelho
 * Interface limpa e moderna para aplicativo de engenharia
 */
private val LightColorScheme = lightColorScheme(
    primary = Vermelho,
    onPrimary = Branco,
    primaryContainer = VermelhoClaro,
    onPrimaryContainer = Branco,
    
    secondary = VermelhoEscuro,
    onSecondary = Branco,
    
    background = Branco,
    onBackground = TextoEscuro,
    
    surface = Branco,
    onSurface = TextoEscuro,
    
    surfaceVariant = CinzaClaro,
    onSurfaceVariant = TextoMedio,
    
    outline = Cinza,
    
    error = VermelhoEscuro,
    onError = Branco
)

/**
 * Tema principal do aplicativo
 * Aplica cores, tipografia e formas aos componentes
 */
@Composable
fun InspecaoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
