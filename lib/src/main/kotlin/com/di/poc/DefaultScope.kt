package com.di.poc

class DefaultScope
{
  companion object {
    val scope = DependencyInjectionContainer().createScope()
  }
}