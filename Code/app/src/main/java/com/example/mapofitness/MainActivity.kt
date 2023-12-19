package com.example.mapofitness

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mapofitness.screens.activity.ActivityScreen
import com.example.mapofitness.screens.activity.ActivityViewModel
import com.example.mapofitness.screens.dashboard.DashboardViewModel
import com.example.mapofitness.screens.home.AddWeightBottomSheetContent
import com.example.mapofitness.screens.home.HomeScreen
import com.example.mapofitness.screens.home.HomeViewModel
import com.example.mapofitness.screens.login.GoogleAuthUiClient
import com.example.mapofitness.screens.login.LoginScreen
import com.example.mapofitness.screens.login.SignInViewModel
import com.example.mapofitness.screens.navigation_bar.BottomTab
import com.example.mapofitness.screens.navigation_bar.NavigationBar
import com.example.mapofitness.screens.profile.PersonalInfoScreen
import com.example.mapofitness.screens.profile.PersonalInfoViewModel
import com.example.mapofitness.screens.profile.ProfileScreen
import com.example.mapofitness.screens.profile.ProfileViewModel
import com.example.mapofitness.screens.start.StartScreen
import com.example.mapofitness.theme.Dark
import com.example.mapofitness.theme.MapoFitnessTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapoFitnessTheme {
                navigation()
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun navigation(){
        val navController = rememberNavController()
        val addWeightSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()
        val homeViewModel = viewModel<HomeViewModel>()

        ModalBottomSheetLayout(
            sheetState = addWeightSheetState,
            sheetContent = {
                AddWeightBottomSheetContent(onDismiss = {coroutineScope.launch { addWeightSheetState.hide() }}, homeViewModel)
            },
            sheetBackgroundColor = Dark,
            sheetShape = RoundedCornerShape(20.dp),
            content = {
                Scaffold(
                    bottomBar = {
                        val tabRoutes = BottomTab.values().map { it.route }
                        val currentRoute = currentRoute(navController)
                        if (currentRoute in tabRoutes) {
                            NavigationBar(navController)
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination =
                        if(googleAuthUiClient.getSignedInUser() != null){
                            BottomTab.Home.route
                        }else{
                            "start"
                        }
                    ) {
                        composable("start"){
                            StartScreen(navController)
                        }
                        composable("login"){
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsState()


                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if(result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.navigate(BottomTab.Home.route)
                                    viewModel.resetState()
                                }
                            }

                            LoginScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable(BottomTab.Profile.route) {
                            val viewModel = viewModel<ProfileViewModel>()
                            ProfileScreen(
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.navigate("login")
                                    }
                                },
                                onPersonalInfo = { navController.navigate("personalInfo") },
                                viewModel
                            )
                        }
                        composable(BottomTab.Home.route){
                            val dashboardViewModel = viewModel<DashboardViewModel>()
                            HomeScreen(
                                homeViewModel,
                                dashboardViewModel,
                                addWeightSheetState,
                                onActivity = { navController.navigate("activity") }
                            )
                        }
                        composable("navigationbar"){ NavigationBar(navController) }
                        composable("personalInfo"){
                            val persoanlInfoViewModel = viewModel<PersonalInfoViewModel>()
                            PersonalInfoScreen(
                                onBack = { navController.navigate(BottomTab.Profile.route) },
                                persoanlInfoViewModel
                            )
                        }
                        composable("activity") {
                            val activityViewModel = viewModel<ActivityViewModel>()
                            ActivityScreen(
                                onBack = { navController.navigate(BottomTab.Home.route) },
                                activityViewModel
                            )
                        }
                    }
                }
            }
        )
    }
}


@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

