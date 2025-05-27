package pe.edu.upeu.granturismojpc.ui.presentation.screens.reserva
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upeu.granturismojpc.R

@Composable
fun ReservaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F0)) // Light background
            .padding(16.dp)
    ) {
        // Fecha de Selección
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFE67E22), RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "LUN", color = Color.White, fontSize = 12.sp)
                    Text(text = "15", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
            Text(text = "Marzo de 2025", color = Color(0xFFE67E22), fontSize = 16.sp)
        }

        // Anfitrión
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your actual image resource
                contentDescription = "Anfitrión",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(28.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Asociación Suma Jakañani", fontWeight = FontWeight.SemiBold, color = Color(0xFF4A4A4A))
                Text(text = "Tus anfitriones de hoy", color = Color.Gray, fontSize = 12.sp)
            }
        }

        // Experiencias del día
        ExperienceItem(
            icon = Icons.Filled.ShoppingCart,
            time = "08:00 - 10:00 AM",
            title = "Desayuno de Bienvenida",
            description = "Experiencia de desayuno tradicional andino",
            backgroundColor = Color(0xFFFFF3E0) // Light orange
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExperienceItem(
            icon = Icons.Filled.Timeline,
            time = "10:30 - 12:00 PM",
            title = "Taller de tejido",
            description = "Aprenda técnicas tradicionales de tejido",
            backgroundColor = Color(0xFFFFE0B2) // Light brown
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExperienceItem(
            icon = Icons.Filled.Restaurant,
            time = "12:30 - 02:00 PM",
            title = "Almuerzo Tradicional",
            description = "Auténtica cocina local",
            backgroundColor = Color(0xFFFFF9C4) // Light yellow
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ubicación
        Text(text = "Ubicación", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color(0xFF4A4A4A))
        Spacer(modifier = Modifier.height(8.dp))
        //MapViewComposable()

        Spacer(modifier = Modifier.height(16.dp))

        // Detalles de la reserva
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            //elevation = 2.dp
        ) {
            Column {
                Text(text = "Detalles de la reserva", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color(0xFF4A4A4A))
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Timeline, contentDescription = "Duración", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Duración: 8 horas", color = Color.Gray, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, contentDescription = "Ubicación", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Capachica, Puno, Peru", color = Color.Gray, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "S/.250", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFE67E22), modifier = Modifier.align(Alignment.End))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contactar al anfitrión
        Button(
            onClick = { /* TODO: Handle contact action */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            //colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF16A085)) // Green color
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_dialog_email), // Example email icon
                    contentDescription = "Contactar",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Contactar al anfitrión", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ExperienceItem(icon: androidx.compose.ui.graphics.vector.ImageVector, time: String, title: String, description: String, backgroundColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = Color.Gray, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = time, color = Color.Gray, fontSize = 12.sp)
            Text(text = title, fontWeight = FontWeight.SemiBold, color = Color(0xFF4A4A4A))
            Text(text = description, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

/*@Composable
fun MapViewComposable() {
    val juliaca = LatLng(-15.4969, -70.0226) // Approximate coordinates for Juliaca, Puno, Peru
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(juliaca, 10f)
    }
    MapView(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(8.dp)),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = juliaca),
            title = "Juliaca",
            snippet = "Puno, Peru"
        )
    }
}*/