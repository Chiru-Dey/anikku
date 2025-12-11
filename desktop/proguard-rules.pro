# Anikku Desktop ProGuard Rules

# Keep main entry point
-keep class MainKt { *; }

# Keep Compose runtime
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }

# Keep VLCJ classes
-keep class uk.co.caprica.vlcj.** { *; }

# Keep JNA classes
-keep class com.sun.jna.** { *; }
-keepclassmembers class * extends com.sun.jna.** { *; }

# Keep Koin
-keep class org.koin.** { *; }

# Keep coroutines
-keep class kotlinx.coroutines.** { *; }

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
