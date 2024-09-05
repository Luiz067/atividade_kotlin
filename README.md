
# Jogo Kotlin - Interface e Lógica com Jetpack Compose

Este projeto é um simples jogo de cliques desenvolvido em Kotlin usando o Jetpack Compose para a interface. O jogo muda a imagem e a mensagem com base no progresso do jogador e oferece botões de interação para avançar ou desistir.

## Pré-requisitos

Para rodar o projeto, você precisará:

- Android Studio instalado (versão recomendada: Flamingo | 2022.2.1 Patch 2 ou superior)
- Um dispositivo Android físico ou um emulador configurado
- Kotlin configurado no seu Android Studio

## Como rodar o projeto

### 1. Clonar o repositório

Primeiro, clone o repositório do GitHub:

```bash
git clone https://github.com/Luiz067/atividade_kotlin.git
```

### 2. Abrir o projeto no Android Studio

- Abra o Android Studio.
- Clique em **File** > **Open**.
- Selecione a pasta onde o projeto foi clonado.

### 3. Configurar o Emulador ou Dispositivo

- **Emulador**: Caso ainda não tenha configurado um emulador Android, você pode criar um emulador através de **Tools** > **AVD Manager**.
- **Dispositivo Físico**: Conecte um dispositivo Android via USB e ative o modo de desenvolvedor nele.

### 4. Executar o projeto

- Certifique-se de que o projeto foi carregado corretamente no Android Studio.
- No topo da interface do Android Studio, selecione o dispositivo em que deseja rodar o app (emulador ou dispositivo físico).
- Clique no ícone de "Play" ou use o atalho `Shift + F10` para rodar o projeto.

O aplicativo será compilado e executado no dispositivo selecionado.

### 5. Estrutura do Projeto

O projeto possui uma estrutura básica com:

- **MainActivity.kt**: Contém a lógica principal do jogo e define a interface utilizando Jetpack Compose.
- **Recursos de imagem**: O projeto inclui diversas imagens (`img_inicial`, `img_mediana`, `img_final`, `img_conquista`, `img_desistencia`, etc.) que representam diferentes estados do jogo.

### 6. Gameplay

- Ao iniciar o jogo, uma imagem e uma mensagem são exibidas com a opção de "Avançar" ou "Desistir".
- O jogador precisa clicar em "Avançar" até atingir o número total de cliques necessários para vencer.
- A cada progresso, uma nova imagem é mostrada.
- Se o jogador desistir, uma imagem de desistência é exibida.

