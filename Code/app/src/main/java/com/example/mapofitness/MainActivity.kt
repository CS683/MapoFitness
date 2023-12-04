package com.example.mapofitness

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapofitness.screens.login.LoginScreen
import com.example.mapofitness.screens.login.SignInViewModel
import com.example.mapofitness.screens.start.StartScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mapofitness.data.local.entity.User
import com.example.mapofitness.data.local.entity.UserManager
import com.example.mapofitness.screens.home.HomeScreen
import com.example.mapofitness.screens.login.GoogleAuthUiClient
import com.example.mapofitness.screens.navigation_bar.BottomTab
import com.example.mapofitness.screens.navigation_bar.NavigationBar
import com.example.mapofitness.screens.profile.ProfileScreen
import com.example.mapofitness.theme.MapoFitnessTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapoFitnessTheme {
                navigation()
            }
        }
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun navigation(){
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                val tabRoutes = BottomTab.values().map { it.route }
                val currentRoute = currentRoute(navController)
                if (currentRoute in tabRoutes) {
                    NavigationBar(navController)
                }
            }
        ) {
            NavHost(navController = navController, startDestination = "start") {
                composable("start"){ StartScreen(navController) }
                composable("login"){
                    val viewModel = viewModel<SignInViewModel>()
                    val state by viewModel.state.collectAsState()

                    LaunchedEffect(key1 = Unit) {
                        if(googleAuthUiClient.getSignedInUser() != null) {
                            checkUserState()
                            navController.navigate(BottomTab.Profile.route)
                        }
                    }

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
                            checkUserState()
                            navController.navigate(BottomTab.Profile.route)
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
                    ProfileScreen(
                        onSignOut = {
                            lifecycleScope.launch {
                                googleAuthUiClient.signOut()
                                Toast.makeText(
                                    applicationContext,
                                    "Signed out",
                                    Toast.LENGTH_LONG
                                ).show()

                                navController.popBackStack()
                            }
                        }
                    )
                }
                composable(BottomTab.Home.route){HomeScreen()}
                composable("navigationbar"){ NavigationBar(navController)}
            }
        }
    }

    private fun checkUserState(){
        val userId = googleAuthUiClient.getSignedInUser()!!.userId
        val user = googleAuthUiClient.getSignedInUser()
        val userRef = FirebaseDatabase.getInstance().getReference("users")
        userRef.child(userId).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val existUser = dataSnapshot.getValue(User::class.java)
                if (existUser != null) {
                    UserManager.setUser(existUser)
                }
            } else {
                val userMap = user?.toMap()
                if(user != null){
                    userRef.child(user.userId).setValue(userMap)
                        .addOnSuccessListener {
                            Log.i("Database", "Successful")
                        }
                        .addOnFailureListener {
                            Log.i("Database", "Failed")
                        }
                }
                if (user != null) {
                    UserManager.setUser(user)
                }
            }
            Log.i("Info", "Current user is: ${UserManager.currentUser?.username}")
        }.addOnFailureListener {
            Log.e("Firebase", "Error fetching user data")
        }
    }
}

@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

