package example

import container.DependencyInjectionContainer
import example.services.IRandomStringsService
import example.services.RandomStringsService
import example.utils.IRandomProvider
import example.utils.RandomProvider

fun main() {
  // Create and populate container
  val container = DependencyInjectionContainer().injectServices()

  // Start server
  val server = createServer(container)
  server.start()
}

fun DependencyInjectionContainer.injectServices(): DependencyInjectionContainer {
  return this.injectSingleton<IRandomProvider, RandomProvider>()
      .injectScoped<IRandomStringsService, RandomStringsService>()
}
