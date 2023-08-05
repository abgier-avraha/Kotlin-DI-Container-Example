# Constructor-Based Dependency Injection for Kotlin JVM

Uses reified generics to build objects based on their constructor arguments.

Refer to `lib/src/test/kotlin/com/di/poc/DependencyInjectionContainerTest.kt` for more detailed examples.

## Example

```kotlin
fun main() {
    val container = DependencyInjectionContainer()
    
    container
        .injectTransient<ILogger, Logger>()
        .injectScoped<IService, Service>()

    val scope = container.createScope()
    val service = scope.provide<IService>()
    service.logger.configure(service)
    service.logSomething("Test")
}
```