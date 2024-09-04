package com.example.jogo_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicia a interface do jogo chamando o Composable MeuJogo()
        setContent {
            MeuJogo()
        }
    }
}

@Composable
fun MeuJogo() {
    // Obtém a configuração atual do dispositivo, como orientação (retrata ou paisagem)
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    // Variáveis de estado que são mantidas ao longo da troca de orientação
    // O rememberSaveable preserva o estado mesmo se a tela girar
    var cliquesTotais by rememberSaveable { mutableStateOf(Random.nextInt(1, 50)) } // Define quantos cliques são necessários para ganhar
    var cliquesAtuais by rememberSaveable { mutableStateOf(0) } // Contador dos cliques atuais
    var exibirImagemDesistencia by rememberSaveable { mutableStateOf(false) } // Indica se o jogador desistiu
    var mensagem by rememberSaveable { mutableStateOf("Comece sua jornada") } // Mensagem exibida para o jogador

    // Define a imagem e mensagem baseada no progresso atual do jogador
    val imagemRes = when {
        exibirImagemDesistencia -> {
            mensagem = "Você desistiu"
            R.drawable.img_desistencia
        }
        cliquesAtuais >= cliquesTotais -> {
            mensagem = "Parabéns, você venceu!"
            R.drawable.img_conquista
        }
        cliquesAtuais >= cliquesTotais * 2 / 3 -> {
            mensagem = "Quase lá"
            R.drawable.img_final
        }
        cliquesAtuais >= cliquesTotais / 3 -> {
            mensagem = "Continue"
            R.drawable.img_mediana
        }
        else -> {
            mensagem = "Comece sua jornada"
            R.drawable.img_inicial
        }
    }

    // Define a interface do usuário
    Box(
        modifier = Modifier
            .fillMaxSize() // Preenche toda a tela
            .background( // Define um gradiente como fundo
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF212121), Color(0xFF3E3E3E)) // Cores do gradiente
                )
            )
    ) {
        // Imagem de fundo
        Image(
            painter = painterResource(id = R.drawable.img_background),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Ajusta a imagem ao tamanho da tela
            modifier = Modifier.fillMaxSize() // A imagem preenche toda a tela
        )

        // Verifica se o dispositivo está em modo paisagem
        if (isLandscape) {
            // Layout para modo paisagem (horizontal)
            Row(
                modifier = Modifier
                    .fillMaxSize() // Preenche a tela
                    .padding(16.dp), // Espaçamento interno
                horizontalArrangement = Arrangement.Center, // Alinha horizontalmente ao centro
                verticalAlignment = Alignment.CenterVertically // Alinha verticalmente ao centro
            ) {
                // Imagem representando o progresso
                Image(
                    painter = painterResource(id = imagemRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp) // Tamanho da imagem
                        .padding(16.dp) // Espaçamento interno
                        .border(2.dp, Color.White, RoundedCornerShape(12.dp)) // Borda arredondada branca
                        .shadow(8.dp, RoundedCornerShape(12.dp)) // Adiciona sombra à imagem
                )

                Spacer(modifier = Modifier.width(16.dp)) // Espaço entre a imagem e o texto

                // Coluna contendo a mensagem e os botões
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Mensagem exibida para o jogador
                    Text(
                        text = mensagem,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 26.sp,
                            fontFamily = FontFamily.Cursive, // Fonte cursiva para um estilo personalizado
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth() // Preenche a largura disponível
                            .background(Color.Black, RoundedCornerShape(12.dp)) // Fundo preto com bordas arredondadas
                            .padding(24.dp), // Espaçamento interno da mensagem
                        textAlign = TextAlign.Center // Texto centralizado
                    )

                    Spacer(modifier = Modifier.height(24.dp)) // Espaçamento entre a mensagem e os botões

                    // Botões de ação: Tentar de novo ou avançar/desistir
                    if (exibirImagemDesistencia || cliquesAtuais >= cliquesTotais) {
                        // Se o jogador desistiu ou venceu, exibe o botão para tentar de novo
                        StyledButton(
                            onClick = {
                                // Reinicia o jogo
                                cliquesAtuais = 0
                                cliquesTotais = Random.nextInt(1, 50)
                                exibirImagemDesistencia = false
                                mensagem = "Comece sua jornada"
                            },
                            modifier = Modifier.width(260.dp)
                        ) {
                            Text(
                                text = "Tentar de novo",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    } else {
                        // Se o jogo está em andamento, exibe os botões de avançar e desistir
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            StyledButton(onClick = {
                                // Incrementa o contador de cliques
                                if (cliquesAtuais < cliquesTotais) {
                                    cliquesAtuais++
                                }
                            }) {
                                Text(text = "Avançar", fontSize = 20.sp)
                            }

                            StyledButton(onClick = {
                                // Marca como desistência
                                exibirImagemDesistencia = true
                            }) {
                                Text(text = "Desistir", fontSize = 20.sp)
                            }
                        }
                    }
                }
            }
        } else {
            // Layout para modo retrato (vertical)
            Column(
                modifier = Modifier
                    .fillMaxSize() // Preenche a tela
                    .padding(16.dp), // Espaçamento interno
                horizontalAlignment = Alignment.CenterHorizontally, // Alinha ao centro horizontalmente
                verticalArrangement = Arrangement.Center // Alinha ao centro verticalmente
            ) {
                // Imagem representando o progresso
                Image(
                    painter = painterResource(id = imagemRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp) // Tamanho da imagem
                        .padding(16.dp) // Espaçamento interno
                        .border(2.dp, Color.White, CircleShape) // Borda circular branca
                        .shadow(8.dp, CircleShape) // Sombra circular
                )

                Spacer(modifier = Modifier.height(24.dp)) // Espaço entre a imagem e a mensagem

                // Mensagem exibida para o jogador
                Text(
                    text = mensagem,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 26.sp,
                        fontFamily = FontFamily.Cursive, // Fonte cursiva personalizada
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth() // Preenche a largura disponível
                        .background(Color.Black, RoundedCornerShape(12.dp)) // Fundo preto com bordas arredondadas
                        .padding(24.dp), // Espaçamento interno da mensagem
                    textAlign = TextAlign.Center // Texto centralizado
                )

                Spacer(modifier = Modifier.height(24.dp)) // Espaço entre a mensagem e os botões

                // Botões de ação
                if (exibirImagemDesistencia || cliquesAtuais >= cliquesTotais) {
                    // Botão para tentar de novo
                    StyledButton(
                        onClick = {
                            // Reinicia o jogo
                            cliquesAtuais = 0
                            cliquesTotais = Random.nextInt(1, 50)
                            exibirImagemDesistencia = false
                            mensagem = "Comece sua jornada"
                        },
                        modifier = Modifier.width(260.dp)
                    ) {
                        Text(
                            text = "Tentar de novo",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    // Botões de avançar e desistir
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StyledButton(onClick = {
                            // Incrementa o contador de cliques
                            if (cliquesAtuais < cliquesTotais) {
                                cliquesAtuais++
                            }
                        }) {
                            Text(text = "Avançar", fontSize = 20.sp)
                        }

                        StyledButton(onClick = {
                            // Marca como desistência
                            exibirImagemDesistencia = true
                        }) {
                            Text(text = "Desistir", fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}

// Composable StyledButton, responsável por estilizar os botões
@Composable
fun StyledButton(
    onClick: () -> Unit, // Ação ao clicar no botão
    modifier: Modifier = Modifier, // Modificadores do botão
    content: @Composable RowScope.() -> Unit // Conteúdo do botão
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF444444), // Cor do botão
            contentColor = Color.White // Cor do texto
        ),
        shape = RoundedCornerShape(12.dp), // Forma do botão (arredondado)
        border = BorderStroke(2.dp, Color(0xFFCCCCCC)), // Borda do botão
        modifier = modifier
            .height(60.dp) // Altura do botão
            .padding(8.dp) // Espaçamento externo
            .widthIn(min = 180.dp) // Largura mínima
            .shadow(8.dp, RoundedCornerShape(12.dp)) // Sombra no botão
    ) {
        content() // Conteúdo do botão (texto ou ícones)
    }
}

// Preview para ver como o layout se comporta no Android Studio
@Preview(showBackground = true)
@Composable
fun MeuJogoPreview() {
    MeuJogo()
}