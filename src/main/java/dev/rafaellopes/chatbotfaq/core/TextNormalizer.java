package dev.rafaellopes.chatbotfaq.core;

import java.text.Normalizer;

/**
 * Normalizes text for consistent matching across different platforms and encodings.
 * Performs lowercase conversion, accent removal, and whitespace normalization.
 */
public final class TextNormalizer {

    private TextNormalizer() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Normalizes text by converting to lowercase, removing accents, and normalizing whitespace.
     * This ensures consistent matching regardless of platform encoding or user input variations.
     *
     * @param text the text to normalize
     * @return normalized text, or null if input is null
     */
    public static String normalize(String text) {
        if (text == null) {
            return null;
        }

        // Convert to lowercase
        String normalized = text.toLowerCase();

        // Remove accents using Unicode normalization
        // NFD = Canonical Decomposition (separates base char from accent)
        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD);

        // Remove combining diacritical marks (accents)
        normalized = normalized.replaceAll("\\p{M}", "");

        // Normalize whitespace (replace multiple spaces with single space and trim)
        normalized = normalized.trim().replaceAll("\\s+", " ");

        return normalized;
    }
}
