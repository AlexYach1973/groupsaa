import android.databinding.tool.ext.cleanLazyProps
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt.android)
    id("kotlin-parcelize")
}


android {
    namespace = "com.alexyach.compose.groupsaa"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.alexyach.compose.groupsaa"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.11"
//    }

}

configurations.all {
    exclude(group = "com.intellij", module = "annotations")
}

//configurations.all {
//    exclude(group = "com.intellij", module = "annotations")
//}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.navigation)
    implementation(libs.androidx.compose.livedata)
    implementation(libs.androidx.compose.viewmodel)
    implementation(libs.androidx.gson)
    implementation(libs.play.services.location)
    implementation(libs.androidx.compose.permission)
    implementation(libs.androidx.animation)

    // Retrofit
    implementation(libs.androidx.retrofit)
    implementation(libs.androidx.retrofit.gson)
    // Room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.datastore.preference)

    implementation(libs.androidx.coroutines.play.services)

    // Hilt
    implementation(libs.androidx.hilt.android)
    implementation(libs.androidx.hilt.navigation)
    ksp(libs.androidx.hilt.compiler)

    api(libs.coroutines.core)


//    implementation(libs.androidx.foundation)

    //!!!
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1")
//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1'

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}