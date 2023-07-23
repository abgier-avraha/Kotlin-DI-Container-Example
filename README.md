# Constructor-Based Dependency Injection for Kotlin JVM

## Uses Reified Generics

```kotlin
sharedDependencyInjectionContainer.inject<IServiceA, ServiceA>()
sharedDependencyInjectionContainer.inject<IRequiresA, RequiresA>()

val instance = sharedDependencyInjectionContainer.provide<IRequiresA>()
```

## Run Tests

```sh
./gradlew test --info
```
