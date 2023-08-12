package container

class DefaultScope {
  companion object {
    val scope = DependencyInjectionContainer().createScope()
  }
}
