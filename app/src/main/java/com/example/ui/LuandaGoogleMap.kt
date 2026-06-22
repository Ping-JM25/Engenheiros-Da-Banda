package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.LayoutDirection
import com.example.data.Engineer
import com.example.ui.theme.*
import java.util.Locale
import kotlin.math.*

// Luanda Geography Configurations & Geo-helpers
object LuandaGeo {
    const val CENTER_LAT = -8.8350
    const val CENTER_LNG = 13.2350
    const val MIN_LAT = -8.8800
    const val MAX_LAT = -8.7800
    const val MIN_LNG = 13.1800
    const val MAX_LNG = 13.2800

    data class LuandaStreet(
        val name: String,
        val isBeco: Boolean,
        val points: List<Pair<Double, Double>>, // List of Lat, Lng
        val description: String = ""
    )

    data class LuandaDistrict(
        val name: String,
        val lat: Double,
        val lng: Double,
        val populationDensity: String
    )

    // Avenues, Streets, and famous Alleys/Becos inside central Luanda
    val paths = listOf(
        // Avenida 4 de Fevereiro (Marginal) - Beautiful seaside avenue
        LuandaStreet(
            name = "Avenida 4 de Fevereiro (Marginal de Luanda)",
            isBeco = false,
            points = listOf(
                Pair(-8.8020, 13.2185),
                Pair(-8.8070, 13.2210),
                Pair(-8.8120, 13.2250),
                Pair(-8.8160, 13.2320)
            ),
            description = "Principal marginal costeira à beira da Baía de Luanda."
        ),
        // Avenida Comandante Valódia
        LuandaStreet(
            name = "Avenida Comandante Valódia",
            isBeco = false,
            points = listOf(
                Pair(-8.8100, 13.2420),
                Pair(-8.8170, 13.2370),
                Pair(-8.8250, 13.2320)
            ),
            description = "Eixo vital de comércio e trânsito da Ingombota e Sambizanga."
        ),
        // Avenida Lenine
        LuandaStreet(
            name = "Avenida Vladimir Lenine",
            isBeco = false,
            points = listOf(
                Pair(-8.8220, 13.2320),
                Pair(-8.8300, 13.2330),
                Pair(-8.8380, 13.2340)
            ),
            description = "Avenida central que liga o Maculusso à Maianga."
        ),
        // Avenida Deolinda Rodrigues (Estrada de Catete)
        LuandaStreet(
            name = "Avenida Deolinda Rodrigues (Estrada de Catete)",
            isBeco = false,
            points = listOf(
                Pair(-8.8350, 13.2450),
                Pair(-8.8400, 13.2600),
                Pair(-8.8480, 13.2800)
            ),
            description = "Principal via de entrada e saída leste de Luanda."
        ),
        // Avenida Marien Ngouabi
        LuandaStreet(
            name = "Avenida Marien Ngouabi",
            isBeco = false,
            points = listOf(
                Pair(-8.8380, 13.2340),
                Pair(-8.8350, 13.2450),
                Pair(-8.8320, 13.2550)
            ),
            description = "Ligação crucial entre a Maianga e a Vila Alice."
        ),
        // Rua Rainha Ginga (Mutamba)
        LuandaStreet(
            name = "Rua Rainha Ginga",
            isBeco = false,
            points = listOf(
                Pair(-8.8120, 13.2210),
                Pair(-8.8130, 13.2250),
                Pair(-8.8140, 13.2280)
            ),
            description = "Centro financeiro e governamental da Mutamba."
        ),
        // Rua Amílcar Cabral (Maianga)
        LuandaStreet(
            name = "Rua Amílcar Cabral",
            isBeco = false,
            points = listOf(
                Pair(-8.8280, 13.2300),
                Pair(-8.8330, 13.2295),
                Pair(-8.8380, 13.2290)
            ),
            description = "Área residencial e consular arborizada na Maianga."
        ),
        // BECOS EXPLICITAMENTE ADICIONADOS PARA A DEMONSTRAÇÃO
        LuandaStreet(
            name = "Beco da Poeira (Sambizanga)",
            isBeco = true,
            points = listOf(
                Pair(-8.8030, 13.2470),
                Pair(-8.8040, 13.2490),
                Pair(-8.8055, 13.2480)
            ),
            description = "Acesso tradicional pedestre no coração do Sambizanga."
        ),
        LuandaStreet(
            name = "Beco do Sambizanga",
            isBeco = true,
            points = listOf(
                Pair(-8.8050, 13.2510),
                Pair(-8.8070, 13.2535),
                Pair(-8.8090, 13.2520)
            ),
            description = "Rua estreita de alta densidade no Sambizanga."
        ),
        LuandaStreet(
            name = "Beco da Lixeira (Rangel)",
            isBeco = true,
            points = listOf(
                Pair(-8.8160, 13.2600),
                Pair(-8.8180, 13.2625),
                Pair(-8.8200, 13.2610)
            ),
            description = "Zonas de becos pedestres de acesso rápido no Rangel."
        ),
        LuandaStreet(
            name = "Beco da Luta (Maianga)",
            isBeco = true,
            points = listOf(
                Pair(-8.8340, 13.2220),
                Pair(-8.8360, 13.2235),
                Pair(-8.8380, 13.2225)
            ),
            description = "Atalho tradicional entre a subida de São José e a Maianga."
        ),
        LuandaStreet(
            name = "Beco do Rosário (Samba)",
            isBeco = true,
            points = listOf(
                Pair(-8.8540, 13.2120),
                Pair(-8.8560, 13.2135),
                Pair(-8.8580, 13.2125)
            ),
            description = "Acesso de marés tradicional no distrito costeiro da Samba."
        ),
        LuandaStreet(
            name = "Beco do Chaba (Samba)",
            isBeco = true,
            points = listOf(
                Pair(-8.8580, 13.2100),
                Pair(-8.8600, 13.2115),
                Pair(-8.8620, 13.2095)
            ),
            description = "Beco estreito comunitário próximo ao mercado da Samba."
        )
    )

