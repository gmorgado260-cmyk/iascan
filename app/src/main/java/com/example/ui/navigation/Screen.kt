package com.example.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.CollectionsBookmark
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
) {
    object Home : Screen(
        route = "home",
        title = "Início",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    object Scanner : Screen(
        route = "scanner",
        title = "Scanner",
        selectedIcon = Icons.Filled.PhotoCamera,
        unselectedIcon = Icons.Outlined.PhotoCamera
    )

    object Collection : Screen(
        route = "collection",
        title = "Coleção",
        selectedIcon = Icons.Filled.CollectionsBookmark,
        unselectedIcon = Icons.Outlined.CollectionsBookmark
    )

    object History : Screen(
        route = "history",
        title = "Histórico",
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.History
    )

    object Catalog : Screen(
        route = "catalog",
        title = "Pesquisa",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )

    object Settings : Screen(
        route = "settings",
        title = "Privacidade",
        selectedIcon = Icons.Filled.Shield,
        unselectedIcon = Icons.Outlined.Shield
    )

    object Result : Screen(
        route = "result",
        title = "Avaliação"
    )

    companion object {
        val bottomNavItems = listOf(Home, Scanner, Collection, History, Catalog, Settings)
    }
}
