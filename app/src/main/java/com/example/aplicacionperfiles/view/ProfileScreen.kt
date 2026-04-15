package com.example.aplicacionperfiles.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionperfiles.R
import com.example.aplicacionperfiles.viewmodel.ProfileViewModel

val CyanNeon = Color(0xFF00E5FF)

@Composable
fun AppNavigation(viewModel: ProfileViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "profile") {
        composable("profile") { ProfileScreen(viewModel, navController) }
        composable("details") { DetailsScreen(viewModel, navController) }
    }
}

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavHostController) {
    val profile = viewModel.profile

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(3.dp, CyanNeon, CircleShape)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = profile.nombre,
            fontSize = 26.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = CyanNeon,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = profile.programa,
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = "${profile.semestre} semestre",
            fontSize = 16.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { navController.navigate("details") },
            colors = ButtonDefaults.buttonColors(containerColor = CyanNeon, contentColor = Color.Black),
            modifier = Modifier.fillMaxWidth(0.85f).padding(vertical = 8.dp)
        ) {
            Text(
                text = "MOSTRAR INFORMACIÓN ADICIONAL",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(viewModel: ProfileViewModel, navController: NavHostController) {
    val profile = viewModel.profile

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Información Personal", color = CyanNeon, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = CyanNeon)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Perfil Personal", fontWeight = FontWeight.Bold, color = CyanNeon, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(profile.biografia, color = Color.White, fontSize = 15.sp, textAlign = TextAlign.Justify)

                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color.DarkGray)

                        Text("Datos de Contacto", fontWeight = FontWeight.Bold, color = CyanNeon, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Edad", color = Color.Gray, fontSize = 13.sp)
                                Text("${profile.edad} años", color = Color.White, fontSize = 16.sp)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Municipio", color = Color.Gray, fontSize = 13.sp)
                                Text(profile.municipio, color = Color.White, fontSize = 16.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text("Correo Electrónico", color = Color.Gray, fontSize = 13.sp)
                            Text(profile.correo, color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            item { SectionTitle("Hobbies") }
            items(profile.hobbies) { Text("• $it", fontSize = 16.sp, color = Color.White) }
            item { Spacer(modifier = Modifier.height(20.dp)) }

            item { SectionTitle("Pasatiempos") }
            items(profile.pasatiempos) { Text("• $it", fontSize = 16.sp, color = Color.White) }
            item { Spacer(modifier = Modifier.height(20.dp)) }

            item { SectionTitle("Deportes Favoritos") }
            items(profile.deportes) { Text("• $it", fontSize = 16.sp, color = Color.White) }
            item { Spacer(modifier = Modifier.height(20.dp)) }

            item { SectionTitle("Intereses Personales") }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(profile.intereses) { interes ->
                        Card(colors = CardDefaults.cardColors(containerColor = CyanNeon)) {
                            Text(
                                text = interes,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = CyanNeon,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}