package example.services

import example.IHttpContextAccessor
import example.utils.IRandomProvider

interface IRandomStringsService {
  fun GetRandomStrings(): List<String>
}

class RandomStringsService : IRandomStringsService {

  private val randomProvider: IRandomProvider
  private val httpContextAccessor: IHttpContextAccessor

  constructor(randomProvider: IRandomProvider, httpContextAccessor: IHttpContextAccessor) {
    this.randomProvider = randomProvider
    this.httpContextAccessor = httpContextAccessor
  }

  override fun GetRandomStrings(): List<String> {
    println(httpContextAccessor.context.request.body)

    return listOf(
        this.randomProvider.CreateRandomString(),
        this.randomProvider.CreateRandomString(),
        this.randomProvider.CreateRandomString()
    )
  }
}
