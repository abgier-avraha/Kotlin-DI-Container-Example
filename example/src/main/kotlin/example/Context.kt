package example

import container.DependencyInjectionContainer

interface IHttpContextAccessor {
  val context: ServerContext
}

class HttpContextAccessor : IHttpContextAccessor {
  override val context: ServerContext

  constructor(context: ServerContext) {
    this.context = context
  }
}

fun DependencyInjectionContainer.injectHttpContextAccessor(): DependencyInjectionContainer {
  return this.injectScoped<IHttpContextAccessor>({
    HttpContextAccessor(StaticContext.latestContext)
  })
}

// TODO: This is ugly and potentially not safe
// This just holds a reference to the latest http context for the injector function
// It's not safe because multiple request handlers could modify this static var at the same time???
// Brainstorm ways to support mulithreading with 1 request per thread
class StaticContext {
  companion object {
    var latestContext = ServerContext(ServerRequest("", mapOf()), null)
  }
}

enum class Claims(val claimString: String) {
  SUB("sub"),
  NAME("name")
}

data class Identity(val claims: Map<String, String?>) {
  val sub = this.claims[Claims.SUB.claimString]
  val name = this.claims[Claims.NAME.claimString]
}

data class Principal(val identity: Identity) {}

data class ServerRequest(val body: String, val paramaters: Map<String, List<String>>) {}

data class ServerContext(val request: ServerRequest, val user: Principal?) {}
