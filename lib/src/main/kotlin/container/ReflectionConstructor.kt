/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package container

class ReflectionConstructor {

    companion object {
        fun <TServiceType> getInstanceFromType(
                type: Class<TServiceType>,
                singletonDependencies: MutableMap<Class<*>, Any>,
                transientDependencies: MutableMap<Class<*>, Class<*>>,
                scopedDependencies: MutableMap<Class<*>, Class<*>>,
                scopedDependencyFactories: MutableMap<Class<*>, () -> Any>,
                scopedCache: MutableMap<Class<*>, Any>,
        ): TServiceType? where TServiceType : Any {

            // Check if type is registered as a singleton dep
            val matchingSingleton = singletonDependencies.get(type) as TServiceType?

            if (matchingSingleton != null) {
                return matchingSingleton
            }

            // Check if type is registered as a transient dep
            val matchingTransient = transientDependencies.get(type) as Class<TServiceType>?
            if (matchingTransient != null) {
                val newInstance =
                        ReflectionConstructor.constructFromClass(
                                matchingTransient,
                                singletonDependencies,
                                transientDependencies,
                                scopedDependencies,
                                scopedDependencyFactories,
                                scopedCache
                        )
                return newInstance
            }

            // Check if type is a cached scoped dep
            val matchingScopedCached = scopedCache.get(type) as TServiceType?
            if (matchingScopedCached != null) {
                return matchingScopedCached
            }

            // Check if type is registered as a scoped dep
            val matchingScoped = scopedDependencies.get(type) as Class<TServiceType>?
            if (matchingScoped != null) {
                val newInstance =
                        ReflectionConstructor.constructFromClass(
                                matchingScoped,
                                singletonDependencies,
                                transientDependencies,
                                scopedDependencies,
                                scopedDependencyFactories,
                                scopedCache
                        )

                if (newInstance == null) {
                    throw Exception("Null returned when constructing scope class ${type}")
                }

                // Add to scoped cache
                scopedCache.put(type, newInstance)
                return newInstance
            }

            // Check if type is registered as a scoped dep factory
            val matchingScopedFactory = scopedDependencyFactories.get(type)
            if (matchingScopedFactory != null) {
                val newInstance = matchingScopedFactory() as TServiceType

                // Add to scoped cache
                scopedCache.put(type, newInstance)
                return newInstance
            }
            return null
        }

        fun <TServiceType> constructFromClass(
                serviceClass: Class<TServiceType>,
                singletonDependencies: MutableMap<Class<*>, Any>,
                transientDependencies: MutableMap<Class<*>, Class<*>>,
                scopedDependencies: MutableMap<Class<*>, Class<*>>,
                scopedDependencyFactories: MutableMap<Class<*>, () -> Any>,
                scopedCache: MutableMap<Class<*>, Any>,
        ): TServiceType? {

            // Retrieve constructor function
            val constructors = serviceClass.getConstructors()
            if (constructors.size < 1) {
                throw Exception("No constructor found for ${serviceClass}")
            }

            val primaryConstructor = serviceClass.getConstructors()[0]

            // Prepare list of args from existing services
            val params = mutableListOf<Any>()
            for (param in primaryConstructor.getParameters()) {
                val paramInstance =
                        ReflectionConstructor.getInstanceFromType(
                                param.type,
                                singletonDependencies,
                                transientDependencies,
                                scopedDependencies,
                                scopedDependencyFactories,
                                scopedCache
                        )
                if (paramInstance == null) {
                    throw Exception("Null returned when constructing parameter ${param.type}")
                }

                params.add(paramInstance)
            }

            return primaryConstructor.newInstance(*params.toTypedArray()) as TServiceType
        }

        inline fun <reified TServiceType, reified TService> construct(
                singletonDependencies: MutableMap<Class<*>, Any>,
                transientDependencies: MutableMap<Class<*>, Class<*>>,
                scopedDependencies: MutableMap<Class<*>, Class<*>>,
                scopedDependencyFactories: MutableMap<Class<*>, () -> Any>,
                scopedCache: MutableMap<Class<*>, Any>,
        ): TServiceType? where TService : TServiceType {
            return ReflectionConstructor.constructFromClass(
                    TService::class.java,
                    singletonDependencies,
                    transientDependencies,
                    scopedDependencies,
                    scopedDependencyFactories,
                    scopedCache
            )
        }
    }
}
