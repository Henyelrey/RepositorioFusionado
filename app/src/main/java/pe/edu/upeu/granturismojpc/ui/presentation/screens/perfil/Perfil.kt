package pe.edu.upeu.granturismojpc.ui.presentation.screens.perfil

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upeu.granturismojpc.ui.navigation.Destinations
import pe.edu.upeu.granturismojpc.ui.presentation.components.SimpleBottomNavigationBar
import pe.edu.upeu.granturismojpc.utils.TokenUtils


@Composable
fun Perfil(
    navergarRegistro: (String) -> Unit,
    navController: NavHostController,
) {
    Scaffold (
        bottomBar = {
            SimpleBottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, bottom = 60.dp)
                .padding(paddingValues), // <- Añadido para respetar el espacio del bottomBar
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User Icon",
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = TokenUtils.USER_LOGIN,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            SettingItem("Idioma", "Español") {
                navController.navigate(Destinations.Language.route)
            }
            SettingItem("Currency", "US") {
                navController.navigate(Destinations.Currency.route)
            }
            SettingItem("Todos mis planes") {
                navController.navigate(Destinations.Plans.route)
            }
            SettingItem("Contactenos") {
                navController.navigate(Destinations.Contact.route)
            }
            SettingItem("Pagina web") {
                navController.navigate(Destinations.Web.route)
            }
            SettingItem("Políticas de privacidad") {
                navController.navigate(Destinations.Privacy.route)
            }
            SettingItem("Condiciones de uso") {
                navController.navigate(Destinations.Terms.route)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "VERSION 1.1.1.1",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 12.dp),
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}


@Composable
fun SettingItem(title: String, value: String? = null, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            fontSize = 16.sp
        )
        if (value != null) {
            Text(text = value, color = Color.Black, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Arrow",
            tint = Color.Gray
        )
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}