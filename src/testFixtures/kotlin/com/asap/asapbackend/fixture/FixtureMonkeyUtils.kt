package com.asap.asapbackend.fixture

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.*

inline fun <reified T> generateFixture(): T {
    return FixtureMonkey.builder()
        .defaultNotNull(true)
        .plugin(KotlinPlugin())
        .build()
        .giveMeOne()
}

inline fun <reified T> generateBasicTypeFixture(length: Int): T {
    return FixtureMonkey.builder()
        .defaultNotNull(true)
        .plugin(KotlinPlugin())
        .build()
        .giveMeBuilder<T>()
        .sample()
}
