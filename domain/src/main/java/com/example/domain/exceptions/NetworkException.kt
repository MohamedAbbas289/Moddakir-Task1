package com.example.domain.exceptions

import java.io.IOException

class NetworkException(cause: Throwable) : IOException(
    "Unable to reach the server. Check your connection and try again.",
    cause
)
