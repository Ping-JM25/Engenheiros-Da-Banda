package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.Engineer
import com.example.data.ServiceRequest
import com.example.ui.theme.*
import com.example.viewmodel.BandaViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BandaAppContent(viewModel: BandaViewModel) {
    val selectedRole by viewModel.selectedRole.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = SlateDark
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            SlateDark,
                            Color(0xFF0C121E) // Rich Deep Indigo Midnight gradient (breaks monotony)
                        )
                    )
                )
                .padding(innerPadding)
        ) {
            // Ambient Technical Glowing Orbs (Premium Tech Glows)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                
                // Tech Cyan Neon Glow in corner
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(TechCyan.copy(alpha = 0.09f), Color.Transparent),
                        center = Offset(width * 0.9f, height * 0.15f),
                        radius = width * 0.7f
                    ),
                    radius = width * 0.7f,
                    center = Offset(width * 0.9f, height * 0.15f)
                )
                
                // Warm Gold Glowing Orb in opposite corner
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(PremiumGold.copy(alpha = 0.07f), Color.Transparent),
                        center = Offset(width * 0.1f, height * 0.85f),
                        radius = width * 0.8f
                    ),
                    radius = width * 0.8f,
                    center = Offset(width * 0.1f, height * 0.85f)
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedRole) {
                    "NONE" -> RoleSelectionScreen(viewModel)
                    "CLIENT" -> ClientFlowContainer(viewModel)
                    "PROFESSIONAL" -> ProfessionalFlowContainer(viewModel)
                }
            }
        }
    }
}

