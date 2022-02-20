package dev.westernpine.lib.mapper;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {

    public static String toString(Map<?, ?> map, String entrySeparator, String valueSeparator) {
        return map.keySet().stream()
                .map(key -> key + valueSeparator + map.get(key))
                .collect(Collectors.joining(entrySeparator));
    }

    public static Map<String, String> fromString(String mapString, String entrySeparator, String valueSeparator) {
        return Arrays.stream(mapString.split(entrySeparator))
                .map(entry -> entry.split(valueSeparator))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
    }
}
