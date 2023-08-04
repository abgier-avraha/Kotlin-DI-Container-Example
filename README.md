# Constructor-Based Dependency Injection for Kotlin JVM

Uses reified generics to build objects based on their constructor arguments.

## Example

```kotlin
fun main() {
    val container = DependencyInjectionContainer()
    
    container
        .injectTransient<ILogger, Logger>()
        .injectSingleton<IService, Service>()
    
    val service = container.provide<IService>()
    service.logger.configure(service)
    service.logSomething("Test")
}
```