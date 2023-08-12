package example.services

import example.utils.IRandomProvider

interface IRandomStringsService {
  fun GetRandomStrings(): List<String>
}

class RandomStringsService : IRandomStringsService {

  private val randomProvider: IRandomProvider

  constructor(randomProvider: IRandomProvider) {
    this.randomProvider = randomProvider
  }

  override fun GetRandomStrings(): List<String> {
    return listOf(
        this.randomProvider.CreateRandomString(),
        this.randomProvider.CreateRandomString(),
        this.randomProvider.CreateRandomString()
    )
  }
}
