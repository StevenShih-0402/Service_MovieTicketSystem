package com.ctbcbank.navi.mid.campaign.management.utils;

import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class FieldValidatorUtils {

    /**
     * 判斷指定的欄位是否為 null 或 empty
     *
     * @param object
     * @param fields
     * @return 檢查指定欄位皆為 null 或 empty 時返回 true，否則返回 false
     */
    public static boolean areFieldNullOrEmpty(Object object, String... fields) {
        if (ObjectUtils.isEmpty(object)) return true;
        return Stream.of(fields).allMatch(fieldName -> {
            try {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(object);
                return ObjectUtils.isEmpty(value);
            } catch (Exception ex) {
                throw new NaviException(FabricResponseCode.INVALID_DATA, "Check AreFieldNullOrEmpty Exception: " + ex);
            }
        });
    }
}
