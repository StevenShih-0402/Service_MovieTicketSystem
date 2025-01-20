package com.ctbcbank.navi.mid.campaign.management.utils;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import lombok.experimental.UtilityClass;
import org.apache.commons.csv.CSVRecord;

import java.util.Arrays;

@UtilityClass
public class CsvUtils {
    private final char DOUBLE_QUOTES = '\"';

    public String addDoubleQuotesSurrounding(String value) {
        return ObjectUtils.isEmpty(value) ? value : DOUBLE_QUOTES + value + DOUBLE_QUOTES;
    }

    public String removeDoubleQuotesSurrounding(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return value;
        }

        if (value.charAt(0) == DOUBLE_QUOTES && value.charAt(value.length() - 1) == DOUBLE_QUOTES) {
            value = value.substring(1, value.length() - 1);
        }

        return value;
    }

    public String[] getCsvHeaderNames(Class<? extends BaseEnums> csvHeaderEnumClass, boolean headerWithDoubleQuotes) {
        return Arrays.stream(csvHeaderEnumClass.getEnumConstants())
                .map(header -> headerWithDoubleQuotes ? addDoubleQuotesSurrounding(header.getCode()) : header.getCode())
                .toArray(String[]::new);
    }

    public String getCsvColumnValueByCsvHeader(CSVRecord csvRecord, BaseEnums csvHeader, boolean headerWithDoubleQuotes) {
        String csvHeaderName = headerWithDoubleQuotes ? getCsvHeaderName(csvHeader) : csvHeader.getCode();

        return getCsvColumnValueByCsvHeaderName(csvRecord, csvHeaderName);
    }

    private String getCsvHeaderName(BaseEnums csvHeaderEnum) {
        return addDoubleQuotesSurrounding(csvHeaderEnum.getCode());
    }

    private String getCsvColumnValueByCsvHeaderName(CSVRecord csvRecord, String headerName) {
        return removeDoubleQuotesSurrounding(csvRecord.get(headerName));
    }

}
