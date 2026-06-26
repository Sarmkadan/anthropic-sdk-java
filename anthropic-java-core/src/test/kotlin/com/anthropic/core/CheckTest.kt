package com.anthropic.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CheckTest {

    @Test
    fun `checkRequired(name, condition) passes when true`() {
        checkRequired("test", true)
    }

    @Test
    fun `checkRequired(name, condition) throws when false`() {
        val exception = assertThrows<IllegalStateException> {
            checkRequired("myField", false)
        }
        assertThat(exception.message).contains("`myField` is required, but was not set")
    }

    @Test
    fun `checkRequired(name, value) returns value when not null`() {
        val result = checkRequired("test", "value")
        assertThat(result).isEqualTo("value")
    }

    @Test
    fun `checkRequired(name, value) throws when null`() {
        assertThrows<IllegalStateException> {
            checkRequired<String>("myField", null)
        }
    }

    @Test
    fun `checkMinLength passes when length is sufficient`() {
        checkMinLength("test", "value", 5)
    }

    @Test
    fun `checkMinLength throws when length is insufficient`() {
        val exception = assertThrows<IllegalStateException> {
            checkMinLength("myField", "val", 5)
        }
        assertThat(exception.message).contains("`myField` must have at least length 5, but was 3")
    }

    @Test
    fun `checkMaxLength passes when length is sufficient`() {
        checkMaxLength("test", "value", 5)
    }

    @Test
    fun `checkMaxLength throws when length is too long`() {
        val exception = assertThrows<IllegalStateException> {
            checkMaxLength("myField", "value", 3)
        }
        assertThat(exception.message).contains("`myField` must have at most length 3, but was 5")
    }
}
