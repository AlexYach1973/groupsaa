package com.alexyach.compose.groupsaa.data.model

import com.google.gson.annotations.SerializedName

data class GitHubAsset(
    val name: String,
    @SerializedName("browser_download_url") val downloadUrl: String
)
