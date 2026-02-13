//package com.alexyach.compose.groupsaa.presentation.navigation3
//
//import androidx.navigation3.runtime.NavKey
//import com.alexyach.compose.groupsaa.R
//import com.alexyach.compose.groupsaa.domain.model.Group
//import kotlinx.serialization.Serializable
//
//@Serializable
//sealed class TopLevelScreen: NavKey {
//    data class UnityKey(
//        val icon: Int = R.drawable.group2,
//        val label: Int = R.string.navigation3_item_unity
//    ) : TopLevelScreen()
//
//    data class GroupDetails(val group: Group) : TopLevelScreen()
//
//    data class RecoveryKey (
//        val icon: Int = R.drawable.health,
//        val label: Int = R.string.navigation3_item_recovery
//    ) : TopLevelScreen()
//
//    data class ServiceKey (
//        val icon: Int = R.drawable.book2,
//        val label: Int = R.string.navigation3_item_service
//    ) : TopLevelScreen()
//
//}
//val TOP_LEVEL_SCREEN = listOf(TopLevelScreen.UnityKey(), TopLevelScreen.RecoveryKey(),
//    TopLevelScreen.ServiceKey()
//)
//
///*
//private sealed interface TopLevelScreen : NavKey {
//    val icon: Int
//    val label: Int
//}
//
//@Serializable
//private data object UnityKey : TopLevelScreen {
//    override val icon = R.drawable.group2
//    override val label = R.string.navigation3_item_unity
//}
//
//@Serializable
//private data class GroupDetails(val group: Group) : NavKey
//
//@Serializable
//private data object RecoveryKey : TopLevelScreen {
//    override val icon = R.drawable.health
//    override val label = R.string.navigation3_item_recovery
//}
//
//@Serializable
//private data object ServiceKey : TopLevelScreen {
//    override val icon = R.drawable.book2
//    override val label = R.string.navigation3_item_service
//}
//
//private val TOP_LEVEL_SCREEN = listOf(UnityKey, RecoveryKey, ServiceKey)
//
//
// */