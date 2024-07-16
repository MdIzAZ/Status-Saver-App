package com.example.statussaver

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.statussaver.presentation.HomeScreenEvent
import com.example.statussaver.presentation.HomeViewModel
import com.example.statussaver.presentation.StatusScreen
import com.example.statussaver.presentation.component.AppNavigationDrawerItem
import com.example.statussaver.presentation.component.HomeScreenTopBar
import com.example.statussaver.presentation.component.navItems
import com.example.statussaver.ui.theme.StatusSaverTheme
import kotlinx.coroutines.launch

//@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StatusSaverTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                var selectedItemIndex by remember { mutableStateOf(0) }
                val scope = rememberCoroutineScope()
                val viewModel = viewModel<HomeViewModel>()
                val list by viewModel.uris.collectAsState()

                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet {
                            navItems.forEachIndexed { index, navItem ->
                                AppNavigationDrawerItem(
                                    isSelected = selectedItemIndex == index,
                                    scope = scope,
                                    drawerState = drawerState,
                                    onItemSelect = {
                                        selectedItemIndex = index
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    navItem = navItem
                                )
                            }
                        }
                    },
                    drawerState = drawerState,
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            HomeScreenTopBar(
                                scope = scope,
                                drawerState = drawerState,
                                onPermissionClick = { requestFolderPermission(Constants.getWhatsappUri()) }
                            )
                        }

                    ) { innerPadding ->
                        StatusScreen(
                            modifier = Modifier.padding(innerPadding),
                            photoUris = list,
                            onImagesButtonClick = { viewModel.onEvent(HomeScreenEvent.onPhotoIconClick) },
                            onVideosButtonClick = { viewModel.onEvent(HomeScreenEvent.onVideoIconClick) }
                        )
                    }
                }


            }
        }
        requestPermission()
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_IMAGES
            ),
            420
        )
    }

    private fun requestFolderPermission(initialUri: Uri) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).also {
            it.putExtra(DocumentsContract.EXTRA_INITIAL_URI, initialUri)
            it.putExtra("android.content.extra.SHOW_ADVANCED", true)
        }
        ActivityCompat.startActivityForResult(
            this,
            intent,
            430,
            null
        )
//        this.startActivityForResult(intent, 430)
    }


}



