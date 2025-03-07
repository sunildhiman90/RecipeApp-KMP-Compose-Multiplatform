package org.sunildhiman90.recipeappcmp.features.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.sunildhiman90.recipeappcmp.features.common.ui.components.ErrorContent
import recipeapp_cmp.composeapp.generated.resources.Res
import recipeapp_cmp.composeapp.generated.resources.app_name
import recipeapp_cmp.composeapp.generated.resources.recipe_app_logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenModalBottomSheet(
    loginViewModel: LoginViewModel,
    showBottomSheet: Boolean,
    onClose: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = {
            false
        })

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var clearInputFields = {
        email = ""
        password = ""
    }

    val scope = rememberCoroutineScope()
    val onCloseIconClick = {
        scope.launch {
            clearInputFields()
            bottomSheetState.hide()
        }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                onClose()
            }
        }
    }

    val loginState by loginViewModel.loginState.collectAsState()

    if (showBottomSheet) {

        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            dragHandle = {},
            onDismissRequest = {
                onClose()
                clearInputFields()
            },
            sheetState = bottomSheetState,
            properties = ModalBottomSheetProperties(
                shouldDismissOnBackPress = true
            )
        ) {

            Column(
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //Heading
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start,
                        text = stringResource(Res.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        modifier = Modifier.clickable {
                            onCloseIconClick()
                        },
                        contentDescription = "Close",
                        imageVector = Icons.Outlined.Close,
                        tint = MaterialTheme.colorScheme.onBackground
                    )

                }

                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

                    //Image or logo
                    Image(
                        painter = painterResource(Res.drawable.recipe_app_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    //Email INput

                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                            cursorColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = {
                            Text("Email")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                            cursorColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = {
                            Text("Password")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    when (
                        loginState
                    ) {
                        is LoginState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                        }

                        is LoginState.Error -> {
                            ErrorContent(text = (loginState as LoginState.Error).message)
                        }

                        is LoginState.Success -> {

                            LaunchedEffect(Unit) {
                                onCloseIconClick()
                                onLoginSuccess()
                            }
                        }

                        else -> Unit
                    }

                    //Button
                    Button(
                        modifier = Modifier.fillMaxWidth().height(45.dp),
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primaryContainer),
                        ),
                        onClick = {
                            loginViewModel.login(email, password)
                        },
                        enabled = loginState !is LoginState.Loading,
                    ) {
                        Text("Login", color = MaterialTheme.colorScheme.onPrimary)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                }
            }

        }
    }


}