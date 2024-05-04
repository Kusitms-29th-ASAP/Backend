package com.asap.asapbackend.fixture

import com.navercorp.fixturemonkey.ArbitraryBuilder
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder

inline fun <reified T> generateFixture(propertyBuilder: (ArbitraryBuilder<T>) -> ArbitraryBuilder<T>): T {
    return FixtureMonkey.builder()
        .defaultNotNull(true)
        .plugin(KotlinPlugin())
        .build()
        .giveMeBuilder<T>()
        .apply { propertyBuilder(this) }
        .sample()
}

inline fun <reified T> generateFixture(): T {
    return generateFixture { it }
}

inline fun <reified T> generateBasicTypeFixture(length: Int): T {
    return FixtureMonkey.builder()
        .defaultNotNull(true)
        .plugin(KotlinPlugin())
        .build()
        .giveMeBuilder<T>()
        .sample()
}
