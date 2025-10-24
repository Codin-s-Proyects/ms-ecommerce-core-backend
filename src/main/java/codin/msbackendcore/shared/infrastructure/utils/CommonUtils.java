package codin.msbackendcore.shared.infrastructure.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class CommonUtils {

    public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String value) {
        if (value == null || enumClass == null) {
            return false;
        }
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }

    public static String generateSlug(String input) {
        return input
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }

    public static String generateSku(String productName, Map<String, Object> attributes, UUID tenantId) {
        String productPrefix = normalize(productName).substring(0, 3).toUpperCase();
        String tenantPrefix = normalize(tenantId.toString()).substring(0, 2).toUpperCase();

        StringBuilder attrCode = new StringBuilder();
        attributes.forEach((key, value) -> {
            String val = normalize(value.toString());
            attrCode.append(val.charAt(0));
        });

        String randomPart = String.format("%03d", new Random().nextInt(999));
        return String.format("%s-%s-%s-%s", productPrefix, tenantPrefix, attrCode, randomPart);
    }

    private static String normalize(String text) {
        return text.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
    }
}
