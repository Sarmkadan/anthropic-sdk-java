package com.anthropic.exceptions

class ValidationException(message: String? = null, cause: Throwable? = null) : AnthropicSdkJavaException(message, cause)
