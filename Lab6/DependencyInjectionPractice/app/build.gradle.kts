plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	id("kotlin-kapt")
}

android {
	namespace = "ru.nsu.dependencyinjectionpractice"
	compileSdk = 34

	defaultConfig {
		applicationId = "ru.nsu.dependencyinjectionpractice"
		minSdk = 26
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	buildFeatures {
		viewBinding = true
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation("com.google.dagger:dagger:2.52")
	kapt("com.google.dagger:dagger-compiler:2.52")
	testImplementation(libs.junit)
	implementation(libs.androidx.activity.ktx)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)

}