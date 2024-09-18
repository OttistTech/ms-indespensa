package com.ottistech.indespensa.api.ms_indespensa.utils;

import lombok.experimental.UtilityClass;
import java.math.BigDecimal;
import java.util.Map;

@UtilityClass
public class ParsingUtils {

    public String defaultIfEmpty(String value) {
        return (value == null || value.isEmpty()) ? null : value;
    }

    public BigDecimal parseAmountFromMetadata(Map<String, String> metadata) {
        return parseQuantityField(metadata, 0);
    }

    public String parseUnitFromMetadata(Map<String, String> metadata) {
        return parseQuantityField(metadata, 1);
    }

    private <T> T parseQuantityField(Map<String, String> metadata, int fieldIndex) {
        String quantity = metadata != null ? metadata.get("quantity") : null;

        if (quantity != null && !quantity.isEmpty()) {
            String[] quantityParts = quantity.split(", ");

            if (quantityParts.length > 0) {
                String[] amountAndUnit = quantityParts[0].split(" ");
                if (amountAndUnit.length > fieldIndex) {
                    try {
                        if (fieldIndex == 0) {
                            return (T) new BigDecimal(amountAndUnit[0]);
                        } else {
                            return (T) amountAndUnit[1];
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing quantity: " + e.getMessage());
                    }
                }
            }
        }

        return null;
    }
}
