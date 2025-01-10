package com.ctbcbank.navi.mid.campaign.management.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.JsonUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigInteger;
import java.util.List;

@Converter
public class InvolvedPartyNoListConverter implements AttributeConverter<List<BigInteger>, String> {

    @Override
    public String convertToDatabaseColumn(List<BigInteger> involvedPartyNo) {
        try {
            // 將 List<BigInteger> 轉換為 JSON 字串
            return JsonUtils.toJson(involvedPartyNo);
        } catch (Exception e) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, "Error converting list to JSON", e);
        }
    }

    @Override
    public List<BigInteger> convertToEntityAttribute(String involvedPartyNo) {
        if (StringUtils.isBlank(involvedPartyNo)) {
            return null;
        }
        try {
            // 將 JSON 字串轉換回 List<BigInteger>
            return JsonUtils.toObject(
                    involvedPartyNo, new TypeReference<>() {
                    }
            );
        } catch (Exception e) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, "Error converting JSON to list", e);
        }
    }
}