    val districts = listOf(
        LuandaDistrict("Mutamba", -8.8150, 13.2230, "Média"),
        LuandaDistrict("Ingombota", -8.8100, 13.2350, "Média-Alta"),
        LuandaDistrict("Maculusso", -8.8250, 13.2300, "Média"),
        LuandaDistrict("Maianga", -8.8380, 13.2320, "Alta"),
        LuandaDistrict("Alvalade", -8.8450, 13.2390, "Média"),
        LuandaDistrict("Samba", -8.8600, 13.2100, "Alta"),
        LuandaDistrict("Sambizanga", -8.8020, 13.2500, "Muito Alta"),
        LuandaDistrict("Rangel", -8.8180, 13.2630, "Muito Alta"),
        LuandaDistrict("Miramar", -8.8050, 13.2380, "Baixa")
    )

    // Convert Geo-coordinates (Lat, Lng) to 2D Screen Space (X, Y)
    fun getOffset(
        lat: Double,
        lng: Double,
        centerLat: Double,
        centerLng: Double,
        zoom: Float,
        w: Float,
        h: Float
    ): Offset {
        val baseScale = 4500f * zoom
        // Delta longitude maps to dx
        val dx = (lng - centerLng).toFloat() * baseScale + (w / 2f)
        // Delta latitude maps to dy (negative sign because y goes downwards in Canvas)
        val dy = (centerLat - lat).toFloat() * baseScale + (h / 2f)
        return Offset(dx, dy)
    }

    // Convert 2D Screen Space (X, Y) back to Geo-coordinates (Lat, Lng)
    fun getGeo(
        offset: Offset,
        centerLat: Double,
        centerLng: Double,
        zoom: Float,
        w: Float,
        h: Float
    ): Pair<Double, Double> {
        val baseScale = 4500f * zoom
        val lng = (offset.x - (w / 2f)) / baseScale + centerLng
        val lat = centerLat - (offset.y - (h / 2f)) / baseScale
        return Pair(lat, lng)
    }
}

