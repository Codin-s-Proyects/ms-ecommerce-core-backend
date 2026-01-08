package codin.msbackendcore.search.application.internal.builders;

import java.util.Map;

public class EmbeddingTextBuilder {
    public static String buildTextOnly(String productName, String categoryName, String brandName, String productDescription,
                             String variantName, Map<String, Object> variantAttributes) {

        StringBuilder sb = new StringBuilder();

        append(sb, categoryName);
        append(sb, brandName);
        append(sb, productName);
        append(sb, productDescription);
        append(sb, variantName);

        if (variantAttributes != null && !variantAttributes.isEmpty()) {
            sb.append(" Attributes: ").append(variantAttributes);
        }

        return sb.toString().trim();
    }

    public static String buildFromAiContext(String aiContext) {
        StringBuilder sb = new StringBuilder();

        append(sb, aiContext);

        return sb.toString().trim();
    }

    public static String buildComposite(
            String textOnly,
            String imageText
    ) {
        return (textOnly + " " + imageText).trim();
    }

    private static void append(StringBuilder sb, String value) {
        if (value != null) {
            sb.append(value).append(" ");
        }
    }


}
