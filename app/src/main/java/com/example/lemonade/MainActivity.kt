package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                ChocolateProcessApp()
            }
        }
    }
}

/**
 * Data class untuk merepresentasikan setiap tahapan dalam proses pembuatan cokelat.
 */
data class ChocolateStep(
    @param:DrawableRes val imageRes: Int,
    val title: String,
    val description: String,
    val stepNumber: Int,
)

/**
 * Daftar 5 tahapan berurutan pembuatan cokelat.
 */
val chocolateSteps = listOf(
    ChocolateStep(
        imageRes = R.drawable.pohon_kakao,
        title = "1. Pohon Kakao",
        description = "Proses dimulai dari pohon kakao yang tumbuh di iklim tropis dan menghasilkan buah kakao.",
        stepNumber = 1,
    ),
    ChocolateStep(
        imageRes = R.drawable.kakao,
        title = "2. Buah & Biji Kakao",
        description = "Buah kakao dipanen, dibelah, lalu bijinya difermentasi dan dikeringkan di bawah sinar matahari.",
        stepNumber = 2,
    ),
    ChocolateStep(
        imageRes = R.drawable.bubuk_coklat,
        title = "3. Karung Bubuk Kakao",
        description = "Biji kakao kering dipanggang dan digiling halus hingga menghasilkan bubuk kakao murni.",
        stepNumber = 3,
    ),
    ChocolateStep(
        imageRes = R.drawable.coklat,
        title = "4. Cokelat Batangan",
        description = "Bubuk kakao dicampur dengan lemak kakao dan gula, kemudian dicetak menjadi cokelat batangan.",
        stepNumber = 4,
    ),
    ChocolateStep(
        imageRes = R.drawable.es_coklat,
        title = "5. Minuman Es Cokelat",
        description = "Cokelat olahan disajikan menjadi minuman es cokelat segar yang lezat dan siap dinikmati!",
        stepNumber = 5,
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChocolateProcessApp() {
    // Logika Manajemen State: Mengingat indeks tahapan saat ini (0 sampai 4)
    var currentStepIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Alur Pembuatan Cokelat",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            // Kontainer Utama: Hanya menampilkan SATU gambar di tengah layar pada satu waktu
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                // Transisi animasi yang mulus antar tahapan
                AnimatedContent(
                    targetState = currentStepIndex,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(400)) + scaleIn(
                            initialScale = 0.9f,
                            animationSpec = tween(400)
                        ))
                            .togetherWith(
                                fadeOut(animationSpec = tween(400)) + scaleOut(
                                    targetScale = 0.9f,
                                    animationSpec = tween(400)
                                ),
                            )
                    },
                    label = "ChocolateStepTransition",
                ) { targetIndex ->
                    val step = chocolateSteps[targetIndex]

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // Card Gambar yang Clickable (TANPA Button sama sekali)
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                            modifier = Modifier
                                .size(280.dp)
                                .shadow(12.dp, RoundedCornerShape(24.dp))
                                .border(
                                    width = 3.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(24.dp),
                                )
                                .clip(RoundedCornerShape(24.dp))
                                // Murni navigasi dengan mengklik gambar
                                .clickable {
                                    // Pindah ke tahapan berikutnya, atau kembali ke awal (looping)
                                    currentStepIndex = (currentStepIndex + 1) % chocolateSteps.size
                                },
                        ) {
                            Image(
                                painter = painterResource(id = step.imageRes),
                                contentDescription = step.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Judul Tahapan
                        Text(
                            text = step.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Deskripsi Tahapan
                        Text(
                            text = step.description,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChocolateProcessAppPreview() {
    LemonadeTheme {
        ChocolateProcessApp()
    }
}
