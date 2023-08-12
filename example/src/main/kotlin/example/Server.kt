package example

import container.DependencyInjectionContainer
import example.services.IRandomStringsService
import kotlinx.serialization.encodeToString as plainJsonFormatter
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun createServer(container: DependencyInjectionContainer): Http4kServer {
  val json = Json

  val app =
      routes(
          "/random-strings" bind
              Method.GET to
              {
                val scope = container.createScope()
                val service = scope.provide<IRandomStringsService>()
                Response(OK).body(json.plainJsonFormatter(service.GetRandomStrings()))
              },
          "/.*" bind Method.GET to { Response(NOT_FOUND).body("Page not found") }
      )
  val server = app.asServer(Undertow(8000))

  return server
}
