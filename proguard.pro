-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-injars /tmp/NaughtyClock.jar
-outjars dist/NaughtyClock.jar
-libraryjars /System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Classes/classes.jar
-libraryjars /System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Classes/charsets.jar
-libraryjars /System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Classes/dt.jar
-libraryjars /System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Classes/jce.jar
-libraryjars /System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Classes/jsse.jar
-libraryjars /System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Classes/laf.jar
-libraryjars /System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Classes/ui.jar
-libraryjars dist/lib/swing-layout-1.0.3.jar
-keep public class naughtyclock.Main {
   public static void main(java.lang.String[]);
}

