package example.utils

import container.DependencyInjectionContainer
import example.injectHttpContextAccessor

inline fun <reified T> DependencyInjectionContainer.createClient(): T where T : Any {
  val scope = this.createScope()
  this.injectHttpContextAccessor()
  return scope.provide<T>()
}
