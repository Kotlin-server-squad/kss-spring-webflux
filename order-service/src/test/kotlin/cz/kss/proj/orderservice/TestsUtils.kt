package cz.kss.proj.orderservice

import cz.kss.proj.orderservice.model.BaseEntity
import org.assertj.core.api.Assertions.assertThat

fun<T : BaseEntity<I>, I> verifySavedEntity(expected: T, actual: T) {
    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields("createdAt", "updatedAt")
        .isEqualTo(expected)
    assertThat(actual.createdAt).isNotNull
    assertThat(actual.updatedAt).isNotNull
}