package com.anthropic.exceptions

class ConfigurationException(message: String? = null, cause: Throwable? = null) : AnthropicSdkJavaException(message, cause)
