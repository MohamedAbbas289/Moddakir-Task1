package com.example.domain.exceptions


class ParsingException(
    e: Throwable
) : RuntimeException("The server returned an unreadable response", e)
