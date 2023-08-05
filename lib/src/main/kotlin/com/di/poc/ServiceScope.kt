package com.di.poc

class ServiceScope
{
    public val container: DependencyInjectionContainer
    public var scopedCache = mutableMapOf<java.lang.Class<Any>, java.lang.Object>()

    constructor(container: DependencyInjectionContainer)
    {
        this.container = container
    }

    inline fun <reified TServiceType>provide() : TServiceType {
        val serviceType = TServiceType::class.java

        val instance = ReflectionConstructor.getInstanceFromType<TServiceType>(
            serviceType as java.lang.Class<TServiceType>,
            this.container.singletonDependencies,
            this.container.transientDependencies,
            this.container.scopedDependencies,
            this.scopedCache)

        if (instance == null) {
            throw Exception("Instance for ${serviceType} could not be fetched")
        }

        return instance as TServiceType
    }
}