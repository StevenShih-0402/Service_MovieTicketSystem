package com.ctbcbank.navi.mid.campaign.management.enums.csvHeader;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.web.enums.ApiResponseCode;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public interface ValidateCsvHeader extends BaseEnums {

    String getAttribute();

    boolean isRequired();

    static boolean checkAttributeRequired(Class<? extends Enum<?>> enumClass, String attribute) {
        // 取得enumValues
        for (Enum<?> constant : enumClass.getEnumConstants()) {
            // 檢查是否實作了 CsvHeaderValidation
            if (constant instanceof ValidateCsvHeader header) {
                if (header.getAttribute().equals(attribute)) {
                    return header.isRequired();
                }
            }
        }
        return false;
    }

    static void checkAttributeValid(Class<? extends Enum<? extends ValidateCsvHeader>> enumClass, Class<?> objClass, Object obj) throws IllegalAccessException {
        String error = "";
        for (Field field : objClass.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (checkAttributeRequired(enumClass, fieldName) && (field.get(obj) == null || field.get(obj).toString().isEmpty())) {
                error = StringUtils.isBlank(error) ? fieldName : String.join(",", error, fieldName);
            }
        }
        if (StringUtils.isNotBlank(error)) {
            throw new NaviException(ApiResponseCode.INVALID_DATA, String.format("%s%s", error, " is required but is missing or empty."));
        }
    }

    static Map<String, Object> getAttributeValue(Class<? extends Enum<? extends ValidateCsvHeader>> enumClass, Class<?> objClass, Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        for (Field field : objClass.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(obj);
            // 取得 enumValues
            for (Enum<?> constant : enumClass.getEnumConstants()) {
                // 檢查是否實作了 CsvHeaderValidation
                if (constant instanceof ValidateCsvHeader header) {
                    String key = ((ValidateCsvHeader) constant).getCode();
                    if (header.getAttribute().equals(fieldName)) {
                        map.computeIfAbsent(key, (k) -> fieldValue);
                    }
                }
            }
        }
        return map;
    }
}
