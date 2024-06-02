package org.example.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class MovieTitleUtil {

    public static String sanitizeTitle(String title) {
        // Normalizar el título para eliminar acentos y caracteres especiales
        String normalizedTitle = Normalizer.normalize(title, Normalizer.Form.NFD);
        // Remover caracteres no válidos para un nombre de archivo
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String sanitizedTitle = pattern.matcher(normalizedTitle).replaceAll("");
        return sanitizedTitle.replaceAll("[^a-zA-Z0-9]", "_");
    }

    public static  String getFileExtension(String originalFileName) {
        if (originalFileName != null && originalFileName.contains(".")) {
            return originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return ".jpg"; // Valor predeterminado si no se puede determinar la extensión
    }
}
