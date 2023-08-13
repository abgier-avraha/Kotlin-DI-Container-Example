package example

import example.services.IRandomStringsService
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString as plainJsonFormatter
import kotlinx.serialization.json.Json

fun createServer(): CIOApplicationEngine {
  return embeddedServer(CIO, port = 8000, module = Application::module)
}

fun Application.module() {
  val json = Json

  routing {
    get("/random-strings") {
      val queryParams = mutableMapOf<String, List<String>>()
      call.request.queryParameters.entries().forEach({ queryParams[it.key] = it.value })
      StaticContext.latestContext = ServerContext(ServerRequest("", queryParams), null)

      // Create new scope and provide service
      val scope = rootContainer.createScope()
      val service = scope.provide<IRandomStringsService>()
      call.respondText(json.plainJsonFormatter(service.GetRandomStrings()))
    }
  }
}
