package example

import container.ServiceScope
import example.services.IRandomStringsService
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.request.receiveText
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.coroutines.*
import kotlinx.serialization.encodeToString as plainJsonFormatter
import kotlinx.serialization.json.Json

fun createServer(): CIOApplicationEngine {
  return embeddedServer(CIO, port = 8000, module = Application::module)
}

// TODO: imagine we generate this list using open api codegen...
// Inject codegened service implementation and their interfaces
// Reflect over codegened service interfaces and fetch their ::class
// Provide the service use the interface ::class
// Iterate over each method to get the handler
//   -> Find the associated path, request mapper and response mapper
//   -> Would it be in a base class somewhere?
//   -> Could you find it by searching for class names via reflection?
val routes =
    listOf(
        Route(
            path = "/random-strings",
            requestMapper = { _, _ -> },
            handler = { scope, _ -> scope.provide<IRandomStringsService>().GetRandomStrings() },
            responseMapper = { output -> Json.plainJsonFormatter(output) }
        )
    )

fun Application.module() {
  routing {
    routes.map { route ->
      get(route.path) {
        // Create new scope and provide service
        val scope = rootContainer.createScope()

        // Update the context
        val body = call.receiveText()
        val params = mutableMapOf<String, String?>()
        call.request
            .queryParameters
            .entries()
            .forEach({ params[it.key] = it.value.joinToString(",") })

        val contextAccessor = scope.provide<IHttpContextAccessor>()
        contextAccessor.update(ServerContext(ServerRequest(body, params), null))

        // Process request
        call.respondText(route.Process(scope, body, params))
      }
    }
  }
}

data class Route<ServiceInput, ServiceOutput>(
    val path: String,
    val requestMapper: (body: String, params: Map<String, String?>) -> ServiceInput,
    val handler: suspend (scope: ServiceScope, input: ServiceInput) -> ServiceOutput,
    val responseMapper: (output: ServiceOutput) -> String
) {
  suspend fun Process(scope: ServiceScope, body: String, params: Map<String, String?>): String {
    // Deserialise request
    val input = this.requestMapper(body, params)
    // Execute handler
    val output = this.handler(scope, input)
    // Serialise response
    return this.responseMapper(output)
  }
}
