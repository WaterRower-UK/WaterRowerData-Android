# WaterRowerData for Android

A library for reading data from a BLE-connected WaterRower device

## WaterRowerData-BLE

The `uk.co.waterrower:waterrowerdata-ble` artifact contains the sources for
reading data from a BLE connected WaterRower device, such as an S5 monitor.
See [waterrowerdata-ble/Readme](waterrowerdata-ble/README.md) for usage
instructions.

## Setup

This library is available on Maven Central.

Include the following in your project's `build.gradle` file:

```diff
buildscript {
  // ...
}

+ subprojects {
+   repositories {
+     mavenCentral()
+   }
+ }
```

Include the following in the `build.gradle` file of the module you wish to add
the dependency to, replacing `x.x.x` with the latest version:

```diff
dependencies {
+ implementation "uk.co.waterrower:waterrower-ble:x.x.x"
}
```

## Development

This project uses Gradle to test and build the library:

 - `./gradlew test` builds and runs the tests
 - `./gradlew :sample-app:installDebug` installs the sample application on
    a connected device.

## Releasing

See [RELEASING.md](RELEASING.md)