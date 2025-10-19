package codin.msbackendcore.shared.infrastructure.utils;

import java.util.Arrays;

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
}
