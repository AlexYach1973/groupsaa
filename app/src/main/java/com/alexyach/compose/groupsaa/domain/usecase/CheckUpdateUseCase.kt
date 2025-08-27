package com.alexyach.compose.groupsaa.domain.usecase

import com.alexyach.compose.groupsaa.data.model.GitHubAsset
import com.alexyach.compose.groupsaa.data.repository.UpdateRepository
import javax.inject.Inject

class CheckUpdateUseCase @Inject constructor(
    private val repository: UpdateRepository
) {

    suspend operator fun invoke(owner: String, repo: String): GitHubAsset? {
        return repository.fetchLatestApk(owner = owner, repo = repo)
    }

}