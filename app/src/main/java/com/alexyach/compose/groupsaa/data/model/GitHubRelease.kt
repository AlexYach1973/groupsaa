package com.alexyach.compose.groupsaa.data.model

import com.google.gson.annotations.SerializedName

data class GitHubRelease(
    @SerializedName("tag_name") val tagName: String,
    val assets: List<GitHubAsset>,
    val name: String,
    val body: String,
    @SerializedName("published_at") val published: String,
)
