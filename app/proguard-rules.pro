# Keep view binding generated classes
-keep class com.numluck.app.databinding.** { *; }

# Keep Kotlin metadata for reflection-free inlining
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Material Components
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**
