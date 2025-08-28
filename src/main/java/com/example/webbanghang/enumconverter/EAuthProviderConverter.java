package com.example.webbanghang.enumconverter;

import java.util.Arrays;

import com.example.webbanghang.model.enums.EAuthProvider;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

//@Converter(autoApply = true)
public class EAuthProviderConverter /*implements AttributeConverter<EAuthProvider,Integer>*/ {

    /*@Override
    public Integer convertToDatabaseColumn(EAuthProvider arg0) {
        return arg0==null?null:arg0.getCode();
    }

    @Override
    public EAuthProvider convertToEntityAttribute(Integer code) {
        return code==null?null:Arrays.stream(EAuthProvider.values()).filter(p->p.getCode()==code).findFirst().orElse(null);
    }*/

}
