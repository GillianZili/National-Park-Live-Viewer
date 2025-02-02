package com.example.nationalparkliveviewerapp.ui.theme

data class Webcam (
    val title:String,
    val url: String,
    val isStreaming: Boolean,
    val relatedParks: List<ParkLocation>,
    val images: List<ParkImage>
)