package example.services

import example.IHttpContextAccessor
import example.utils.IRandomProvider

interface IRandomStringsService {
  suspend fun GetRandomStrings(): List<String>
}

class RandomStringsService : IRandomStringsService {

  private val randomProvider: IRandomProvider
  private val httpContextAccessor: IHttpContextAccessor

  constructor(randomProvider: IRandomProvider, httpContextAccessor: IHttpContextAccessor) {
    this.randomProvider = randomProvider
    this.httpContextAccessor = httpContextAccessor
  }

  // TODO: use async interfaces
  override suspend fun GetRandomStrings(): List<String> {
    // TODO: create logger middleware
    println(httpContextAccessor.context.request.body)
    println(httpContextAccessor.context.request.paramaters)

    return listOf(
        this.randomProvider.CreateRandomString(),
        this.randomProvider.CreateRandomString(),
        this.randomProvider.CreateRandomString()
    )
  }
}
