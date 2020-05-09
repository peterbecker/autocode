package com.github.peterbecker;

/**
 * Supported types for properties. XML must use camelCase.
 */
public enum Type {
    /**
     * A 32 bit signed integer.
     */
    INT32,
    /**
     * A 64 bit signed integer.
     */
    INT64,
    /**
     * An arbitrary length UTF8 string.
     */
    STRING,
    /**
     * A date without time.
     */
    DATE
}
