# Constructor-Based Dependency Injection for Kotlin JVM

Uses reified generics to build objects based on their constructor arguments.

Refer to `example/src/` for more detailed examples on creating a web server and testing.

## Example

```kotlin
fun main() {
    val container = DependencyInjectionContainer()
    
    // Inject your classes based on the lifecycle you want
    container
        .injectSingleton<IJsonFormatter, JsonFormatter>()
        .injectTransient<ILogger, Logger>()
        .injectScoped<IService, Service>()

    // Create a scope
    val scope = container.createScope()

    // Fetch an instance of a class from the scope
    val service = scope.provide<IService>()

    service.executeSomeMethod()
}
```

## Run Tests

```sh
./gradlew test --info
```