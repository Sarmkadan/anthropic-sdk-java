package com.anthropic.exceptions

import com.anthropic.errors.AnthropicException

open class AnthropicSdkJavaException(message: String? = null, cause: Throwable? = null) : AnthropicException(message, cause)
