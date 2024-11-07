plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.hiltAndroid)
}

var versionMajor by extra(0)
var versionMinor by extra(1)
var versionPatch by extra(0)
var versionClassifier: String? by extra(null)
var isSnapshot by extra(true)

var isFeature = false

//change this flag to "true" if you need clear storage data after realise new app version
//var clearStorageFlag by extra(true)

val minSdkVer: Int by rootProject.extra
val targetSdkVer: Int by rootProject.extra
val compileSdkVer: Int by rootProject.extra

android {
    namespace = "com.example.financecontrol"
    compileSdk = compileSdkVer

    signingConfigs {
        create("release") {
            val filePath = project.rootProject.file("./app/keyStore.jks")
            storeFile =
                file(filePath)
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }

    defaultConfig {
        applicationId = "com.example.financecontrol"
        minSdk = minSdkVer
        targetSdk = targetSdkVer
        versionCode = generateVersionCode()
        versionName = generateVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")

            resValue("string", "app_name", if (isFeature) "FFinanceControl" else "FinanceControl")
            if (isFeature) {
                applicationIdSuffix = ".feature"
                versionNameSuffix = "-FEATURE"
            }
        }
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            resValue("string", "app_name", "DFinanceControl")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.keyboardvisibilityevent)

    implementation(libs.androidx.fragment.ktx)

    implementation(libs.androidx.preference.ktx)


    implementation(libs.rxjava2)
    implementation(libs.rxkotlin2)
    implementation(libs.rxandroid)

    implementation(libs.rxbinding)
//    implementation (libs.rxbinding.kotlin)

    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.work.rxjava2)
    implementation(libs.androidx.hilt.work)
    annotationProcessor(libs.androidx.hilt.compiler)

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.room.compiler)
    implementation(libs.androidx.room.rxjava2)

    implementation(libs.gson)


    implementation(libs.retrofit)
    implementation(libs.adapter.rxjava2)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.swiperefreshlayout)

    implementation(libs.timber)

    implementation ("com.github.Dimezis:BlurView:version-2.0.5")

    implementation(project(":core:models"))
    implementation(project(":core:database"))
    implementation(project(":core:data"))
    implementation(project(":core:ui"))

    implementation(project(":features:finance"))
    implementation(project(":features:add"))
    implementation(project(":features:money"))
}

fun generateVersionCode(): Int {
    return versionMajor * 10000 + versionMinor * 100 + versionPatch;
}

fun generateVersionName(): String {
    var versionName: String =
        "${versionMajor}.${versionMinor}.${versionPatch}"
    if (versionClassifier == null && isSnapshot)
        versionClassifier = "SNAPSHOT"

    if (versionClassifier != null) {
        versionName += "-$versionClassifier"
    }

    return versionName

}