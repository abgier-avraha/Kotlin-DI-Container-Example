package example.utils

import kotlin.random.Random

interface IRandomProvider {
  fun CreateRandomString(): String
}

class RandomProvider : IRandomProvider {

  override fun CreateRandomString(): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
    return List(Random.nextInt(0, 12)) { charPool.random() }.joinToString("")
  }
}
