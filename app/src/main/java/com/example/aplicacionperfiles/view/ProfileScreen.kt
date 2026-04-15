package com.example.aplicacionperfiles.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import coil.compose.AsyncImage
import com.example.aplicacionperfiles.R
import com.example.aplicacionperfiles.viewmodel.ProfileViewModel

val CyanNeon = Color(0xFF00E5FF)
val DarkBackground = Color(0xFF121212)
val CardBackground = Color(0xFF1E1E1E)

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
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSCXsbUwbvbDDyt-rtsoYDyFpcIHRjzgK286Q&s.jpg",
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.profile),
            error = painterResource(id = R.drawable.profile),
            modifier = Modifier
                .size(220.dp)
                .clip(CircleShape)
                .border(4.dp, CyanNeon, CircleShape)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = profile.nombre,
            fontSize = 28.sp,
            lineHeight = 32.sp,
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
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { navController.navigate("details") },
            colors = ButtonDefaults.buttonColors(containerColor = CyanNeon, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(55.dp)
        ) {
            Text(
                text = "VER PERFIL COMPLETO",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
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
                title = { Text("Mi Perfil", color = CyanNeon, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = CyanNeon)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {

            // BIOGRAFÍA
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Sobre Mí",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = profile.biografia,
                    color = Color.LightGray,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // TARJETA DESPLEGABLE DE CONTACTO (Animada)
            item {
                ExpandableContactCard(profile = profile)
                Spacer(modifier = Modifier.height(32.dp))
            }

            // LISTAS ESTILO "TAGS" MODERNOS
            item {
                SectionTitle("Intereses y Habilidades")
                TagRow(items = profile.intereses, isHighlight = true)
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                SectionTitle("Hobbies")
                TagRow(items = profile.hobbies)
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                SectionTitle("Deportes")
                TagRow(items = profile.deportes)
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                SectionTitle("Pasatiempos")
                TagRow(items = profile.pasatiempos)
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}


@Composable
fun ExpandableContactCard(profile: com.example.aplicacionperfiles.model.UserProfile) {

    var isExpanded by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "arrowRotation")

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded } // Hace que toda la tarjeta sea clickeable
            .border(1.dp, if (isExpanded) CyanNeon else Color.DarkGray, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = CyanNeon)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Datos de Contacto", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expandir",
                    tint = Color.Gray,
                    modifier = Modifier.rotate(rotationAngle) // Aplica la rotación animada
                )
            }


            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(bottom = 16.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Edad", color = Color.Gray, fontSize = 13.sp)
                            Text("${profile.edad} años", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Ubicación", color = Color.Gray, fontSize = 13.sp)
                            Text(profile.municipio, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text("Correo Electrónico", color = Color.Gray, fontSize = 13.sp)
                        Text(profile.correo, color = CyanNeon, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}


@Composable
fun TagRow(items: List<String>, isHighlight: Boolean = false) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { item ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isHighlight) CyanNeon.copy(alpha = 0.1f) else CardBackground)
                    .border(1.dp, if (isHighlight) CyanNeon else Color.DarkGray, RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = item,
                    color = if (isHighlight) CyanNeon else Color.LightGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}