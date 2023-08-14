package example

import container.DependencyInjectionContainer
import example.services.IRandomStringsService
import example.services.RandomStringsService
import example.utils.IRandomProvider
import example.utils.RandomProvider

val rootContainer = DependencyInjectionContainer()

fun main() {
  rootContainer.injectHttpContextAccessor().injectServices()
  createServer().start(wait = true)
}

fun DependencyInjectionContainer.injectServices(): DependencyInjectionContainer {
  return this.injectSingleton<IRandomProvider, RandomProvider>()
      .injectScoped<IRandomStringsService, RandomStringsService>()
}
