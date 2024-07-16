package com.example.statussaver.presentation

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel() : ViewModel() {

    private var _uris = MutableStateFlow<List<Uri>>(emptyList())
    val uris = _uris.asStateFlow()

    init {
        getFolderContent("/storage/emulated/0/Android/media/com.whatsapp/WhatsApp/Media/.Statuses")
    }


    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.onDownloadIconClick -> {

            }

            HomeScreenEvent.onPhotoIconClick -> {
//                getFolderContent("/storage/emulated/0/DCIM/Facebook")
                getFolderContent("/storage/emulated/0/Android/media/com.whatsapp/WhatsApp/Media/.Statuses")
            }

            HomeScreenEvent.onVideoIconClick -> {
                getFolderContent("/storage/emulated/0/Movies")
            }

        }
    }


    private fun getFolderContent(path: String) {

        val directory = File(path)
        val files = directory.listFiles()?.filter { it.isFile }

        val uriList = files?.map {
            it.toUri()
        }

        uriList?.let { list ->
            _uris.update { list }
        }

        viewModelScope.launch {
            uriList?.let {
                _uris.emit(it)
            }
        }

        uriList?.forEach {
            Log.d("FolderController", it.toString())
        }

    }


}