@Composable
fun LuandaGoogleMap(
    modifier: Modifier = Modifier,
    engineers: List<Engineer>,
    userLat: Double = -8.8350, // Default User location (Maianga)
    userLng: Double = 13.2350,
    trackingEngineerId: Int? = null, // Set during service tracking
    simulatedDistance: Double? = null, // Set during service tracking
    onEngineerSelected: ((Engineer) -> Unit)? = null
) {
    var centerLat by remember { mutableStateOf(userLat) }
    var centerLng by remember { mutableStateOf(userLng) }
    var zoomScale by remember { mutableStateOf(1.2f) } // 1.0 to 4.0
    
    // Search query engine
    var searchQuery by remember { mutableStateOf("") }
    var searchedLocationName by remember { mutableStateOf<String?>(null) }
    var searchBeaconOffset by remember { mutableStateOf<Offset?>(null) }
    var beaconTimer by remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current
    
    // Map mode (Standard Dark vs Satellite hybrid)
    var mapModeExtended by remember { mutableStateOf(false) } // false = obsidian cyber / true = hybrid
    
    // Currently tapped engineer details
    var selectedEngineer by remember { mutableStateOf<Engineer?>(null) }

    LaunchedEffect(trackingEngineerId, simulatedDistance) {
        if (trackingEngineerId != null && simulatedDistance != null) {
            val targetEng = engineers.find { it.id == trackingEngineerId }
            if (targetEng != null) {
                val progress = ((2.4 - simulatedDistance) / 2.4).coerceIn(0.0, 1.0)
                val currentEngLat = targetEng.latitude + (userLat - targetEng.latitude) * progress
                val currentEngLng = targetEng.longitude + (userLng - targetEng.longitude) * progress
                
                centerLat = (userLat + currentEngLat) / 2.0
                centerLng = (userLng + currentEngLng) / 2.0
                zoomScale = 1.3f
            }
        }
    }

    LaunchedEffect(searchBeaconOffset) {
        if (searchBeaconOffset != null) {
            beaconTimer = 10
            while (beaconTimer > 0) {
                kotlinx.coroutines.delay(200)
                beaconTimer--
            }
            searchBeaconOffset = null
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SlateDark)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val baseScale = 4500f * zoomScale
                        val dLng = -dragAmount.x / baseScale
                        val dLat = dragAmount.y / baseScale
                        
                        centerLat = (centerLat + dLat).coerceIn(LuandaGeo.MIN_LAT, LuandaGeo.MAX_LAT)
                        centerLng = (centerLng + dLng).coerceIn(LuandaGeo.MIN_LNG, LuandaGeo.MAX_LNG)
                    }
                }
        ) {
            val canvasW = constraints.maxWidth.toFloat()
            val canvasH = constraints.maxHeight.toFloat()
            val density = LocalDensity.current.density

            if (searchQuery.isNotBlank() && searchBeaconOffset == null && beaconTimer > 5) {
                val matched = LuandaGeo.paths.find { it.name.contains(searchQuery, ignoreCase = true) }
                if (matched != null && matched.points.isNotEmpty()) {
                    searchBeaconOffset = LuandaGeo.getOffset(
                        matched.points[0].first, matched.points[0].second,
                        centerLat, centerLng, zoomScale, canvasW, canvasH
                    )
                }
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                val coastPath = Path()
                val coastPoints = listOf(
                    Pair(-8.7850, 13.2010),
                    Pair(-8.7990, 13.2150),
                    Pair(-8.8050, 13.2180),
                    Pair(-8.8100, 13.2230),
                    Pair(-8.8150, 13.2205),
                    Pair(-8.8250, 13.2080),
                    Pair(-8.8450, 13.1950),
                    Pair(-8.8750, 13.1750)
                )

                val screenCoastOffsets = coastPoints.map {
                    LuandaGeo.getOffset(it.first, it.second, centerLat, centerLng, zoomScale, canvasW, canvasH)
                }

                coastPath.moveTo(0f, 0f)
                coastPath.lineTo(canvasW, 0f)
                for (i in screenCoastOffsets.indices) {
                    if (i == 0) coastPath.moveTo(screenCoastOffsets[i].x, screenCoastOffsets[i].y)
                    else coastPath.lineTo(screenCoastOffsets[i].x, screenCoastOffsets[i].y)
                }
                coastPath.lineTo(0f, canvasH)
                coastPath.close()

                val oceanColor = if (mapModeExtended) Color(0xFF0F2D3A) else Color(0xFF0D1C2A)
                drawPath(
                    path = coastPath,
                    color = oceanColor
                )
                
                val coastLinePath = Path()
                for (i in screenCoastOffsets.indices) {
                    if (i == 0) coastLinePath.moveTo(screenCoastOffsets[i].x, screenCoastOffsets[i].y)
                    else coastLinePath.lineTo(screenCoastOffsets[i].x, screenCoastOffsets[i].y)
                }
                drawPath(
                    path = coastLinePath,
                    color = TechCyan.copy(alpha = 0.6f),
                    style = Stroke(width = 3f)
                )

                val gridColor = Color(0xFF1E2A3A).copy(alpha = 0.4f)
                val step = 100f
                for (x in 0..canvasW.toInt() step step.toInt()) {
                    drawLine(gridColor, Offset(x.toFloat(), 0f), Offset(x.toFloat(), canvasH), 1f)
                }
                for (y in 0..canvasH.toInt() step step.toInt()) {
                    drawLine(gridColor, Offset(0f, y.toFloat()), Offset(canvasW, y.toFloat()), 1f)
                }

                LuandaGeo.districts.forEach { dist ->
                    val offset = LuandaGeo.getOffset(dist.lat, dist.lng, centerLat, centerLng, zoomScale, canvasW, canvasH)
                    if (offset.x in 0f..canvasW && offset.y in 0f..canvasH) {
                        drawCircle(CardGrey.copy(alpha = 0.5f), radius = 30f, center = offset)
                        drawCircle(TechCyan.copy(alpha = 0.15f), radius = 20f, center = offset)
                    }
                }

                LuandaGeo.paths.forEach { road ->
                    if (road.points.size > 1) {
                        val roadPath = Path()
                        val screenPoints = road.points.map {
                            LuandaGeo.getOffset(it.first, it.second, centerLat, centerLng, zoomScale, canvasW, canvasH)
                        }
                        
                        roadPath.moveTo(screenPoints[0].x, screenPoints[0].y)
                        for (i in 1 until screenPoints.size) {
                            roadPath.lineTo(screenPoints[i].x, screenPoints[i].y)
                        }

                        if (road.isBeco) {
                            drawPath(
                                path = roadPath,
                                color = PremiumGold,
                                style = Stroke(
                                    width = 4.5f * zoomScale,
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f),
                                    cap = StrokeCap.Round
                                )
                            )
                            drawPath(
                                path = roadPath,
                                color = PremiumGold.copy(alpha = 0.25f),
                                style = Stroke(
                                    width = 12f * zoomScale,
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f),
                                    cap = StrokeCap.Round
                                )
                            )
                        } else {
                            val roadColor = if (mapModeExtended) Color(0xFF384656).copy(alpha = 0.8f) else Color(0xFF202B3C).copy(alpha = 0.9f)
                            drawPath(
                                path = roadPath,
                                color = roadColor,
                                style = Stroke(width = 8f * zoomScale, cap = StrokeCap.Round, join = StrokeJoin.Round)
                            )
                            drawPath(
                                path = roadPath,
                                color = Color(0xFFFFFFFF).copy(alpha = 0.75f),
                                style = Stroke(width = 2.5f * zoomScale, cap = StrokeCap.Round, join = StrokeJoin.Round)
                            )
                        }
                    }
                }

                searchBeaconOffset?.let { beacon ->
                    drawCircle(
                        color = SuccessGreen.copy(alpha = (beaconTimer / 10f)),
                        radius = (10 - beaconTimer) * 12f + 10f,
                        center = beacon,
                        style = Stroke(4f)
                    )
                    drawCircle(
                        color = SuccessGreen,
                        radius = 8f,
                        center = beacon
                    )
                }

                val clientOffset = LuandaGeo.getOffset(userLat, userLng, centerLat, centerLng, zoomScale, canvasW, canvasH)
                if (clientOffset.x in 0f..canvasW && clientOffset.y in 0f..canvasH) {
                    drawCircle(
                        color = TechCyan.copy(alpha = 0.2f),
                        radius = 35f,
                        center = clientOffset
                    )
                    drawCircle(
                        color = TechCyan,
                        radius = 12f,
                        center = clientOffset
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 5f,
                        center = clientOffset
                    )
                }

                if (trackingEngineerId != null && simulatedDistance != null) {
                    val targetEng = engineers.find { it.id == trackingEngineerId }
                    if (targetEng != null) {
                        val progress = ((2.4 - simulatedDistance) / 2.4).coerceIn(0.0, 1.0)
                        val engCurrentLat = targetEng.latitude + (userLat - targetEng.latitude) * progress
                        val engCurrentLng = targetEng.longitude + (userLng - targetEng.longitude) * progress
                        
                        val engCurrentOffset = LuandaGeo.getOffset(engCurrentLat, engCurrentLng, centerLat, centerLng, zoomScale, canvasW, canvasH)
                        
                        drawLine(
                            color = TechCyan,
                            start = clientOffset,
                            end = engCurrentOffset,
                            strokeWidth = 6f,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color.White,
                            start = clientOffset,
                            end = engCurrentOffset,
                            strokeWidth = 2f,
                            cap = StrokeCap.Round
                        )
                    }
                }
            }

            LuandaGeo.districts.forEach { dist ->
                val offset = LuandaGeo.getOffset(dist.lat, dist.lng, centerLat, centerLng, zoomScale, canvasW, canvasH)
                if (offset.x in 40f..(canvasW - 40f) && offset.y in 40f..(canvasH - 40f)) {
                    Box(
                        modifier = Modifier
                            .offset(
                                x = (offset.x / density).dp - 45.dp,
                                y = (offset.y / density).dp - 10.dp
                            )
                    ) {
                        Text(
                            text = dist.name.uppercase(),
                            color = TextWhite.copy(alpha = 0.85f),
                            fontSize = (11f * zoomScale).sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(90.dp)
                        )
                    }
                }
            }

            LuandaGeo.paths.forEach { road ->
                if (road.points.isNotEmpty() && zoomScale > 1.1f) {
                    val midIdx = road.points.size / 2
                    val pt = road.points[midIdx]
                    val offset = LuandaGeo.getOffset(pt.first, pt.second, centerLat, centerLng, zoomScale, canvasW, canvasH)
                    if (offset.x in 80f..(canvasW - 80f) && offset.y in 40f..(canvasH - 40f)) {
                        Box(
                            modifier = Modifier
                                .offset(
                                    x = (offset.x / density).dp - 50.dp,
                                    y = (offset.y / density).dp - 8.dp
                                )
                                .clip(RoundedCornerShape(4.dp))
                                .background(SlateDark.copy(alpha = 0.75f))
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (road.isBeco) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "Beco",
                                        tint = PremiumGold,
                                        modifier = Modifier.size(10.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                }
                                Text(
                                    text = road.name,
                                    color = if (road.isBeco) PremiumGold else TextGrey,
                                    fontSize = (9f * zoomScale).sp,
                                    fontWeight = if (road.isBeco) FontWeight.Bold else FontWeight.Medium,
                                    maxLines = 1,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.widthIn(max = 100.dp)
                                )
                            }
                        }
                    }
                }
            }

            engineers.forEach { eng ->
                val isMatched = eng.id == trackingEngineerId
                val engLat = if (isMatched && simulatedDistance != null) {
                    val progress = ((2.4 - simulatedDistance) / 2.4).coerceIn(0.0, 1.0)
                    eng.latitude + (userLat - eng.latitude) * progress
                } else {
                    eng.latitude
                }
                val engLng = if (isMatched && simulatedDistance != null) {
                    val progress = ((2.4 - simulatedDistance) / 2.4).coerceIn(0.0, 1.0)
                    eng.longitude + (userLng - eng.longitude) * progress
                } else {
                    eng.longitude
                }

                val engOffset = LuandaGeo.getOffset(engLat, engLng, centerLat, centerLng, zoomScale, canvasW, canvasH)
                if (engOffset.x in 20f..(canvasW - 20f) && engOffset.y in 20f..(canvasH - 20f)) {
                    val mX = (engOffset.x / density).dp - 24.dp
                    val mY = (engOffset.y / density).dp - 48.dp

                    Box(
                        modifier = Modifier
                            .offset(x = mX, y = mY)
                            .size(48.dp)
                            .clickable {
                                selectedEngineer = eng
                            },
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(CardGrey)
                                    .border(
                                        2.dp,
                                        if (isMatched) SuccessGreen else if (eng.specialty == "Informático") TechCyan else PremiumRed,
                                        CircleShape
                                    )
                                    .padding(2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (eng.specialty == "Informático") Icons.Default.Computer else Icons.Default.Badge,
                                    contentDescription = eng.name,
                                    tint = if (eng.specialty == "Informático") TechCyan else PremiumRed,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(
                                        color = if (isMatched) SuccessGreen else if (eng.specialty == "Informático") TechCyan else PremiumRed,
                                        shape = GenericShape { size: Size, _: LayoutDirection ->
                                            this.moveTo(0f, 0f)
                                            this.lineTo(size.width, 0f)
                                            this.lineTo(size.width / 2f, size.height)
                                            this.close()
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }

        // 1. Search Bar Overlay
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp)
                .align(Alignment.TopCenter),
            colors = CardDefaults.cardColors(containerColor = CardGrey.copy(alpha = 0.95f)),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, BorderGrey)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon",
                    tint = PremiumGold,
                    modifier = Modifier.size(22.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            "Pesquise Avenidas, Ruas ou Becos de Luanda...",
                            fontSize = 12.sp,
                            color = TextGrey.copy(alpha = 0.6f)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        cursorColor = PremiumGold
                    ),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            focusManager.clearFocus()
                            val found = LuandaGeo.paths.find {
                                it.name.contains(searchQuery, ignoreCase = true)
                            }
                            if (found != null && found.points.isNotEmpty()) {
                                centerLat = found.points[0].first
                                centerLng = found.points[0].second
                                zoomScale = 1.9f
                                searchedLocationName = found.name
                            } else {
                                val foundDist = LuandaGeo.districts.find {
                                    it.name.contains(searchQuery, ignoreCase = true)
                                }
                                if (foundDist != null) {
                                    centerLat = foundDist.lat
                                    centerLng = foundDist.lng
                                    zoomScale = 1.7f
                                    searchedLocationName = foundDist.name
                                } else {
                                    searchedLocationName = "Não encontrado em Luanda"
                                }
                            }
                        }
                    )
                )

                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        searchedLocationName = null
                        searchBeaconOffset = null
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search",
                            tint = TextGrey,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        searchedLocationName?.let { locName ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xBB000000)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 80.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (locName.contains("Não")) Icons.Default.ErrorOutline else Icons.Default.Place,
                        contentDescription = "Location status",
                        tint = if (locName.contains("Não")) PremiumRed else SuccessGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = locName,
                        color = TextWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 2. Zoom (+) (-) Buttons (Right Side)
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconButton(
                onClick = { zoomScale = (zoomScale + 0.3f).coerceAtMost(3.5f) },
                modifier = Modifier
                    .size(46.dp)
                    .background(CardGrey, shape = CircleShape)
                    .border(BorderStroke(1.dp, BorderGrey), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Zoom In",
                    tint = PremiumGold
                )
            }

            IconButton(
                onClick = { zoomScale = (zoomScale - 0.3f).coerceAtLeast(0.7f) },
                modifier = Modifier
                    .size(46.dp)
                    .background(CardGrey, shape = CircleShape)
                    .border(BorderStroke(1.dp, BorderGrey), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Zoom Out",
                    tint = PremiumGold
                )
            }

            IconButton(
                onClick = {
                    centerLat = userLat
                    centerLng = userLng
                    zoomScale = 1.3f
                },
                modifier = Modifier
                    .size(46.dp)
                    .background(CardGrey, shape = CircleShape)
                    .border(BorderStroke(1.dp, BorderGrey), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "Minha Localização",
                    tint = TechCyan
                )
            }

            IconButton(
                onClick = { mapModeExtended = !mapModeExtended },
                modifier = Modifier
                    .size(46.dp)
                    .background(CardGrey, shape = CircleShape)
                    .border(BorderStroke(1.dp, BorderGrey), shape = CircleShape)
            ) {
                Icon(
                    imageVector = if (mapModeExtended) Icons.Default.Map else Icons.Default.Layers,
                    contentDescription = "Alternar satélite",
                    tint = PremiumGold
                )
            }
        }

        // 3. Coordinate footer
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 14.dp, bottom = 14.dp)
                .background(CardGrey.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                .border(BorderStroke(0.5.dp, BorderGrey), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Explore,
                contentDescription = "Compass",
                tint = TechCyan,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            val dLatDeg = floor(abs(centerLat))
            val dLatMin = floor((abs(centerLat) - dLatDeg) * 60.0)
            val dLngDeg = floor(abs(centerLng))
            val dLngMin = floor((abs(centerLng) - dLngDeg) * 60.0)
            Text(
                text = String.format(
                    Locale.US,
                    "Luanda - %02d°%02d'S, %02d°%02d'E • Zoom: x%.1f",
                    dLatDeg.toInt(), dLatMin.toInt(), dLngDeg.toInt(), dLngMin.toInt(), zoomScale
                ),
                color = TextGrey,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // 4. Selected Technician Profile sheet
        AnimatedVisibility(
            visible = selectedEngineer != null,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 14.dp, vertical = 20.dp)
        ) {
            selectedEngineer?.let { eng ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardGrey),
                    border = BorderStroke(1.8.dp, if (eng.specialty == "Informático") TechCyan else PremiumRed)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (eng.specialty == "Informático") TechCyan.copy(alpha = 0.15f) else PremiumRed.copy(
                                            alpha = 0.15f
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (eng.specialty == "Informático") Icons.Default.Computer else Icons.Default.Badge,
                                    contentDescription = "Eng info logo",
                                    tint = if (eng.specialty == "Informático") TechCyan else PremiumRed,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = eng.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextWhite
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = eng.specialty.uppercase(),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (eng.specialty == "Informático") TechCyan else PremiumRed
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating star",
                                        tint = PremiumGold,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = " ${eng.rating}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextWhite
                                    )
                                }
                            }
                            IconButton(onClick = { selectedEngineer = null }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close sheet",
                                    tint = TextGrey,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "Certificação: ${eng.certId}",
                                fontSize = 11.sp,
                                color = TextGrey,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Experiência: ${eng.experienceYears} anos",
                                fontSize = 11.sp,
                                color = TextGrey,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Divider(color = BorderGrey, thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))

                        Text(
                            text = "COMPETÊNCIAS ESPECÍFICAS:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = PremiumGold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = eng.skills,
                            fontSize = 12.sp,
                            color = TextWhite,
                            modifier = Modifier.padding(top = 2.dp),
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {
                                onEngineerSelected?.invoke(eng)
                                selectedEngineer = null
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (eng.specialty == "Informático") TechCyan else PremiumRed
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.DirectionsRun,
                                    contentDescription = "Run",
                                    tint = SlateDark,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "SOLICITAR SUPORTE COM ${eng.name.split(" ")[0].uppercase()}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = SlateDark
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
