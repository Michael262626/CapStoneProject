package com.africa.semiclon.capStoneProject.constants;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CustomBigDecimalSerializer extends JsonSerializer<BigDecimal> {

    // Optional: Use a DecimalFormat for custom precision
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.00");

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toPlainString());
    }
}
