package com.rewinddb.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewinddb.common.exception.InternalServerException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Small JSON helpers shared across services. Kept as static utilities (no Spring
 * bean) since callers already have an injected {@link ObjectMapper} to pass in.
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    /**
     * Produces a JSON object describing only the fields that changed between two
     * row snapshots, in the shape {@code { "field": { "from": ..., "to": ... } } }.
     * Either snapshot may be {@code null}/blank, which is treated as an empty row.
     */
    public static String diff(String beforeJson, String afterJson, ObjectMapper objectMapper) {
        Map<String, Object> before = toMap(beforeJson, objectMapper);
        Map<String, Object> after = toMap(afterJson, objectMapper);

        Set<String> allFields = new TreeSet<>();
        allFields.addAll(before.keySet());
        allFields.addAll(after.keySet());

        Map<String, Object> changes = new LinkedHashMap<>();
        for (String field : allFields) {
            Object oldValue = before.get(field);
            Object newValue = after.get(field);
            if (!Objects.equals(oldValue, newValue)) {
                Map<String, Object> change = new LinkedHashMap<>();
                change.put("from", oldValue);
                change.put("to", newValue);
                changes.put(field, change);
            }
        }

        try {
            return objectMapper.writeValueAsString(changes);
        } catch (JsonProcessingException ex) {
            throw new InternalServerException("Failed to compute version diff");
        }
    }

    private static Map<String, Object> toMap(String json, ObjectMapper objectMapper) {
        if (!StringUtils.hasText(json)) {
            return Map.of();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException ex) {
            throw new InternalServerException("Failed to parse stored row snapshot");
        }
    }
}
