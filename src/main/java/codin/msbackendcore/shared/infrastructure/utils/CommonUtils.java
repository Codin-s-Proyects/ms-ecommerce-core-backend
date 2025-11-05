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
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }

    public static String generateSlug(String input) {
        return input
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }

    public static String generateSku(String productName, String categoryName, String brandName, Map<String, Object> attributes, UUID tenantId) {
        String productPrefix = normalize(productName).substring(0, 3).toUpperCase();
        String categoryPrefix = (categoryName != null && !categoryName.isEmpty())
                ? normalize(categoryName).substring(0, 2).toUpperCase()
                : null;
        String brandPrefix = (brandName != null && !brandName.isEmpty())
                ? normalize(brandName).substring(0, 2).toUpperCase()
                : null;
        String tenantPrefix = normalize(tenantId.toString()).substring(0, 2).toUpperCase();

        StringBuilder attrCode = new StringBuilder();
        attributes.forEach((key, value) -> {
            String val = normalize(value.toString());
            attrCode.append(val.charAt(0));
        });

        String randomPart = String.format("%03d", new Random().nextInt(999));

        StringBuilder skuBuilder = new StringBuilder();
        if (categoryPrefix != null) skuBuilder.append(categoryPrefix).append("-");
        if (brandPrefix != null) skuBuilder.append(brandPrefix).append("-");
        skuBuilder.append(productPrefix).append("-");
        skuBuilder.append(tenantPrefix).append("-");
        skuBuilder.append(attrCode).append("-");
        skuBuilder.append(randomPart);

        return skuBuilder.toString();
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
}
