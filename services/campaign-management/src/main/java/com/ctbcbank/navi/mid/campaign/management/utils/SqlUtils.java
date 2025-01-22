package com.ctbcbank.navi.mid.campaign.management.utils;

import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class SqlUtils {

    public static <T> StringBuilder composeSql(List<T> entities, List<String> selectFields, String fromTableName, String prefixFieldName, Map<String, Object> sqlParams) throws Exception {

        if (sqlParams == null) {
            sqlParams = new HashMap<>();
        }
        String tableName = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(fromTableName)) {
            tableName = fromTableName;
        } else {
            if (CollectionUtils.isEmpty(entities)) {
                throw new NaviException(FabricResponseCode.INVALID_DATA, "Entities is empty.");
            }
            Class<?> clazz = entities.get(0).getClass();
            if (!clazz.isAnnotationPresent(Entity.class)) {
                throw new NaviException(FabricResponseCode.INVALID_DATA, "Class is not a valid Entity: " + clazz.getName());
            }
            tableName = clazz.getAnnotation(Table.class).name();
        }

        if (StringUtils.isBlank(tableName)) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, "Table name is blank.");
        }

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ");
        if (CollectionUtils.isEmpty(selectFields)) {
            sqlBuilder.append(" * ");
        } else {
            StringJoiner selectFieldsSJ = new StringJoiner(", ");
            for (String selectField : selectFields) {
                selectFieldsSJ.add(selectField);
            }
            sqlBuilder.append(selectFieldsSJ.toString());
        }
        sqlBuilder.append(" FROM ").append(tableName);


        StringJoiner whereClause = new StringJoiner(" AND ");
        if (!CollectionUtils.isEmpty(entities)) {
            Class<?> clazz = entities.get(0).getClass();
            int entityIndex = 0;
            for (T entity : entities) {
                StringJoiner entityClause = new StringJoiner(" AND ");
                for (Field field : clazz.getDeclaredFields()) {
                    // 允許訪問 private 欄位
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if (ObjectUtils.isNotEmpty(value)) {
                        String columnName;
                        if (field.isAnnotationPresent(Column.class)) {
                            columnName = field.getAnnotation(Column.class).name();
                            String paramName = prefixFieldName + "_" + field.getName() + "_" + entityIndex;
                            entityClause.add(columnName + " = :" + paramName);
                            sqlParams.put(paramName, value);
                        }
                    }
                }

                if (entityClause.length() > 0) {
                    whereClause.add("(" + entityClause.toString() + ")");
                }
                entityIndex++;
            }
        }


        // 沒有任何條件，回傳無條件查詢
        if (whereClause.length() == 0) {
            return sqlBuilder;
        }

        sqlBuilder.append(" WHERE ");
        return sqlBuilder.append(whereClause.toString());
    }

}
