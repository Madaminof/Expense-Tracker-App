plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    id("com.google.dagger.hilt.android")
    id ("kotlin-kapt")
    kotlin("kapt")
}

android {
    namespace = "com.example.expancetracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.expancetracker"
        minSdk = 24
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    dependencies {
        // AndroidX Core
        implementation("androidx.core:core-ktx:1.15.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
        implementation("androidx.activity:activity-compose:1.9.3")

        // Compose + Material3
        implementation(platform("androidx.compose:compose-bom:2024.09.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3:1.3.0")
        implementation("androidx.compose.material3:material3-window-size-class:1.2.1")

        // 👇 Qo‘shish kerak bo‘lgan qator (swipeable uchun)
        implementation("androidx.compose.material:material:1.7.2")

        // Ikonalar
        implementation("androidx.compose.material:material-icons-extended")

        // Navigation
        implementation("androidx.navigation:navigation-compose")

        // Hilt
        implementation("com.google.dagger:hilt-android:2.51.1")
        implementation(libs.appcompat)
        implementation(libs.androidx.constraintlayout.compose.android)
        implementation(libs.androidx.constraintlayout)
        kapt("com.google.dagger:hilt-compiler:2.51.1")
        implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

        // Firebase
        implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
        implementation("com.google.firebase:firebase-database")
        implementation("com.google.firebase:firebase-storage")
        implementation("com.google.firebase:firebase-firestore")

        // Retrofit + OkHttp
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.11.0")
        implementation("com.squareup.okhttp3:okhttp:4.10.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

        // Room
        implementation("androidx.room:room-runtime:2.7.0")
        kapt("androidx.room:room-compiler:2.7.0")
        implementation("androidx.room:room-ktx:2.7.0")

        // DataStore
        implementation("androidx.datastore:datastore-preferences:1.1.1")

        // Coil
        implementation("io.coil-kt:coil-compose:2.6.0")

        // Accompanist
        implementation("com.google.accompanist:accompanist-systemuicontroller:0.35.0-alpha")
        implementation("com.google.accompanist:accompanist-navigation-animation:0.35.2-beta")

        // Test dependencies
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0")

        // Debug only
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")


    }



}
