package com.alexyach.compose.groupsaa.data.repository

import com.alexyach.compose.groupsaa.data.model.GitHubAsset
import com.alexyach.compose.groupsaa.data.network.GitHubApi
import javax.inject.Inject

class UpdateRepository @Inject constructor(
    private val api: GitHubApi
) {

    suspend fun fetchLatestApk(owner: String, repo: String): GitHubAsset? {
        val release = api.getLatestRelease(owner = owner, repo = repo)

        return release.assets.find { it.name.endsWith(".apk") }
    }

}