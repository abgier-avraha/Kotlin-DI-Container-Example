package example.mocks

import example.utils.IRandomProvider

class MockRandomProvider : IRandomProvider {
  private val value: String

  constructor(value: String) {
    this.value = value
  }

  override fun CreateRandomString(): String {
    return this.value
  }
}
