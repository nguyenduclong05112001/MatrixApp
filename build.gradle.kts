plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false

    //Google firebase
    alias(libs.plugins.gms.google.services) apply false

    //dagger
    alias(libs.plugins.hilt.android) apply false
}