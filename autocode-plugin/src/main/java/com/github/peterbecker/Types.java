package com.github.peterbecker;

import java.util.Map;

public class Types {
    public static final Map<Type, String> JAVA = Map.of(
            Type.INT32, "int",
            Type.INT64, "long",
            Type.STRING, "String",
            Type.DATE, "java.time.LocalDate"
    );

    public static final Map<Type, String> TYPE_SCRIPT = Map.of(
            Type.INT32, "Number",
            Type.INT64, "Number",
            Type.STRING, "String",
            Type.DATE, "Date"
    );
}
