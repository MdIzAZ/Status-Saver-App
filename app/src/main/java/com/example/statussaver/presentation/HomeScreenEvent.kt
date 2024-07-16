package com.example.statussaver.presentation

import android.net.Uri

sealed class HomeScreenEvent {
    data object onPhotoIconClick: HomeScreenEvent()
    data object onVideoIconClick: HomeScreenEvent()
    data class onDownloadIconClick(val uri: Uri?): HomeScreenEvent()
}