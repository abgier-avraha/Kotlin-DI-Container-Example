# Constructor-Based Dependency Injection for Kotlin JVM

## Uses Reified Generics

```kotlin
val container = DependencyInjectionContainer()

container
    .injectFactory<ILogger>({ parent -> Logger(parent) })
    .inject<IService, Service>()

val service = container.provide<IService>()
```

## Run Tests

```sh
./gradlew test --info
```
