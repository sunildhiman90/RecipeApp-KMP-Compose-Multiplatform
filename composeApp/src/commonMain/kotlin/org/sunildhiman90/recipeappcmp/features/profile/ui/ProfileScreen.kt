package org.sunildhiman90.recipeappcmp.features.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.sunildhiman90.recipeappcmp.features.common.ui.components.ErrorContent
import org.sunildhiman90.recipeappcmp.features.common.ui.components.Loader
import org.sunildhiman90.recipeappcmp.features.profile.data.User
import recipeapp_cmp.composeapp.generated.resources.Res
import recipeapp_cmp.composeapp.generated.resources.avatar
import recipeapp_cmp.composeapp.generated.resources.profile_dummy

@Composable
fun ProfileRoute(
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    onLogout: () -> Unit,
    profileViewModel: ProfileViewModel = koinViewModel()
) {

    val profileScreenUiState = profileViewModel.profileUiState.collectAsState()

    ProfileScreen(
        isUserLoggedIn = isUserLoggedIn,
        profileScreenUiState = profileScreenUiState.value,
        onEditProfile = {},
        onLogout = {
            onLogout()
        },
        onLogin = {
            openLoginBottomSheet {
                profileViewModel.refresh()
            }
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    isUserLoggedIn: () -> Boolean,
    profileScreenUiState: ProfileScreenUiState,
    onEditProfile: () -> Unit,
    onLogin: () -> Unit,
    onLogout: () -> Unit
) {
    println("isUserLoggedIn=${isUserLoggedIn.invoke()}")
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text("Profile")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        ) {
            HorizontalDivider(
                thickness = 0.3.dp,
                color = MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.5f
                )
            )

            when {

                !isUserLoggedIn() -> {
                    NotLoggedInProfileScreen(
                        onLogin = onLogin,
                        onSignUp = {}
                    )
                }

                profileScreenUiState.isLoading -> {
                    Loader()
                }

                profileScreenUiState.error != null -> {
                    ErrorContent()
                }

                profileScreenUiState.userInfo != null && isUserLoggedIn() -> {
                    ProfileContent(
                        userInfo = profileScreenUiState.userInfo,
                        onEditProfile = onEditProfile,
                        onLogout = onLogout
                    )
                }
            }
        }
    }

}

@Composable
fun NotLoggedInProfileScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(Res.drawable.profile_dummy),
            contentDescription = "Placeholder",
            modifier = Modifier.size(120.dp).clip(CircleShape).border(
                0.3.dp, MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.5f
                ), CircleShape
            ).background(MaterialTheme.colorScheme.outline),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "You are not Logged In",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Login to view your profile",
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.fillMaxWidth().height(45.dp)) {
            Button(
                onClick = onLogin,
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    "Log In", fontSize = 16.sp
                )
            }
        }


        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth().height(45.dp)) {

            OutlinedButton(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                onClick = onSignUp,
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.primaryContainer,
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(
                    "Sign Up", fontSize = 16.sp
                )
            }
        }

    }

}

@Composable
fun ProfileContent(
    userInfo: User,
    onEditProfile: () -> Unit,
    onLogout: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(Res.drawable.avatar),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(
                    0.3.dp, MaterialTheme.colorScheme.outline.copy(
                        alpha = 0.5f
                    ), CircleShape
                ).background(MaterialTheme.colorScheme.outline),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            userInfo.email,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth().height(45.dp)) {

            OutlinedButton(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                onClick = onEditProfile,
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.primaryContainer,
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(
                    "Edit Profile", fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth().height(45.dp)) {

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    "Logout", fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

        }


    }

}