// --- SCREEN 1: ROLE SELECTION ---
@Composable
fun RoleSelectionScreen(viewModel: BandaViewModel) {
    var showResetDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .drawBehind {
                // Cyber tech background subtle gridlines (re-imagined for premium feel)
                val step = 70f
                for (x in 0..size.width.toInt() step step.toInt()) {
                    drawLine(
                        color = Color(0xFF1E2A3A).copy(alpha = 0.25f),
                        start = Offset(x.toFloat(), 0f),
                        end = Offset(x.toFloat(), size.height),
                        strokeWidth = 1f
                    )
                }
                for (y in 0..size.height.toInt() step step.toInt()) {
                    drawLine(
                        color = Color(0xFF1E2A3A).copy(alpha = 0.25f),
                        start = Offset(0f, y.toFloat()),
                        end = Offset(size.width, y.toFloat()),
                        strokeWidth = 1f
                    )
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // App Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 45.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.sweepGradient(listOf(PremiumGold, TechCyan, PremiumGold)))
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(21.dp))
                ) {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.logo_official),
                        contentDescription = "Logo Oficial Engenheiros da Banda",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "ENGENHEIROS DA BANDA",
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.5.sp,
                color = PremiumGold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Técnicos de Confiança à sua Porta",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextGrey,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 6.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Premium Tech Network Hardware Banner Image
            Card(
                colors = CardDefaults.cardColors(containerColor = CardGrey),
                border = BorderStroke(
                    1.2.dp, 
                    Brush.linearGradient(listOf(TechCyan, PremiumGold))
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.img_tech_hardware),
                        contentDescription = "Infraestrutura Tecnológica",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                    // Semi-transparent SlateDark overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        SlateDark.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )
                    // Tech Badge overlay on bottom-start
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(TechCyan.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                .border(1.dp, TechCyan.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.NetworkCheck,
                                    contentDescription = null,
                                    tint = TechCyan,
                                    modifier = Modifier.size(11.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "SOLUÇÕES INTELIGENTES DE REDE E TI",
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TechCyan,
                                    letterSpacing = 0.8.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Selection Cards
        Column(
            verticalArrangement = Arrangement.spacedBy(22.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 35.dp)
        ) {
            Text(
                text = "Selecione o seu perfil de entrada",
                color = TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            // Card 1: CLIENT (Premium Glow Gold Border)
            Card(
                onClick = { viewModel.selectRole("CLIENT") },
                colors = CardDefaults.cardColors(containerColor = CardGrey),
                border = BorderStroke(1.8.dp, PremiumGold.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("role_client_card")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(PremiumGold.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonSearch,
                            contentDescription = "Cliente",
                            tint = PremiumGold,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Sou Cliente",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextWhite
                        )
                        Text(
                            text = "Preciso de assistência técnica e suporte rápido de Informática ou TI.",
                            fontSize = 13.sp,
                            color = TextGrey,
                            modifier = Modifier.padding(top = 4.dp),
                            lineHeight = 17.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Entrar",
                        tint = PremiumGold,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // Card 2: PROFESSIONAL (Premium Glow Tech Cyan Border)
            Card(
                onClick = { viewModel.selectRole("PROFESSIONAL") },
                colors = CardDefaults.cardColors(containerColor = CardGrey),
                border = BorderStroke(1.8.dp, TechCyan.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("role_prof_card")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(TechCyan.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Engineering,
                            contentDescription = "Engenheiro",
                            tint = TechCyan,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Sou Profissional",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextWhite
                        )
                        Text(
                            text = "Cadastre as suas competências técnicas e atenda chamados na área.",
                            fontSize = 13.sp,
                            color = TextGrey,
                            modifier = Modifier.padding(top = 4.dp),
                            lineHeight = 17.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Entrar",
                        tint = TechCyan,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        // Footer Brand & Reset
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Text(
                text = "© 2026 ENGENHEIROS DA BANDA",
                fontSize = 11.sp,
                fontWeight = FontWeight.Light,
                color = TextGrey.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Limpar base de dados",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = PremiumRed.copy(alpha = 0.9f),
                modifier = Modifier
                    .clickable { showResetDialog = true }
                    .padding(4.dp)
                    .testTag("db_reset_btn")
            )
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Restaurar Fábrica?", color = TextWhite) },
            text = { Text("Tem a certeza que deseja limpar as requisições, diagnósticos e reiniciar o app de forma limpa?", color = TextGrey) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetApp()
                        showResetDialog = false
                    }
                ) {
                    Text("Reiniciar", color = PremiumRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancelar", color = TextWhite)
                }
            },
            containerColor = CardGrey
        )
    }
}

// --- CONTAINER: CLIENT FLOW ---
@Composable
fun ClientFlowContainer(viewModel: BandaViewModel) {
    val currentClientScreen by viewModel.currentClientScreen.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        // Futuristic Top Navigation bar with high contrast gradient and bottom border
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(SlateDark, Color(0xFF0F1622))))
                .drawBehind {
                    // Crisp, thin high-contrast separator line at the bottom
                    drawLine(
                        color = BorderGrey,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 2f
                    )
                }
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (currentClientScreen == "LOGIN" || currentClientScreen == "REGISTER") {
                    IconButton(
                        onClick = {
                            if (currentClientScreen == "REGISTER") {
                                viewModel.navigateToClientScreen("LOGIN")
                            } else {
                                viewModel.selectRole("NONE")
                            }
                        },
                        modifier = Modifier.testTag("client_auth_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = PremiumGold,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                } else if (currentClientScreen != "HOME") {
                    IconButton(
                        onClick = {
                            when (currentClientScreen) {
                                "TRIAGE" -> viewModel.navigateToClientScreen("HOME")
                                "STATUS" -> viewModel.navigateToClientScreen("HOME")
                                "PROFILE" -> viewModel.navigateToClientScreen("HOME")
                                else -> viewModel.navigateToClientScreen("HOME")
                            }
                        },
                        modifier = Modifier.testTag("client_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = PremiumGold,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                } else {
                    IconButton(
                        onClick = { viewModel.selectRole("NONE") },
                        modifier = Modifier.testTag("client_back_to_role")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapVert,
                            contentDescription = "Mudar Função",
                            tint = PremiumGold,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(6.dp))
                
                Text(
                    text = "Cliente da Banda",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = TextWhite,
                    letterSpacing = 0.5.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (currentClientScreen != "LOGIN" && currentClientScreen != "REGISTER") {
                    IconButton(onClick = { viewModel.navigateToClientScreen("PROFILE") }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Perfil Cliente",
                            tint = PremiumGold,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when (currentClientScreen) {
                "LOGIN" -> ClientLoginScreen(viewModel)
                "REGISTER" -> ClientRegisterScreen(viewModel)
                "HOME" -> ClientHomeScreen(viewModel)
                "TRIAGE" -> ClientTriageScreen(viewModel)
                "STATUS" -> ClientStatusTrackingScreen(viewModel)
                "PROFILE" -> ClientProfileScreen(viewModel)
            }
        }
    }
}

// --- SCREEN: CLIENT HOME ---
@Composable
fun ClientHomeScreen(viewModel: BandaViewModel) {
    val clientName by viewModel.clientName.collectAsStateWithLifecycle()
    val activeRequest by viewModel.activeClientRequest.collectAsStateWithLifecycle()
    val allRequests by viewModel.allRequests.collectAsStateWithLifecycle()
    val allOnlineEngineers by viewModel.allOnlineEngineers.collectAsStateWithLifecycle()

    var viewModeMap by remember { mutableStateOf(false) }

    // 2. Interactive Tech Diagnostic panel (Entertaining & tech-oriented)
    var networkLatency by remember { mutableStateOf(24) }
    var systemStability by remember { mutableStateOf(99) }
    var activeTechTip by remember { mutableStateOf("Redes Wi-Fi instáveis? Tente posicionar o roteador em local livre de obstáculos e alto.") }
    
    // Simulating minor network variations on recompositions
    LaunchedEffect(Unit) {
        while(true) {
            kotlinx.coroutines.delay(4000)
            networkLatency = (15..35).random()
            systemStability = (97..100).random()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Futuristic Tech Hero Banner Image Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CardGrey),
                border = BorderStroke(
                    1.5.dp, 
                    Brush.linearGradient(listOf(TechCyan, PremiumGold))
                ),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.img_tech_client),
                        contentDescription = "Suporte Tecnológico Luanda",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                    // Overlay gradient for high-contrast text visibility
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        SlateDark.copy(alpha = 0.95f)
                                    )
                                )
                            )
                    )
                    // Floating indicators/holographic styling
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(TechCyan.copy(alpha = 0.25f), RoundedCornerShape(4.dp))
                                .border(1.dp, TechCyan, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "CENTRAL DE INOVAÇÃO DE TI",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = TechCyan,
                                letterSpacing = 1.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Olá, ${clientName.ifBlank { "Cliente da Banda" }}! 👋",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = TextWhite
                        )
                    }
                }
            }
        }

        // 2. Interactive Tech Diagnostic panel (Entertaining & tech-oriented)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CardGrey),
                border = BorderStroke(1.dp, BorderGrey),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Dns,
                                contentDescription = "Diagnóstico",
                                tint = TechCyan,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Terminal de Monitoramento de Rede",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                        }
                        
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SuccessGreen.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "SISTEMA ONLINE",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Diagnostic metrics row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(SlateDark.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                .border(1.dp, BorderGrey, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Text("Latência Luanda", fontSize = 10.sp, color = TextGrey)
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Text("${networkLatency}ms", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TechCyan)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Speed,
                                    contentDescription = null,
                                    tint = TechCyan,
                                    modifier = Modifier.size(12.dp).align(Alignment.CenterVertically)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(SlateDark.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                .border(1.dp, BorderGrey, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Text("Estabilidade VPN", fontSize = 10.sp, color = TextGrey)
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Text("${systemStability}%", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(12.dp).align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Changing tech tips panel
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF141F32), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "💡 DICA TECNOLÓGICA INTELIGENTE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PremiumGold
                            )
                            Icon(
                                imageVector = Icons.Default.Cached,
                                contentDescription = "Próxima Dica",
                                tint = PremiumGold,
                                modifier = Modifier
                                    .size(14.dp)
                                    .clickable {
                                        val tips = listOf(
                                            "Wi-Fi lento? Reiniciar o router limpa a cache de pacotes congestionados.",
                                            "Software travando? Uma formatação limpa ou atualização de drivers resolve 90% dos problemas de lentidão.",
                                            "Fiação elétrica quente de rede pode enfraquecer o sinal DSL. Afaste cabos paralelos de potência.",
                                            "Nunca partilhe a senha do seu roteador principal sem configurar uma rede de visitantes estéril."
                                        )
                                        val currentIdx = tips.indexOf(activeTechTip)
                                        activeTechTip = tips.filter { it != activeTechTip }.random()
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = activeTechTip,
                            fontSize = 12.sp,
                            color = TextWhite,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Active Request Alert Banner
        if (activeRequest != null) {
            item {
                Card(
                    onClick = { viewModel.navigateToClientScreen("STATUS") },
                    colors = CardDefaults.cardColors(containerColor = PremiumGold.copy(alpha = 0.15f)),
                    border = BorderStroke(1.dp, PremiumGold),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(PremiumGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DirectionsRun,
                                contentDescription = "Corrida",
                                tint = SlateDark,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Tem um chamado activo!",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                            Text(
                                text = "Área: ${activeRequest!!.category} • Estado: ${activeRequest!!.status}",
                                fontSize = 11.sp,
                                color = PremiumGold
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Ver Chamado",
                            tint = PremiumGold
                        )
                    }
                }
            }
        }

        // Switcher Tabs
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SlateDark)
                    .border(2.dp, BorderGrey, RoundedCornerShape(12.dp))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModeMap = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!viewModeMap) CardGrey else Color.Transparent,
                        contentColor = if (!viewModeMap) PremiumGold else TextGrey
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Category,
                            contentDescription = "Menu Especialidades",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Especialidades",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Button(
                    onClick = { viewModeMap = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (viewModeMap) CardGrey else Color.Transparent,
                        contentColor = if (viewModeMap) PremiumGold else TextGrey
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = "Menu Mapa",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Mapa de Luanda",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }

        // Show Map or List details
        if (viewModeMap) {
            item {
                Box(
                    modifier = Modifier
                        .height(360.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, BorderGrey, RoundedCornerShape(16.dp))
                ) {
                    LuandaGoogleMap(
                        engineers = allOnlineEngineers,
                        onEngineerSelected = { eng ->
                            viewModel.selectCategory(eng.specialty)
                            viewModel.updateProblemDescription("Solicitação directa para o especialista ${eng.name}. Favor atender ao meu problema urgente em Luanda.")
                        }
                    )
                }
            }
        } else {
            // Category Cards Grid Title
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Especialidades Técnicas",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(SuccessGreen)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${allOnlineEngineers.size + 3} Online",
                            fontSize = 11.sp,
                            color = SuccessGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Category Items List
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    CategoryCard(
                        title = "Informáticos",
                        description = "Software, redes estruturadas, administração de sistemas Linux/Cloud e desenvolvimento.",
                        icon = Icons.Default.Computer,
                        color = TechCyan,
                        onClick = { viewModel.selectCategory("Informático") }
                    )

                    CategoryCard(
                        title = "Técnicos de TI",
                        description = "Suporte a computadores físicos, reparações rápidas, Wi-Fi lento e formatação urgente.",
                        icon = Icons.Default.SettingsSuggest,
                        color = PremiumRed,
                        onClick = { viewModel.selectCategory("Técnico de TI") }
                    )
                }
            }

            // Historical Log of Requests
            item {
                Text(
                    text = "Histórico de Chamados Recentes",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            val clientRequests = allRequests.filter { it.clientPhone == viewModel.clientPhone.value }
            if (clientRequests.isEmpty()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardGrey),
                        border = BorderStroke(1.dp, BorderGrey),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Engineering,
                                contentDescription = "Lista Vazia",
                                tint = TextGrey,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Nenhum chamado efetuado",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextWhite
                            )
                            Text(
                                text = "A sua segurança e comodidade técnica começam aqui.",
                                fontSize = 11.sp,
                                color = TextGrey,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            } else {
                items(clientRequests) { req ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardGrey),
                        border = BorderStroke(1.dp, BorderGrey),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    val ic = when (req.category) {
                                        "Informático" -> Icons.Default.Computer
                                        else -> Icons.Default.SettingsSuggest
                                    }
                                    val badgeColor = when (req.category) {
                                        "Informático" -> TechCyan
                                        else -> PremiumRed
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(badgeColor.copy(alpha = 0.15f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = ic,
                                            contentDescription = req.category,
                                            tint = badgeColor,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = req.category,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextWhite
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(
                                            if (req.status == "COMPLETED") SuccessGreen.copy(alpha = 0.15f)
                                            else PremiumGold.copy(alpha = 0.15f)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = if (req.status == "COMPLETED") "Concluído" else "Em Andamento",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (req.status == "COMPLETED") SuccessGreen else PremiumGold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = req.problemDescription,
                                fontSize = 13.sp,
                                color = TextWhite,
                                maxLines = 2,
                                lineHeight = 17.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                                .format(Date(req.requestTime))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Solicitado em $formattedDate",
                                    fontSize = 11.sp,
                                    color = TextGrey
                                )

                                if (req.rating != null) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        repeat(req.rating) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = "Star",
                                                tint = PremiumGold,
                                                modifier = Modifier.size(12.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = CardGrey),
        border = BorderStroke(1.5.dp, color.copy(alpha = 0.4f)), // Glowing specialty-themed border
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("category_card_$title")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(color.copy(alpha = 0.18f))
                    .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextWhite
                )

                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = TextGrey,
                    modifier = Modifier.padding(top = 4.dp),
                    lineHeight = 17.sp
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Escolher",
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// --- SCREEN: CLIENT TRIAGE & AI ANALYSER ---
@Composable
fun ClientTriageScreen(viewModel: BandaViewModel) {
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val desc by viewModel.problemDescription.collectAsStateWithLifecycle()
    val isTriageLoading by viewModel.isTriageLoading.collectAsStateWithLifecycle()
    val aiResult by viewModel.aiTriageResult.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Step header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(PremiumGold.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Triage",
                    tint = PremiumGold,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Triagem de Problemas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Text(
                    text = "Categoria selecionada: $selectedCategory",
                    fontSize = 12.sp,
                    color = PremiumGold,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Text(
            text = "Explique brevemente o problema técnico que está ocorrendo para receber conselhos imediatos de segurança e ser direcionado ao melhor técnico.",
            fontSize = 13.sp,
            color = TextGrey,
            lineHeight = 18.sp
        )

        // Text input field
        OutlinedTextField(
            value = desc,
            onValueChange = { viewModel.updateProblemDescription(it) },
            placeholder = {
                Text(
                    text = if (selectedCategory == "Informático") {
                        "Ex: O computador portátil não liga, faz um bipe e cheira a componente quente..."
                    } else {
                        "Ex: O Wi-Fi não conecta no telemóvel dos meus avós, o roteador está aceso..."
                    },
                    color = TextGrey.copy(alpha = 0.6f)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = PremiumGold,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .testTag("triage_description_input"),
            shape = RoundedCornerShape(12.dp)
        )

        Button(
            onClick = {
                focusManager.clearFocus()
                viewModel.runAiTriage() 
            },
            enabled = desc.isNotBlank() && !isTriageLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = PremiumGold,
                contentColor = SlateDark
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("ai_analyser_btn"),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isTriageLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = SlateDark)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Analisar com IA", fontWeight = FontWeight.Black, fontSize = 14.sp)
                }
            }
        }

        // Triage results Panel
        if (aiResult != null && !isTriageLoading) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CardGrey),
                border = BorderStroke(1.dp, BorderGrey),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("triage_result_card")
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FactCheck,
                            contentDescription = "Diagnóstico",
                            tint = SuccessGreen,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Análise Prática Recebida",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = BorderGrey)

                    // Helper to render basic markdown bold elements cleanly in simple compose Text
                    val parsedText = aiResult!!
                        .replace("### ", "")
                        .replace("**", "")

                    Text(
                        text = parsedText,
                        fontSize = 13.sp,
                        color = TextWhite,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            // Confirm call button
            Card(
                colors = CardDefaults.cardColors(containerColor = PremiumGold.copy(alpha = 0.08f)),
                border = BorderStroke(1.dp, PremiumGold.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Área sugerida pelo Diagnóstico: $selectedCategory",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PremiumGold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.createServiceRequest() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SuccessGreen,
                            contentColor = SlateDark
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("confirm_call_engineer_btn")
                    ) {
                        Icon(imageVector = Icons.Default.LocalTaxi, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Solicitar Engenheiro Sob Demanda",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        } else if (!isTriageLoading && aiResult == null) {
            // Quick trigger button standard without Gemini if they prefer to bypass
            Button(
                onClick = { viewModel.createServiceRequest() },
                enabled = desc.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BorderGrey,
                    contentColor = TextWhite
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("bypass_ai_request_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Chamar Técnico Sem Analisar", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

// --- SCREEN: GPS RADAR SIMULATED MAP TRACKING ---
@Composable
fun ClientStatusTrackingScreen(viewModel: BandaViewModel) {
    val req by viewModel.activeClientRequest.collectAsStateWithLifecycle()
    val eng by viewModel.matchedEngineer.collectAsStateWithLifecycle()
    val dist by viewModel.simulatedDistance.collectAsStateWithLifecycle()
    val showArrivalBanner by viewModel.showArrivalBanner.collectAsStateWithLifecycle()

    var ratingInput by remember { mutableStateOf(5) }
    var feedbackInput by remember { mutableStateOf("") }

    var showCancelDialog by remember { mutableStateOf(false) }
    var selectedReasonIndex by remember { mutableStateOf(0) }
    val reasons = listOf(
        "Tempo de espera previsto é muito longo",
        "Já resolvi o problema técnico sozinho ou por outra via",
        "Cometi um erro ou escolhi a categoria errada"
    )

    if (showArrivalBanner) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissArrivalBanner() },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Chegou",
                        tint = SuccessGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Técnico Chegou!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                }
            },
            text = {
                Text(
                    text = "${eng?.name ?: "O profissional"} chegou à sua localização de assistência em Luanda! Ele está pronto para resolver o seu problema técnico.",
                    fontSize = 13.sp,
                    color = TextGrey,
                    lineHeight = 18.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissArrivalBanner() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SuccessGreen,
                        contentColor = SlateDark
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("OK, Excelente", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            },
            containerColor = SlateDark,
            tonalElevation = 6.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.border(1.5.dp, SuccessGreen, RoundedCornerShape(16.dp))
        )
    }

    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Alerta",
                        tint = PremiumRed,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Cancelar Chamado?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Por favor, indique-nos a razão do cancelamento para podermos melhorar o nosso serviço em Luanda:",
                        fontSize = 13.sp,
                        color = TextGrey,
                        lineHeight = 18.sp
                    )
                    
                    reasons.forEachIndexed { index, reason ->
                        val isSelected = selectedReasonIndex == index
                        Card(
                            onClick = { selectedReasonIndex = index },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) PremiumRed.copy(alpha = 0.12f) else CardGrey
                            ),
                            border = BorderStroke(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) PremiumRed else BorderGrey
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedReasonIndex = index },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = PremiumRed,
                                        unselectedColor = TextGrey
                                    )
                                )
                                Text(
                                    text = reason,
                                    fontSize = 12.sp,
                                    color = TextWhite,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val finalReason = reasons.getOrElse(selectedReasonIndex) { "Motivo de cancelamento desconhecido" }
                        viewModel.cancelActiveRequest(finalReason)
                        showCancelDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PremiumRed,
                        contentColor = TextWhite
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("confirm_cancel_active_btn")
                ) {
                    Text("Confirmar Cancelamento", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showCancelDialog = false },
                    modifier = Modifier.testTag("close_cancel_dialog_btn")
                ) {
                    Text("Fechar / Voltar Atrás", color = TextGrey, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                }
            },
            containerColor = SlateDark,
            tonalElevation = 6.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.border(1.5.dp, BorderGrey, RoundedCornerShape(16.dp))
        )
    }

    if (req == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                tint = SuccessGreen,
                contentDescription = "Sem pedidos",
                modifier = Modifier.size(54.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sem Chamados Activos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.navigateToClientScreen("HOME") }) {
                Text("Voltar ao Início")
            }
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top status card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CardGrey),
                border = BorderStroke(1.dp, BorderGrey),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val statusLabel = when (req!!.status) {
                        "PENDING" -> "Procurando Engenheiro..."
                        "ACCEPTED" -> "Técnico a Caminho da sua Residência"
                        "IN_PROGRESS" -> "Trabalho em Progresso no Local"
                        else -> "Serviço Concluído"
                    }
                    val statusColor = when (req!!.status) {
                        "PENDING" -> PremiumGold
                        "IN_PROGRESS" -> TechCyan
                        "ACCEPTED" -> PremiumGold
                        else -> SuccessGreen
                    }

                    Text(
                        text = statusLabel.uppercase(),
                        color = statusColor,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val kmText = if (req!!.status == "PENDING") {
                        "A aguardar aceite..."
                    } else if (req!!.status == "ACCEPTED") {
                        String.format(Locale.US, "%.1f km restantes", dist)
                    } else if (req!!.status == "IN_PROGRESS") {
                        "Engenheiro no local da obra"
                    } else {
                        "Pronto para avaliação"
                    }

                    Text(
                        text = kmText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Simulated route visual progress
                    LinearProgressIndicator(
                        progress = {
                            if (req!!.status == "PENDING") {
                                0.05f
                            } else if (req!!.status == "ACCEPTED") {
                                ((2.4 - dist) / 2.4).toFloat().coerceIn(0f, 1f)
                            } else if (req!!.status == "IN_PROGRESS" || req!!.status == "COMPLETED") {
                                1f
                            } else {
                                0.1f
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = statusColor,
                        trackColor = BorderGrey
                    )
                }
            }
        }

        // INTERACTIVE LUANDA MAP TRACKING THE TECHNICIAN LIVE
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateDark),
                border = BorderStroke(1.5.dp, BorderGrey),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                val allOnlineEngineers by viewModel.allOnlineEngineers.collectAsStateWithLifecycle()
                LuandaGoogleMap(
                    engineers = allOnlineEngineers,
                    userLat = viewModel.clientLat,
                    userLng = viewModel.clientLng,
                    trackingEngineerId = eng?.id,
                    simulatedDistance = dist
                )
            }
        }

        // Specialist Profile Details
        if (eng == null) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardGrey),
                    border = BorderStroke(1.5.dp, PremiumGold.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth().testTag("pending_instructions_card")
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = PremiumGold,
                            modifier = Modifier.size(28.dp),
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "A Aguardar Aceitação pelo Técnico...",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "A sua solicitação foi publicada no mural de assistências de Luanda. Um profissional qualificado do Técnico da Banda irá analisar e aceitar o seu chamado a qualquer momento.",
                            fontSize = 12.sp,
                            color = TextGrey,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = BorderGrey, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "💡 Para simular os dois lados da Interconexão:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PremiumGold,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Text(
                            text = "1. Mude para a função 'Técnico da Banda' (clique no botão superior esquerdo de setas).\n" +
                                   "2. Entre na conta do técnico (ou registe-se).\n" +
                                   "3. Ative o interruptor 'Online' e aceite este chamado!",
                            fontSize = 11.sp,
                            color = TextGrey,
                            modifier = Modifier.align(Alignment.Start).padding(top = 4.dp),
                            lineHeight = 15.sp
                        )
                        
                        Spacer(modifier = Modifier.height(18.dp))
                        
                        Button(
                            onClick = { viewModel.simulateAutoMatch(req!!.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = PremiumGold, contentColor = SlateDark),
                            modifier = Modifier.fillMaxWidth().height(42.dp).testTag("bypass_automatch_btn"),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Simular Auto-Aceitar (Atalho)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        if (eng != null) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardGrey),
                    border = BorderStroke(1.dp, BorderGrey),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(PremiumGold.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Engineering,
                                    contentDescription = "Foto",
                                    tint = PremiumGold,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = eng!!.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextWhite
                                )
                                Text(
                                    text = "${eng!!.specialty} • ${eng!!.experienceYears} Anos Exp.",
                                    fontSize = 12.sp,
                                    color = TextGrey
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = PremiumGold,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = String.format(Locale.US, "%.1f", eng!!.rating),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextWhite
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Registo: ${eng!!.certId}",
                                        fontSize = 11.sp,
                                        color = SuccessGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Biografia do Técnico:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Text(
                            text = eng!!.description,
                            fontSize = 12.sp,
                            color = TextGrey,
                            modifier = Modifier.padding(top = 2.dp),
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Customer safety assurance stamp
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SuccessGreen.copy(alpha = 0.1f))
                                .padding(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                        ) {
                            Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = "Safe", tint = SuccessGreen, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Técnico verificado com biometria e registo de segurança.", fontSize = 10.sp, color = SuccessGreen, fontWeight = FontWeight.Medium)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Trigger Call / Message simulations
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = { /* No-op, mock call */ },
                                modifier = Modifier.weight(1f),
                                border = BorderStroke(1.dp, BorderGrey),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
                            ) {
                                Icon(imageVector = Icons.Default.Phone, contentDescription = "Chamar")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Ligar: ${eng!!.phone}", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }

        // Active request content problem
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CardGrey),
                border = BorderStroke(1.dp, BorderGrey),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Descrição do Serviço Solicitado:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PremiumGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = req!!.problemDescription,
                        fontSize = 13.sp,
                        color = TextWhite,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Close and Submit Review Card (Only appears when In Progress or Completed)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CardGrey),
                border = BorderStroke(1.dp, BorderGrey),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Concluir e Avaliar Técnico",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "Uma vez finalizado o trabalho do engenheiro na sua casa, deixe a sua avaliação e comentário seguro.",
                        fontSize = 11.sp,
                        color = TextGrey,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Stars rating selector
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (star in 1..5) {
                            IconButton(
                                onClick = { ratingInput = star },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = if (star <= ratingInput) Icons.Default.Star else Icons.Default.StarOutline,
                                    contentDescription = "Estrela $star",
                                    tint = PremiumGold,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Text comment
                    OutlinedTextField(
                        value = feedbackInput,
                        onValueChange = { feedbackInput = it },
                        placeholder = { Text("Comentário sobre a pontualidade, atendimento e segurança...", fontSize = 12.sp, color = TextGrey.copy(alpha = 0.5f)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            focusedBorderColor = PremiumGold,
                            unfocusedBorderColor = BorderGrey,
                            focusedContainerColor = SlateDark,
                            unfocusedContainerColor = SlateDark
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .testTag("client_feedback_input"),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { viewModel.submitRatingAndFeedback(req!!.id, ratingInput, feedbackInput) },
                        colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen, contentColor = SlateDark),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("submit_review_and_finish_btn")
                    ) {
                        Text("Submeter Avaliação e Finalizar", fontWeight = FontWeight.Black)
                    }
                }
            }
        }

        // Crisis Cancel request option
        item {
            TextButton(
                onClick = { showCancelDialog = true },
                modifier = Modifier.testTag("cancel_active_request_btn")
            ) {
                Text("Cancelar Pedido ou Chamado", color = PremiumRed, fontWeight = FontWeight.Bold)
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// --- SCREEN: CLIENT PROFILE ---
@Composable
fun ClientProfileScreen(viewModel: BandaViewModel) {
    val name by viewModel.clientName.collectAsStateWithLifecycle()
    val phone by viewModel.clientPhone.collectAsStateWithLifecycle()
    val email by viewModel.clientEmailInput.collectAsStateWithLifecycle()
    val morada by viewModel.clientMorada.collectAsStateWithLifecycle()
    val photoVerified by viewModel.clientPhotoVerified.collectAsStateWithLifecycle()

    var nameInput by remember { mutableStateOf(name.ifBlank { "Cliente da Banda" }) }
    var phoneInput by remember { mutableStateOf(phone.ifBlank { "900000000" }) }
    var emailInput by remember { mutableStateOf(email.ifBlank { "cliente@banda.co.ao" }) }
    var moradaInput by remember { mutableStateOf(morada.ifBlank { "Luanda, Angola" }) }
    var photoVerifiedState by remember { mutableStateOf(photoVerified) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Box(contentAlignment = Alignment.BottomEnd) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Photo",
                    tint = PremiumGold,
                    modifier = Modifier.size(88.dp)
                )
                if (photoVerifiedState) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verified Profile",
                        tint = SuccessGreen,
                        modifier = Modifier
                            .size(26.dp)
                            .background(SlateDark, CircleShape)
                            .padding(2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Perfil do Cliente da Banda",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextWhite
            )

            Text(
                text = "Mantenha as suas informações de contacto e residencial sempre atualizadas em Luanda. Estes dados são exibidos de forma auditada para garantir a segurança dos profissionais técnicos.",
                fontSize = 12.sp,
                color = TextGrey,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp),
                lineHeight = 16.sp
            )
        }

        item {
            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Nome do Cliente") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("client_profile_name"),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = phoneInput,
                onValueChange = { phoneInput = it },
                label = { Text("Número de Telemóvel") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("client_profile_phone"),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = emailInput,
                onValueChange = { emailInput = it },
                label = { Text("Correio Electrónico / E-mail") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("client_profile_email"),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = moradaInput,
                onValueChange = { moradaInput = it },
                label = { Text("Endereço / Morada de Luanda") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("client_profile_morada"),
                singleLine = true
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardGrey, RoundedCornerShape(8.dp))
                    .border(1.dp, BorderGrey, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Selo de Conta Verificada", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 12.sp)
                    Text("Exibe o distintivo verificado para atestar a autenticidade dos seus dados residenciais.", fontSize = 10.sp, color = TextGrey)
                }
                Switch(
                    checked = photoVerifiedState,
                    onCheckedChange = { photoVerifiedState = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = SuccessGreen,
                        checkedTrackColor = SuccessGreen.copy(alpha = 0.3f),
                        uncheckedThumbColor = TextGrey,
                        uncheckedTrackColor = BorderGrey
                    )
                )
            }
        }

        item {
            Button(
                onClick = { viewModel.saveClientDetailedProfile(nameInput, phoneInput, emailInput, moradaInput, photoVerifiedState) },
                colors = ButtonDefaults.buttonColors(containerColor = PremiumGold, contentColor = SlateDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("save_client_profile_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar Alterações", fontWeight = FontWeight.Bold)
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// --- SCREEN: CLIENT LOGIN ---
@Composable
fun ClientLoginScreen(viewModel: BandaViewModel) {
    var identifierInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    val loginError by viewModel.clientLoginError.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateDark)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(30.dp))
            // Gorgeous real-world tech themed avatar/logo
            Box(
                modifier = Modifier
                    .size(95.dp)
                    .clip(CircleShape)
                    .background(Brush.sweepGradient(listOf(TechCyan, PremiumGold, TechCyan)))
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.img_tech_client),
                        contentDescription = "Cliente da Banda",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Cliente da Banda",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextWhite,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "Entre na sua conta para solicitar assistências qualificadas",
                fontSize = 12.sp,
                color = TextGrey,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 10.dp)
            )
        }

        item {
            if (loginError != null) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = PremiumRed.copy(alpha = 0.15f)),
                    border = BorderStroke(1.dp, PremiumRed),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = loginError!!,
                        color = PremiumRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        item {
            OutlinedTextField(
                value = identifierInput,
                onValueChange = { identifierInput = it },
                label = { Text("Número de Telemóvel ou E-mail") },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = PremiumGold) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier.fillMaxWidth().testTag("client_login_identifier"),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = passwordInput,
                onValueChange = { passwordInput = it },
                label = { Text("Senha de Acesso") },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = PremiumGold) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier.fillMaxWidth().testTag("client_login_password"),
                singleLine = true
            )
        }

        item {
            Button(
                onClick = { viewModel.loginClient(identifierInput, passwordInput) },
                colors = ButtonDefaults.buttonColors(containerColor = PremiumGold, contentColor = SlateDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("client_login_submit_btn"),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Entrar na Conta", fontWeight = FontWeight.Bold)
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Ainda não está cadastrado? ", color = TextGrey, fontSize = 13.sp)
                TextButton(
                    onClick = { viewModel.navigateToClientScreen("REGISTER") },
                    modifier = Modifier.testTag("go_to_client_register")
                ) {
                    Text("Cria conta", color = PremiumGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        item {
            TextButton(
                onClick = { viewModel.selectRole("NONE") },
                modifier = Modifier.testTag("client_login_back_roll")
            ) {
                Text("Voltar ao Selector de Funções", color = PremiumGold.copy(alpha = 0.7f), fontSize = 12.sp)
            }
        }
    }
}

// --- SCREEN: CLIENT REGISTER ---
@Composable
fun ClientRegisterScreen(viewModel: BandaViewModel) {
    var nameInput by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var moradaInput by remember { mutableStateOf("") }
    var photoVerified by remember { mutableStateOf(true) }
    var hasSimulatedPhoto by remember { mutableStateOf(false) }

    var registerError by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateDark)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Gorgeous tech themed real avatar/logo for registration
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Brush.sweepGradient(listOf(TechCyan, PremiumGold, TechCyan)))
                    .padding(2.4.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.img_tech_client),
                        contentDescription = "Cliente da Banda",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Cadastramento de Cliente",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextWhite,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Os seus dados transmitem confiança e segurança ao profissional correspondido",
                fontSize = 12.sp,
                color = TextGrey,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 10.dp)
            )
        }

        if (registerError != null) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = PremiumRed.copy(alpha = 0.15f)),
                    border = BorderStroke(1.dp, PremiumRed),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = registerError!!,
                        color = PremiumRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        // Subtitle section for Security Details
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SuccessGreen.copy(alpha = 0.08f)),
                border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = "Trust", tint = SuccessGreen)
                    Column {
                        Text("Perfil de Cliente Verificado", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 12.sp)
                        Text("Técnicos aceitam chamados 3x mais rápido de perfis identificados com morada e contacto de confiança.", fontSize = 10.sp, color = TextGrey)
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Nome Completo") },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = PremiumGold) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier.fillMaxWidth().testTag("client_register_name"),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = phoneInput,
                onValueChange = { phoneInput = it },
                label = { Text("Telemóvel (Contacto em Luanda)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = PremiumGold) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier.fillMaxWidth().testTag("client_register_phone"),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = emailInput,
                onValueChange = { emailInput = it },
                label = { Text("Endereço de E-mail") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = PremiumGold) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier.fillMaxWidth().testTag("client_register_email"),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = passwordInput,
                onValueChange = { passwordInput = it },
                label = { Text("Crie uma Senha") },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = PremiumGold) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier.fillMaxWidth().testTag("client_register_password"),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = moradaInput,
                onValueChange = { moradaInput = it },
                label = { Text("Morada em Luanda (Ex: Luanda Sul, Talatona...)") },
                placeholder = { Text("Introduza a sua morada residencial...", fontSize = 12.sp, color = TextGrey.copy(alpha = 0.5f)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = PremiumGold) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = PremiumGold,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier.fillMaxWidth().testTag("client_register_morada"),
                singleLine = true
            )
        }

        // BI/Passport Photo upload simulation section to transmit trust
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CardGrey),
                border = BorderStroke(1.dp, if (hasSimulatedPhoto) SuccessGreen else BorderGrey),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Foto de Perfil com Documento (BI/NIF)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "Necessário para validar que você é um cliente real, transmitindo absoluta segurança ao técnico da plataforma.",
                        fontSize = 11.sp,
                        color = TextGrey,
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp),
                        lineHeight = 15.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { hasSimulatedPhoto = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (hasSimulatedPhoto) SuccessGreen.copy(alpha = 0.2f) else PremiumGold.copy(alpha = 0.2f),
                                contentColor = if (hasSimulatedPhoto) SuccessGreen else PremiumGold
                            ),
                            border = BorderStroke(1.dp, if (hasSimulatedPhoto) SuccessGreen else PremiumGold),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (hasSimulatedPhoto) "Foto Capturada" else "Capturar/Carregar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        if (hasSimulatedPhoto) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(imageVector = Icons.Default.Verified, contentDescription = "Verificado", tint = SuccessGreen, modifier = Modifier.size(18.dp))
                                Text("Verificada", color = SuccessGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Checkbox(
                    checked = photoVerified,
                    onCheckedChange = { photoVerified = it },
                    colors = CheckboxDefaults.colors(checkedColor = PremiumGold, uncheckedColor = TextGrey)
                )
                Text(
                    text = "Desejo partilhar o meu selo verificado para maior confiança e atendimento célere.",
                    color = TextGrey,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Button(
                onClick = {
                    if (nameInput.isBlank() || phoneInput.isBlank() || emailInput.isBlank() || passwordInput.isBlank() || moradaInput.isBlank()) {
                        registerError = "Por favor, preencha todos os dados essenciais para o seu registo de confiança!"
                    } else {
                        registerError = null
                        viewModel.registerClient(
                            name = nameInput,
                            phone = phoneInput,
                            email = emailInput,
                            pass = passwordInput,
                            morada = moradaInput,
                            isPhotoVerified = photoVerified,
                            photoBase64 = if (hasSimulatedPhoto) "MOCK_PHOTO_DATA" else null
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PremiumGold, contentColor = SlateDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("client_register_submit_btn"),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Preencher e Guardar Dados", fontWeight = FontWeight.Bold)
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Já possui uma conta ativa? ", color = TextGrey, fontSize = 13.sp)
                TextButton(onClick = { viewModel.navigateToClientScreen("LOGIN") }) {
                    Text("Faça Entrar", color = PremiumGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}


// ============================================
// ============================================
// --- CONTAINER: PROFESSIONAL FLOW ---
@Composable
fun ProfessionalFlowContainer(viewModel: BandaViewModel) {
    val currentProfScreen by viewModel.currentProfessionalScreen.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        // Futuristic Top Navigation bar for Professional (high contrast gradient and bottom border)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(SlateDark, Color(0xFF0F1622))))
                .drawBehind {
                    // Crisp separator border at the bottom
                    drawLine(
                        color = BorderGrey,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 2f
                    )
                }
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        if (currentProfScreen == "LOGIN") {
                            viewModel.selectRole("NONE")
                        } else {
                            viewModel.navigateToProfessionalScreen("LOGIN")
                        }
                    },
                    modifier = Modifier.testTag("prof_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = TechCyan,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Técnico da Banda",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = TextWhite,
                    letterSpacing = 0.5.sp
                )
            }

            if (currentProfScreen == "DASHBOARD" || currentProfScreen == "CREDENTIALS") {
                IconButton(onClick = { viewModel.navigateToProfessionalScreen("CREDENTIALS") }) {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        contentDescription = "Credenciais",
                        tint = TechCyan,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when (currentProfScreen) {
                "LOGIN" -> ProfessionalLoginScreen(viewModel)
                "REGISTER" -> ProfessionalRegisterScreen(viewModel)
                "DASHBOARD" -> ProfessionalDashboardScreen(viewModel)
                "CREDENTIALS" -> ProfessionalCredentialsScreen(viewModel)
            }
        }
    }
}

// --- SCREEN: PROFESSIONAL PORTAL LOGIN ---
@Composable
fun ProfessionalLoginScreen(viewModel: BandaViewModel) {
    var identifierInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    val loginError by viewModel.profLoginError.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Premium tech themed professional real avatar
        Box(
            modifier = Modifier
                .size(95.dp)
                .clip(CircleShape)
                .background(Brush.sweepGradient(listOf(SuccessGreen, TechCyan, SuccessGreen)))
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            ) {
                Image(
                    painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.img_tech_professional),
                    contentDescription = "Portal do Profissional",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Portal do Profissional",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextWhite
        )

        Text(
            text = "Insira as suas credenciais para aceder à sua conta técnica e gerir novos chamados de assistência.",
            fontSize = 12.sp,
            color = TextGrey,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp),
            lineHeight = 16.sp
        )

        OutlinedTextField(
            value = identifierInput,
            onValueChange = { identifierInput = it },
            label = { Text("Número de Telemóvel ou E-mail") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("prof_login_phone"),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            label = { Text("Senha de Acesso") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("prof_login_password"),
            singleLine = true
        )

        if (loginError != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(PremiumRed.copy(alpha = 0.12f))
                    .border(1.dp, PremiumRed, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ErrorOutline,
                    contentDescription = "Erro de Login",
                    tint = PremiumRed,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = loginError ?: "",
                    color = TextWhite,
                    fontSize = 11.sp,
                    modifier = Modifier.weight(1f),
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.loginProfessional(identifierInput, passwordInput) },
            enabled = identifierInput.isNotBlank() && passwordInput.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = TechCyan, contentColor = SlateDark),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("prof_login_btn"),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Entrar", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { viewModel.navigateToProfessionalScreen("REGISTER") },
            modifier = Modifier.testTag("prof_goto_register_btn")
        ) {
            Text("Ainda não está cadastrado? Cria conta.", color = TechCyan, fontWeight = FontWeight.SemiBold)
        }
    }
}

// --- SCREEN: PROFESSIONAL SIGNUP/REGISTER (CADASTRAMENTO) ---
@Composable
fun ProfessionalRegisterScreen(viewModel: BandaViewModel) {
    val name by viewModel.profNameInput.collectAsStateWithLifecycle()
    val phone by viewModel.profPhoneInput.collectAsStateWithLifecycle()
    val email by viewModel.profEmailInput.collectAsStateWithLifecycle()
    val password by viewModel.profPasswordInput.collectAsStateWithLifecycle()
    val workHistory by viewModel.profWorkHistoryInput.collectAsStateWithLifecycle()
    val specialty by viewModel.profSpecialtyInput.collectAsStateWithLifecycle()
    val skills by viewModel.profSkillsInput.collectAsStateWithLifecycle()
    val experience by viewModel.profExperienceInput.collectAsStateWithLifecycle()
    val desc by viewModel.profDescInput.collectAsStateWithLifecycle()

    val specialties = listOf("Informático", "Técnico de TI")
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Brush.sweepGradient(listOf(SuccessGreen, TechCyan, SuccessGreen)))
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.img_tech_professional),
                        contentDescription = "Profissional da Banda",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = "Cadastramento",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Text(
                    text = "Portal do Técnico",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TechCyan
                )
            }
        }

        Text(
            text = "Preencha as suas informações e experiências essenciais. Após a conclusão, os seus dados serão guardados com segurança.",
            fontSize = 12.sp,
            color = TextGrey,
            lineHeight = 17.sp
        )

        // Fields
        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.updateProfFormFields(name = it) },
            label = { Text("Nome Completo") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("prof_reg_name"),
            singleLine = true
        )

        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.updateProfFormFields(email = it) },
            label = { Text("E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("prof_reg_email"),
            singleLine = true
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { viewModel.updateProfFormFields(phone = it) },
            label = { Text("Contacto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("prof_reg_phone"),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.updateProfFormFields(password = it) },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("prof_reg_password"),
            singleLine = true
        )

        // Specialty Selector
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = specialty,
                onValueChange = {},
                readOnly = true,
                label = { Text("Área de Especialização") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = TechCyan,
                    unfocusedBorderColor = BorderGrey,
                    focusedContainerColor = CardGrey,
                    unfocusedContainerColor = CardGrey
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .testTag("prof_reg_specialty_trigger"),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            )

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .background(CardGrey)
            ) {
                specialties.forEach { spec ->
                    DropdownMenuItem(
                        text = { Text(spec, color = TextWhite) },
                        onClick = {
                            viewModel.updateProfFormFields(specialty = spec)
                            isExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = experience,
            onValueChange = { viewModel.updateProfFormFields(experience = it) },
            label = { Text("Experiência (Anos)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("prof_reg_experience"),
            singleLine = true
        )

        OutlinedTextField(
            value = workHistory,
            onValueChange = { viewModel.updateProfFormFields(workHistory = it) },
            label = { Text("Empresas por onde trabalhou ou clientes") },
            placeholder = { Text("Quais empresas ou tipos de clientes já realizou serviços?", fontSize = 11.sp, color = TextGrey) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .testTag("prof_reg_work_history"),
            maxLines = 4
        )

        OutlinedTextField(
            value = skills,
            onValueChange = { viewModel.updateProfFormFields(skills = it) },
            label = { Text("Principais Habilidades Técnicas") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("prof_reg_skills")
        )

        OutlinedTextField(
            value = desc,
            onValueChange = { viewModel.updateProfFormFields(description = it) },
            label = { Text("Resumo de Qualificações de Biografia") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = BorderGrey,
                focusedContainerColor = CardGrey,
                unfocusedContainerColor = CardGrey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .testTag("prof_reg_desc"),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { viewModel.submitProfessionalRegistration() },
            enabled = name.isNotBlank() && phone.isNotBlank() && password.isNotBlank() && email.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = TechCyan, contentColor = SlateDark),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("prof_reg_submit"),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Guardar Dados", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// --- SCREEN: PROFESSIONAL DASHBOARD ---
@Composable
fun ProfessionalDashboardScreen(viewModel: BandaViewModel) {
    val profile by viewModel.currentEngineerProfile.collectAsStateWithLifecycle()
    val activeReq by viewModel.activeProfRequest.collectAsStateWithLifecycle()
    val activeRequestsList by viewModel.activeRequests.collectAsStateWithLifecycle()
    val allRequests by viewModel.allRequests.collectAsStateWithLifecycle()

    if (profile == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = TechCyan)
        }
        return
    }

    var selectedTab by remember { mutableStateOf("CHAMADOS") } // "CHAMADOS", "PERFIL", "HISTORICO"

    // Edit profile states
    var nameInput by remember(profile!!.id) { mutableStateOf(profile!!.name) }
    var phoneInput by remember(profile!!.id) { mutableStateOf(profile!!.phone) }
    var emailInput by remember(profile!!.id) { mutableStateOf(profile!!.email) }
    var specialtyInput by remember(profile!!.id) { mutableStateOf(profile!!.specialty) }
    var experienceInput by remember(profile!!.id) { mutableStateOf(profile!!.experienceYears.toString()) }
    var descriptionInput by remember(profile!!.id) { mutableStateOf(profile!!.description) }
    var skillsInput by remember(profile!!.id) { mutableStateOf(profile!!.skills) }
    var workHistoryInput by remember(profile!!.id) { mutableStateOf(profile!!.workHistory) }
    var passwordInput by remember(profile!!.id) { mutableStateOf(profile!!.password) }

    var isSpecialtyDropdownExpanded by remember { mutableStateOf(false) }
    var showSavedFeedback by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Futuristic Segmented Tab Bar inside high-contrast row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(CardGrey, RoundedCornerShape(10.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val tabs = listOf(
                "CHAMADOS" to "Chamados",
                "PERFIL" to "Meu Perfil",
                "HISTORICO" to "Histórico"
            )
            tabs.forEach { (route, label) ->
                val isSelected = selectedTab == route
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) TechCyan else Color.Transparent)
                        .clickable { selectedTab = route }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) SlateDark else TextWhite
                    )
                }
            }
        }

        // Tab contents
        when (selectedTab) {
            "CHAMADOS" -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Online status Toggle Bar
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CardGrey),
                            border = BorderStroke(1.dp, BorderGrey),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = profile!!.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextWhite
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(if (profile!!.isOnline) SuccessGreen else TextGrey)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (profile!!.isOnline) "Activo & Online" else "Offline",
                                            fontSize = 12.sp,
                                            color = if (profile!!.isOnline) SuccessGreen else TextGrey,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                Switch(
                                    checked = profile!!.isOnline,
                                    onCheckedChange = { viewModel.toggleEngineerOnlineStatus(it) },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = SuccessGreen,
                                        checkedTrackColor = SuccessGreen.copy(alpha = 0.2f),
                                        uncheckedThumbColor = TextGrey,
                                        uncheckedTrackColor = BorderGrey
                                    ),
                                    modifier = Modifier.testTag("prof_online_switch")
                                )
                            }
                        }
                    }

                    // 1. Motivational Hero Banner for Technician (Aesthetic & Purpose-driven)
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CardGrey),
                            border = BorderStroke(1.5.dp, Brush.linearGradient(listOf(SuccessGreen, TechCyan))),
                            shape = RoundedCornerShape(18.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                                Image(
                                    painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.img_tech_professional),
                                    contentDescription = "Motivação Profissional",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                )
                                // Gradient for text legibility
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    SlateDark.copy(alpha = 0.98f)
                                                )
                                            )
                                        )
                                )
                                // Overlaid text structure
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(16.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(SuccessGreen.copy(alpha = 0.25f), RoundedCornerShape(4.dp))
                                            .border(1.dp, SuccessGreen, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "HERÓI DA TECNOLOGIA",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SuccessGreen,
                                            letterSpacing = 1.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "O seu talento resolve crises!",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Black,
                                        color = TextWhite
                                    )
                                    Text(
                                        text = "Cada atendimento garante a estabilidade de redes e energia na nossa Luanda.",
                                        fontSize = 11.sp,
                                        color = TextGrey,
                                        lineHeight = 14.sp
                                    )
                                }
                            }
                        }
                    }

                    // 2. Technician Elite Stats & Encouraging quotes (Motivational design)
                    item {
                        var totalKz by remember { mutableStateOf(35000) }
                        var pointsSolved by remember { mutableStateOf(12) }
                        
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CardGrey),
                            border = BorderStroke(1.dp, BorderGrey),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.TrendingUp,
                                            contentDescription = "Progresso",
                                            tint = SuccessGreen,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Painel de Rendimento Diário",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextWhite
                                        )
                                    }
                                    
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(TechCyan.copy(alpha = 0.15f))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "CAT: ELITE",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TechCyan
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Ganhos Estimados
                                    Column(
                                        modifier = Modifier
                                            .weight(1.2f)
                                            .background(SlateDark.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                            .border(1.dp, BorderGrey, RoundedCornerShape(10.dp))
                                            .padding(12.dp)
                                    ) {
                                        Text("Faturação Estimada", fontSize = 10.sp, color = TextGrey)
                                        Text(
                                            text = "${String.format("%,d", totalKz)} Kz",
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Black,
                                            color = SuccessGreen,
                                            modifier = Modifier.padding(top = 2.dp)
                                        )
                                    }

                                    // Atendimentos Concluídos
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .background(SlateDark.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                            .border(1.dp, BorderGrey, RoundedCornerShape(10.dp))
                                            .padding(12.dp)
                                    ) {
                                        Text("Casos Resolvidos", fontSize = 10.sp, color = TextGrey)
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(top = 2.dp)
                                        ) {
                                            Text(
                                                text = "$pointsSolved",
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Black,
                                                color = TechCyan
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = SuccessGreen,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // Feedback carrossel/citação inspiradora
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF141F32), RoundedCornerShape(8.dp))
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(PremiumGold.copy(alpha = 0.15f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.FormatQuote,
                                            contentDescription = "Quote",
                                            tint = PremiumGold,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Último feedback recebido:",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PremiumGold
                                        )
                                        Text(
                                            text = "\"Técnico excelente, consertou a nossa rede de escritórios em 20 minutos. Recomendo!\"",
                                            fontSize = 11.sp,
                                            color = TextWhite,
                                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                            lineHeight = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Active Job Panel (If matched and accepted)
                    if (activeReq != null) {
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = TechCyan.copy(alpha = 0.12f)),
                                border = BorderStroke(1.dp, TechCyan),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "TRABALHO ACTIVO EM CURSO",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TechCyan
                                        )

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(TechCyan)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = activeReq!!.status,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SlateDark
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Text(
                                        text = "Cliente: ${activeReq!!.clientName}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextWhite
                                    )
                                    Text(
                                        text = "Contacto: ${activeReq!!.clientPhone}",
                                        fontSize = 12.sp,
                                        color = TextGrey
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "Problema Relatado:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TechCyan
                                    )
                                    Text(
                                        text = activeReq!!.problemDescription,
                                        fontSize = 12.sp,
                                        color = TextWhite,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Button(
                                        onClick = { viewModel.completeRequest(activeReq!!.id) },
                                        colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen, contentColor = SlateDark),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .testTag("prof_complete_job_btn")
                                    ) {
                                        Text("Indicar Trabalho Finalizado", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    // Available Incoming Client Calls Section
                    item {
                        Text(
                            text = "Chamados Pendentes Compartilhados",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    val specialtyCalls = activeRequestsList.filter { 
                        it.category == profile!!.specialty && it.status == "PENDING"
                    }

                    if (specialtyCalls.isEmpty()) {
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = CardGrey),
                                border = BorderStroke(1.dp, BorderGrey),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Inbox,
                                        contentDescription = "Incoming empty",
                                        tint = TextGrey,
                                        modifier = Modifier.size(36.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Nenhum chamado pendente no momento",
                                        fontSize = 13.sp,
                                        color = TextWhite,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Mantenha o interruptor ativo para receber atribuições de emergência rapidamente.",
                                        fontSize = 11.sp,
                                        color = TextGrey,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        items(specialtyCalls) { req ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = CardGrey),
                                border = BorderStroke(1.dp, BorderGrey),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = req.clientName,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextWhite
                                        )

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(PremiumRed.copy(alpha = 0.15f))
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "URGENTE",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = PremiumRed
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = req.problemDescription,
                                        fontSize = 13.sp,
                                        color = TextWhite,
                                        lineHeight = 17.sp
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Button(
                                        onClick = { viewModel.acceptRequestByEngineer(req.id) },
                                        colors = ButtonDefaults.buttonColors(containerColor = TechCyan, contentColor = SlateDark),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .testTag("prof_accept_request_btn_${req.id}")
                                    ) {
                                        Text("Aceitar & Iniciar Corrida", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    item { Spacer(modifier = Modifier.height(24.dp)) }
                }
            }
            "PERFIL" -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Editar os Meus Dados",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Text(
                            text = "Mantenha a sua especialização e contactos profissionais atualizados. Os seus dados são visíveis aos clientes ao aceitar chamados.",
                            fontSize = 12.sp,
                            color = TextGrey,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    if (showSavedFeedback) {
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = SuccessGreen.copy(alpha = 0.15f)),
                                border = BorderStroke(1.dp, SuccessGreen),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Salvo", tint = SuccessGreen)
                                    Text("Informações atualizadas com sucesso e guardadas no banco de dados local!", color = TextWhite, fontSize = 12.sp)
                                }
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = { Text("Nome Completo") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedBorderColor = TechCyan,
                                unfocusedBorderColor = BorderGrey,
                                focusedContainerColor = CardGrey,
                                unfocusedContainerColor = CardGrey
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("prof_edit_name"),
                            singleLine = true
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = phoneInput,
                            onValueChange = { phoneInput = it },
                            label = { Text("Telemóvel") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedBorderColor = TechCyan,
                                unfocusedBorderColor = BorderGrey,
                                focusedContainerColor = CardGrey,
                                unfocusedContainerColor = CardGrey
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("prof_edit_phone"),
                            singleLine = true
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            label = { Text("E-mail") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedBorderColor = TechCyan,
                                unfocusedBorderColor = BorderGrey,
                                focusedContainerColor = CardGrey,
                                unfocusedContainerColor = CardGrey
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("prof_edit_email"),
                            singleLine = true
                        )
                    }

                    // Specialty Selector
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = specialtyInput,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Área de Especialização") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = TextWhite,
                                    unfocusedTextColor = TextWhite,
                                    focusedBorderColor = TechCyan,
                                    unfocusedBorderColor = BorderGrey,
                                    focusedContainerColor = CardGrey,
                                    unfocusedContainerColor = CardGrey
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { isSpecialtyDropdownExpanded = !isSpecialtyDropdownExpanded }
                                    .testTag("prof_edit_specialty_trigger"),
                                trailingIcon = {
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                                }
                            )

                            DropdownMenu(
                                expanded = isSpecialtyDropdownExpanded,
                                onDismissRequest = { isSpecialtyDropdownExpanded = false },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .background(CardGrey)
                            ) {
                                listOf("Electricista", "Informático", "Técnico de TI").forEach { spec ->
                                    DropdownMenuItem(
                                        text = { Text(spec, color = TextWhite) },
                                        onClick = {
                                            specialtyInput = spec
                                            isSpecialtyDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = experienceInput,
                            onValueChange = { experienceInput = it },
                            label = { Text("Anos de Experiência") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedBorderColor = TechCyan,
                                unfocusedBorderColor = BorderGrey,
                                focusedContainerColor = CardGrey,
                                unfocusedContainerColor = CardGrey
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("prof_edit_experience"),
                            singleLine = true
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = skillsInput,
                            onValueChange = { skillsInput = it },
                            label = { Text("Principais Habilidades Técnicas") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedBorderColor = TechCyan,
                                unfocusedBorderColor = BorderGrey,
                                focusedContainerColor = CardGrey,
                                unfocusedContainerColor = CardGrey
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("prof_edit_skills"),
                            singleLine = true
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = workHistoryInput,
                            onValueChange = { workHistoryInput = it },
                            label = { Text("Empresas / Clientes onde já atuou") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedBorderColor = TechCyan,
                                unfocusedBorderColor = BorderGrey,
                                focusedContainerColor = CardGrey,
                                unfocusedContainerColor = CardGrey
                            ),
                            modifier = Modifier.fillMaxWidth().height(100.dp).testTag("prof_edit_work_history"),
                            maxLines = 4
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = descriptionInput,
                            onValueChange = { descriptionInput = it },
                            label = { Text("Resumo de Qualificações de Biografia") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedBorderColor = TechCyan,
                                unfocusedBorderColor = BorderGrey,
                                focusedContainerColor = CardGrey,
                                unfocusedContainerColor = CardGrey
                            ),
                            modifier = Modifier.fillMaxWidth().height(80.dp).testTag("prof_edit_desc"),
                            maxLines = 3
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = passwordInput,
                            onValueChange = { passwordInput = it },
                            label = { Text("Senha de Acesso") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedBorderColor = TechCyan,
                                unfocusedBorderColor = BorderGrey,
                                focusedContainerColor = CardGrey,
                                unfocusedContainerColor = CardGrey
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("prof_edit_password"),
                            singleLine = true
                        )
                    }

                    item {
                        Button(
                            onClick = {
                                viewModel.updateCurrentEngineerProfile(
                                    name = nameInput,
                                    phone = phoneInput,
                                    email = emailInput,
                                    specialty = specialtyInput,
                                    experience = experienceInput,
                                    description = descriptionInput,
                                    skills = skillsInput,
                                    workHistory = workHistoryInput,
                                    passwordEntered = passwordInput
                                )
                                showSavedFeedback = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = TechCyan, contentColor = SlateDark),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("prof_edit_save_btn"),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Guardar Alterações", fontWeight = FontWeight.Bold)
                        }
                    }

                    item { Spacer(modifier = Modifier.height(24.dp)) }
                }
            }
            "HISTORICO" -> {
                val completedRequests = allRequests.filter { 
                    it.matchedEngineerId == profile!!.id && it.status == "COMPLETED"
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Histórico de Assistências",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Text(
                            text = "Lista de atendimentos concluídos com sucesso em Luanda com as avaliações dos clientes.",
                            fontSize = 12.sp,
                            color = TextGrey,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    if (completedRequests.isEmpty()) {
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = CardGrey),
                                border = BorderStroke(1.dp, BorderGrey),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.History,
                                        contentDescription = "Empty History",
                                        tint = TextGrey,
                                        modifier = Modifier.size(36.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Nenhum histórico registado ainda",
                                        fontSize = 13.sp,
                                        color = TextWhite,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "As suas assistências finalizadas com sucesso aparecerão arquivadas neste painel auditivo.",
                                        fontSize = 11.sp,
                                        color = TextGrey,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        items(completedRequests) { req ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = CardGrey),
                                border = BorderStroke(1.dp, BorderGrey),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = req.clientName,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextWhite
                                        )

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(SuccessGreen.copy(alpha = 0.15f))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "CONCLUÍDO",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SuccessGreen
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Contacto: ${req.clientPhone}",
                                        fontSize = 11.sp,
                                        color = TextGrey
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Problema: ${req.problemDescription}",
                                        fontSize = 12.sp,
                                        color = TextWhite,
                                        lineHeight = 15.sp
                                    )

                                    if (req.rating != null || req.feedback != null) {
                                        HorizontalDivider(
                                            modifier = Modifier.padding(vertical = 10.dp),
                                            color = BorderGrey,
                                            thickness = 0.5.dp
                                        )

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // Star rating row
                                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                                val ratingCount = req.rating ?: 5
                                                for (star in 1..5) {
                                                    Icon(
                                                        imageVector = if (star <= ratingCount) Icons.Default.Star else Icons.Default.StarOutline,
                                                        contentDescription = "Avaliação",
                                                        tint = PremiumGold,
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                }
                                            }

                                            Text(
                                                text = "Avaliação do Cliente",
                                                fontSize = 10.sp,
                                                color = TextGrey,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }

                                        if (!req.feedback.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = "\"${req.feedback}\"",
                                                fontSize = 11.sp,
                                                color = SuccessGreen,
                                                lineHeight = 14.sp,
                                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item { Spacer(modifier = Modifier.height(24.dp)) }
                }
            }
        }
    }
}

// --- SCREEN: PROFESSIONAL BADGE/CREDENTIALS ---
@Composable
fun ProfessionalCredentialsScreen(viewModel: BandaViewModel) {
    val profile by viewModel.currentEngineerProfile.collectAsStateWithLifecycle()

    if (profile == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Official Credentials Security Certificate Badge
        Card(
            colors = CardDefaults.cardColors(containerColor = CardGrey),
            border = BorderStroke(2.dp, TechCyan),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Stamp
                Text(
                    text = "REPÚBLICA DE ANGOLA",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextGrey,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "CREDÊNCIAL OFICIAL DE ENGENHARIA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TechCyan,
                    letterSpacing = 0.5.sp
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = TechCyan.copy(alpha = 0.3f))

                // Avatar Icon Placeholder
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(1.dp, TechCyan, CircleShape)
                        .background(SlateDark),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Engineering,
                        contentDescription = "Badge Profile",
                        tint = TechCyan,
                        modifier = Modifier.size(44.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = profile!!.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = TextWhite
                )

                Text(
                    text = profile!!.specialty.uppercase(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = PremiumGold,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Official Serial Number and Rating
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SlateDark)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SERIAL Nº: ",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Light,
                        color = TextGrey
                    )
                    Text(
                        text = profile!!.certId,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = TechCyan
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Specific credential skills listed
                Text(
                    text = "Competências Avançadas:",
                    fontSize = 11.sp,
                    color = TextGrey,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = profile!!.skills,
                    fontSize = 12.sp,
                    color = TextWhite,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                )

                // QR CODE MOCK VIA COMPOSE CANVAS VECTOR SECTOR
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(TextWhite)
                        .padding(8.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Drawing mock QR squares safely and elegantly
                        val qSize = size.width / 5f
                        // Corner Anchors
                        drawRect(Color.Black, Offset(0f, 0f), size = androidx.compose.ui.geometry.Size(qSize * 1.5f, qSize * 1.5f))
                        drawRect(Color.Black, Offset(size.width - qSize * 1.5f, 0f), size = androidx.compose.ui.geometry.Size(qSize * 1.5f, qSize * 1.5f))
                        drawRect(Color.Black, Offset(0f, size.height - qSize * 1.5f), size = androidx.compose.ui.geometry.Size(qSize * 1.5f, qSize * 1.5f))

                        // Random bits
                        drawRect(Color.Black, Offset(qSize * 2f, qSize * 2f), size = androidx.compose.ui.geometry.Size(qSize, qSize))
                        drawRect(Color.Black, Offset(qSize * 3f, qSize * 1.5f), size = androidx.compose.ui.geometry.Size(qSize, qSize))
                        drawRect(Color.Black, Offset(qSize * 1.5f, qSize * 3f), size = androidx.compose.ui.geometry.Size(qSize, qSize))
                        drawRect(Color.Black, Offset(size.width - qSize, size.height - qSize), size = androidx.compose.ui.geometry.Size(qSize, qSize))
                        drawRect(Color.Black, Offset(qSize * 3f, size.height - qSize * 2f), size = androidx.compose.ui.geometry.Size(qSize, qSize))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Certificate safety check mark stamp
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Verified, contentDescription = "Verified Seal", tint = SuccessGreen, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "ASSINATURA DIGITAL VERIFICADA EM BLOCKCHAIN", fontSize = 9.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                }
            }
        }

        Button(
            onClick = { viewModel.navigateToProfessionalScreen("DASHBOARD") },
            colors = ButtonDefaults.buttonColors(containerColor = TechCyan, contentColor = SlateDark),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .testTag("prof_credentials_back_btn")
        ) {
            Text("Voltar ao Painel", fontWeight = FontWeight.Bold)
        }
    }
}
