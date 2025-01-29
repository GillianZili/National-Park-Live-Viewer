package com.example.nationalparkliveviewerapp.ui.theme

data class Webcam (
    val title:String,
    val url: String,
    val isStreaming: Boolean,
    val relatedParks: List<Park>,
    val images: List<ParkImage>
)