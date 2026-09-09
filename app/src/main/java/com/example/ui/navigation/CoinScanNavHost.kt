package com.example.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.CoinViewModel
import com.example.ui.screens.CatalogSearchScreen
import com.example.ui.screens.CollectionScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PrivacySettingsScreen
import com.example.ui.screens.ResultScreen
import com.example.ui.screens.ScannerScreen
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardSurfaceDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.SlateDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun CoinScanNavHost(
    viewModel: CoinViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute != Screen.Result.route

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = SlateDark,
                    contentColor = TextWhite,
                    tonalElevation = 0.dp,
                    modifier = Modifier
                        .testTag("bottom_nav_bar")
                        .border(width = 0.5.dp, color = BorderSubtle)
                ) {
                    Screen.bottomNavItems.forEach { screen ->
                        val selected = currentRoute == screen.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                val iconVector = if (selected) screen.selectedIcon else screen.unselectedIcon
                                if (iconVector != null) {
                                    Icon(
                                        imageVector = iconVector,
                                        contentDescription = screen.title,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    fontSize = 11.sp,
                                    maxLines = 1
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = GoldPrimary,
                                selectedTextColor = GoldPrimary,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted,
                                indicatorColor = CardSurfaceDark
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ObsidianDark)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToScanner = {
                        navController.navigate(Screen.Scanner.route)
                    },
                    onNavigateToCatalog = {
                        navController.navigate(Screen.Catalog.route)
                    },
                    onNavigateToCollection = {
                        navController.navigate(Screen.Collection.route)
                    },
                    onCoinSelected = { coin ->
                        viewModel.selectHistoryCoin(coin)
                        navController.navigate(Screen.Result.route)
                    }
                )
            }

            composable(Screen.Scanner.route) {
                ScannerScreen(
                    viewModel = viewModel,
                    onAnalysisFinished = {
                        navController.navigate(Screen.Result.route)
                    }
                )
            }

            composable(Screen.Collection.route) {
                CollectionScreen(
                    viewModel = viewModel,
                    onCoinSelected = { coin ->
                        viewModel.selectHistoryCoin(coin)
                        navController.navigate(Screen.Result.route)
                    },
                    onNavigateToScanner = {
                        navController.navigate(Screen.Scanner.route)
                    }
                )
            }

            composable(Screen.History.route) {
                HistoryScreen(
                    viewModel = viewModel,
                    onCoinSelected = { coin ->
                        viewModel.selectHistoryCoin(coin)
                        navController.navigate(Screen.Result.route)
                    },
                    onNavigateToScanner = {
                        navController.navigate(Screen.Scanner.route)
                    }
                )
            }

            composable(Screen.Catalog.route) {
                CatalogSearchScreen(
                    viewModel = viewModel,
                    onNavigateToScanner = {
                        navController.navigate(Screen.Scanner.route)
                    }
                )
            }

            composable(Screen.Settings.route) {
                PrivacySettingsScreen()
            }

            composable(Screen.Result.route) {
                ResultScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToScanner = {
                        viewModel.clearCapturedImages()
                        navController.navigate(Screen.Scanner.route) {
                            popUpTo(Screen.Home.route)
                        }
                    },
                    onNavigateToCollection = {
                        navController.navigate(Screen.Collection.route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                )
            }
        }
    }
}
