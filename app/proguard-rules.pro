# Preserve application-specific models and data classes
-keep class group.beymen.network.data.model.** { *; }
-keep class group.beymen.network.data.source.local.** { *; }

# Prevent obfuscation of Hilt-generated classes and modules
-keep class dagger.hilt.** { *; }
-keep class dagger.hilt.internal.** { *; }
-keep class androidx.hilt.** { *; }
-keep class dagger.internal.codegen.** { *; }

# Preserve Retrofit interfaces and serialized names
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }

# Gson rules to keep serialized field names
-keep class com.google.gson.annotations.SerializedName
-keepattributes *Annotation*

# Room database rules
-keep class androidx.room.** { *; }
-keep class androidx.sqlite.db.** { *; }
-keepattributes *Annotation*

# Jetpack Compose rules
-keep class androidx.compose.** { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}
-dontwarn androidx.compose.**

# Prevent stripping resources and layout ids
-keepclassmembers class * {
    @androidx.annotation.IdRes <fields>;
}
-keepclassmembers class * {
    @androidx.annotation.StringRes <fields>;
}

# OkHttp rules
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# Logging libraries (if used)
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

# Debugging stack traces
-keepattributes SourceFile,LineNumberTable

# Hiding original source file names
-renamesourcefileattribute SourceFile

# Support libraries and other framework components
-dontwarn android.arch.**
-dontwarn android.support.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
