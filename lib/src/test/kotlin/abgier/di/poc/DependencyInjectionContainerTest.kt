/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package abgier.di.poc

import kotlin.test.Test
import kotlin.test.assertTrue

class DependencyInjectionContainerTest {
    
    @Test fun canInjectAndProvide() {
        val container = DependencyInjectionContainer()
        
        container
            .injectFactory<ILogger<Any>>({parent -> Logger(parent) as ILogger<Any>})
            .inject<IService, Service>()

        assertTrue(container.provide<IService>().someMethod())
    }

    @Test fun singletonFactoriesUsesCorrectClass() {
        val container = DependencyInjectionContainer()
        
        container
            .injectFactory<ILogger<Any>>({parent -> Logger(parent) as ILogger<Any>})

        val dummyLogger = container.provideFactory<ILogger<Dummy>, Dummy>()
        assertTrue(dummyLogger != null)

        dummyLogger.info("Hello World")
    }

    @Test fun factoryClassesAreCached() {
        val container = DependencyInjectionContainer()
        
        container
            .injectFactory<ILogger<Any>>({parent -> Logger(parent) as ILogger<Any>})

        val dummyLoggerFirst = container.provideFactory<ILogger<Dummy>, Dummy>()
        val dummyLoggerSecond = container.provideFactory<ILogger<Dummy>, Dummy>()
        assertTrue(dummyLoggerFirst == dummyLoggerSecond)
    }
}

class Dummy {

}

interface ILogger<T> {
    fun info(message: String)
}


class Logger<T> : ILogger<T> {
    private val parentClass: java.lang.Class<T>

    constructor(parentClass: java.lang.Class<T>) {
        this.parentClass = parentClass
    }

    override fun info(message: String) {
        println("${this.parentClass.getName()}::${message}")
    }
}

interface IService {
    fun someMethod(): Boolean
}

class Service : IService {
    val logger: ILogger<Service>

    constructor(logger: ILogger<Service>) {
        this.logger = logger
    }

    override fun someMethod(): Boolean
    {
        this.logger.info("Hello World")
        return true
    }
}