# Constructor-Based Dependency Injection for Kotlin JVM

## Uses Reified Generics

```kotlin
fun main() {
    val container = DependencyInjectionContainer()
    
    container
        .injectFactory<ILogger>({ parent -> Logger(parent) })
        .inject<IService, Service>()
    
    val service = container.provide<IService>()
    service.logSomething("Test")
}

interface ILogger {
    fun info(message: String)
}

class Logger : ILogger {
    private val parentClass: java.lang.Class<Any>

    constructor(parentClass: java.lang.Class<Any>) {
        this.parentClass = parentClass
    }

    override fun info(message: String) {
        println("${this.parentClass.getName()}::${message}")
    }
}

interface IService {
    fun logSomething()
}

class Service : IService {
    private val logger: ILogger

    constructor(logger: ILogger) {
        this.logger = logger
    }

    override fun logSomething(message: String): Boolean
    {
        this.logger.info(message)
    }
}

```

## Run Tests

```sh
./gradlew test --info
```
