package example

import container.DependencyInjectionContainer

interface IHttpContextAccessor {
  var context: ServerContext

  fun update(context: ServerContext) {
    this.context = context
  }
}

class HttpContextAccessor : IHttpContextAccessor {
  override var context: ServerContext

  constructor(context: ServerContext) {
    this.context = context
  }
}

fun DependencyInjectionContainer.injectHttpContextAccessor(): DependencyInjectionContainer {
  return this.injectScoped<IHttpContextAccessor>({
    HttpContextAccessor(ServerContext(ServerRequest("", mapOf()), null))
  })
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

data class ServerRequest(val body: String, val paramaters: Map<String, String?>) {}

data class ServerContext(val request: ServerRequest, val user: Principal?) {}
