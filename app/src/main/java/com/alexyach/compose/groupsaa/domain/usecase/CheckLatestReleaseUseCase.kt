package com.alexyach.compose.groupsaa.domain.usecase

import com.alexyach.compose.groupsaa.data.model.GitHubRelease
import com.alexyach.compose.groupsaa.data.network.GitHubApi
import javax.inject.Inject

class CheckLatestReleaseUseCase @Inject constructor(
    private val api: GitHubApi
) {
    suspend fun invoke(owner: String, repo: String): GitHubRelease? {
        return api.getLatestRelease(owner = owner, repo = repo)
    }
}