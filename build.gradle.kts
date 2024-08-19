plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21"
    id("org.jetbrains.kotlin.kapt") version "1.8.22"
    id("com.google.gms.google-services") version "4.4.2" apply false
}


