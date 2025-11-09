package com.flowlog.converter;

import com.flowlog.enums.RoleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false) // 안전하게 필드에만 명시 적용
public class RoleTypeConverter implements AttributeConverter<RoleType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RoleType attribute) {
        return attribute == null ? null : attribute.getCode(); // Enum -> DB(숫자)
    }

    @Override
    public RoleType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : RoleType.fromCode(dbData); // DB(숫자) -> Enum
    }
}
