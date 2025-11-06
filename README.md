# Trabalho de extensão da matéria Trabalho de Extensão — Programação para Dispositivos Móveis em Android
Sistema de Inspeção de Segurança
Desenvolvi um aplicativo em Kotlin, utilizando o Android Studio, com o objetivo de otimizar o processo de inspeção de segurança realizado por empresas especializadas.
O sistema permite:


Cadastro de novas inspeções, com:

Registro automático da data atual
Identificação do local inspecionado
Inclusão dos pontos verificados
Adição de observações técnicas sobre o ambiente



Visualização do histórico de inspeções, possibilitando o acompanhamento das condições de segurança ao longo do tempo.


O aplicativo foi pensado para ser uma ferramenta prática e eficiente, contribuindo para a organização e rastreabilidade das inspeções realizadas.

### 1. Clonar o Repositório

```bash
git clone https://github.com/TLisrael/trab_extensao_inspecao_de_incendio.git
cd trab_extensao_inspecao_de_incendio
```

### 2. Abrir no Android Studio

1. Abra o **Android Studio**
2. Clique em **File → Open**
3. Selecione a pasta do projeto clonado
4. Aguarde o Android Studio sincronizar o Gradle (pode levar alguns minutos na primeira vez)

###  3. Executar o Aplicativo

#### **Opção A: Emulador Android**
1. No Android Studio, clique em **Device Manager** (ícone de celular na barra lateral)
2. Crie um novo dispositivo virtual ou selecione um existente
3. Clique no botão **Run** ou pressione **Shift + F10**

#### **Opção B: Dispositivo Físico**
1. Ative o **Modo Desenvolvedor** no seu Android:
   - Vá em **Configurações → Sobre o telefone**
   - Toque 7 vezes em **Número da versão**
2. Ative a **Depuração USB**:
   - Vá em **Configurações → Sistema → Opções do desenvolvedor**
   - Ative **Depuração USB**
3. Conecte o dispositivo via USB ao computador
4. Clique no botão **Run** no Android Studio


## Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Kotlin** | 1.9.20 | Linguagem de programação |
| **Jetpack Compose** | 2024.01.00 | Framework de UI declarativa |
| **Room Database** | 2.6.1 | Banco de dados SQLite |
| **Navigation Compose** | 2.7.6 | Navegação entre telas |
| **Material Design 3** | - | Design system moderno |
| **Coroutines** | 1.7.3 | Programação assíncrona |
| **StateFlow** | - | Gerenciamento de estado reativo |
| **ViewModel** | 2.7.0 | Gerenciamento de lógica de UI |
