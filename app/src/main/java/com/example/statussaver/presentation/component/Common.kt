package com.example.statussaver.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector


data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)


val navItems = listOf(
    NavItem(
        title = "Status", selectedIcon = Icons.Filled.Home, unSelectedIcon = Icons.Outlined.Home
    ),
    NavItem(
        title = "B-Status",
        selectedIcon = Icons.Filled.AccountBox,
        unSelectedIcon = Icons.Outlined.AccountBox
    ),
    NavItem(
        title = "Info", selectedIcon = Icons.Filled.Info, unSelectedIcon = Icons.Outlined.Info
    )
)

@Composable
fun BottomNavBar() {

    var selectedItemNumber by remember {
        mutableStateOf(2)
    }

    NavigationBar {
        navItems.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedItemNumber == index,
                onClick = {
                    selectedItemNumber = index
                },
                icon = {
                    Icon(
                        imageVector = if (selectedItemNumber == index) navItem.selectedIcon
                        else navItem.unSelectedIcon, contentDescription = navItem.title
                    )
                },
                label = {
                    Text(text = navItem.title)
                }
            )
        }
    }

}