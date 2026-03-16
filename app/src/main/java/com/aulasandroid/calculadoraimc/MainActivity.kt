package com.aulasandroid.calculadoraimc

import android.R.attr.fontWeight
import android.R.attr.onClick
import android.R.attr.text
import android.R.attr.x
import android.R.attr.y
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aulasandroid.calculadoraimc.ui.theme.CalculadoraIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculadoraIMCScreen(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun CalculadoraIMCScreen(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        //header
        var altura by remember {
            mutableStateOf("")
        }

        var peso by remember {
            mutableStateOf("")
        }

        var imc by remember {
            mutableDoubleStateOf( 0.0)
        }



        Column(
            modifier = Modifier.fillMaxWidth()
                .height(160.dp)
                .background(color = colorResource(R.color.color_app)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.bmi),
                contentDescription = "logo app",
                modifier = Modifier.size(80.dp)
                    .padding(vertical = 16.dp)
            )
            Text(
                text = "Calculadora de IMC",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp)
                    //offset sobrepõe, usamos o x e y para alinhar
                    .offset(y = (-30).dp)
                    .size(250.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF9F6F6)
                ),
                // a sombra do card
                elevation = CardDefaults.cardElevation(4.dp),

                )

            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Text(
                        modifier = Modifier
                            .offset(y = 20.dp),
                        text = "Seus dados",
                        fontSize = 24.sp,
                        color = colorResource(R.color.color_app),
                        fontWeight = FontWeight.Bold

                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .offset(y = 40.dp),
                        value = altura,
                        onValueChange = { novoValor ->
                            altura = novoValor
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = { Text(text = "Altura") },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(R.color.color_app)
                        ),
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .offset(y = 60.dp),
                        value = peso,
                        onValueChange = { novoValor ->
                            peso = novoValor
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = { Text(text = "Peso") },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(R.color.color_app)
                        ),
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )

                    Button(
                        modifier = Modifier
                            .width(280.dp)
                            .offset(y = 75.dp),
                        onClick = {

                           imc= calcularIMC(altura.toDouble(),peso.toDouble())

                            },


                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.color_app)
                        )
                    ) {
                        Text(
                            text = "Calcular".uppercase(),
                            color = Color.White

                        )
                    }
                }// fecha coluna dos componentes
            }// fecha o card

            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                colors = CardDefaults.cardColors(
                    containerColor = definirCorCategoria(imc)
                ),




            ) {  Row(
                modifier = Modifier
                    .fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(text = "%.1f".format(imc),
                    fontWeight = FontWeight.Bold,
                    color = Color.White

                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = definirCategoria(imc),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }

            }

        }// fecha coluna dos campos
    }// fecha coluna principal
}// fecha a função
