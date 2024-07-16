package com.example.statussaver.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppNavigationDrawerItem(
    isSelected: Boolean,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onItemSelect:()->Unit,
    navItem: NavItem
) {
    Spacer(modifier = Modifier.height(16.dp))
    NavigationDrawerItem(
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        label = { Text(navItem.title) },
        selected = isSelected,
        onClick = {
            onItemSelect()
        },
        icon = {
            Icon(
                imageVector = if (isSelected) navItem.selectedIcon
                else navItem.unSelectedIcon,
                contentDescription = navItem.title
            )
        }
    )
}