package codin.msbackendcore.shared.infrastructure.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class CommonUtils {

    public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String value) {
        if (value == null || enumClass == null) {
            return false;
        }
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equals(value));
    }

    public static String generateSlug(String input) {
        return input
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }

    public static String generateSku(String productName, String categoryName, String brandName, Map<String, Object> attributes, UUID tenantId) {
        String productPrefix = safePrefix(productName, 3);
        String categoryPrefix = safePrefix(categoryName, 2);
        String brandPrefix = safePrefix(brandName, 2);
        String tenantPrefix = safePrefix(tenantId.toString(), 2);

        StringBuilder attrCode = new StringBuilder();
        attributes.forEach((key, value) -> {
            String val = normalize(value.toString());
            attrCode.append(val.charAt(0));
        });

        String timeHash = Integer.toHexString(
                (productName + System.nanoTime()).hashCode()
        ).toUpperCase();

        String randomPart = String.format("%05d", new Random().nextInt(100000));

        StringBuilder skuBuilder = new StringBuilder();
        if (categoryPrefix != null) skuBuilder.append(categoryPrefix).append("-");
        if (brandPrefix != null) skuBuilder.append(brandPrefix).append("-");
        skuBuilder.append(productPrefix).append("-");
        skuBuilder.append(tenantPrefix).append("-");
        skuBuilder.append(attrCode).append("-");
        skuBuilder.append(timeHash, 0, 4).append("-");
        skuBuilder.append(randomPart);

        return skuBuilder.toString();
    }

    public static String generatePriceListCode(String name, UUID tenantId) {
        String namePart = safePrefix(name, 4);

        String tenantPart = safePrefix(tenantId.toString(), 4);
        String randomPart = String.format("%04d", new Random().nextInt(10000));
        return namePart + "-" + tenantPart + "-" + randomPart;
    }

    public static String toVectorString(float[] embedding) {
        if (embedding == null || embedding.length == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < embedding.length; i++) {
            sb.append(embedding[i]);
            if (i < embedding.length - 1) sb.append(',');
        }
        sb.append(']');
        return sb.toString();
    }

    public static String generateOrderNumber(int currentYear, int nextNumber) {
        return String.format("ORD-%d-%06d", currentYear, nextNumber);
    }

    public static String generateTransactionId(UUID tenantId) {
        return "TX-" + tenantId.toString().substring(0, 6) + "-" + System.currentTimeMillis();
    }

    public static String generateAmountFormat(BigDecimal amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00", symbols).format(amount);
    }

    private static String normalize(String text) {
        return text.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
    }

    private static String safePrefix(String value, int length) {
        if (value == null) return null;

        String normalized = normalize(value);
        if (normalized.isEmpty()) return null;

        return normalized.substring(0, Math.min(length, normalized.length()));
    }
}
