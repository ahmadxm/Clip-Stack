# ShiftX (Android)

Native Android app built with Kotlin, Jetpack Compose, MVVM, Room, Hilt, Navigation, and DataStore.

## Setup
1. Open this project in Android Studio (Giraffe/Koala or newer).
2. Ensure Android Gradle Plugin and Kotlin plugin versions are compatible.
3. Create a `local.properties` with your SDK path if not auto-detected.

## Initial Data
- The Room database pre-populates from `app/src/main/assets/initial_data.json`.
- Replace placeholder employees/holidays with your web app's `src/lib/data.ts` content converted to JSON.

## Security
- Passwords are encrypted with a simple AES utility in `AESUtil`. Replace `SECRET` with a secure source and consider using salted hashing.

## Features
- Employee and Admin flows with separate navigation graphs.
- Login screens, dashboards, profiles, preferences.
- Room entities and DAOs for employees, shifts, public holidays, and Fill & Pay Later entries.
- DataStore for session and preferences.
- PDF export stubs via iText.