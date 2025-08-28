package com.example.webbanghang.enumconverter;

import java.util.Arrays;

import com.example.webbanghang.model.enums.IEnumCode;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EnumConverter<T extends Enum<T>& IEnumCode> implements AttributeConverter<T, Integer> {
    private final Class<T> enumClass;
    public EnumConverter(Class<T> enumClass) {
        this.enumClass= enumClass;
    }
    @Override
    public Integer convertToDatabaseColumn(T arg0) {
        if(arg0==null) return null;
        return arg0.getCode();
    }

    //@SuppressWarnings("unchecked")
    @Override
    public T convertToEntityAttribute(Integer arg0) {
        /*if(arg0==null) return null;
        if(enumClass.isEnum()) {
            Object[] constants = enumClass.getEnumConstants();
             Object result = arg0 == null ? null : Arrays.stream(constants)
            .filter(r -> {
                if(r instanceof IEnumCode rCode) {
                    if(rCode.getCode()==arg0) {
                        return true;
                    } 
                    
                }
                return false;
            })
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
            if(result instanceof IEnumCode res) {
                return (T) res;
            }
        }
        throw new UnsupportedOperationException("Unimplemented method 'convertToEntityAttribute'");*/ 
        if (arg0 == null) return null;

        return Arrays.stream(enumClass.getEnumConstants())
                     .filter(e -> e.getCode() == arg0)
                     .findFirst()
                     .orElseThrow(() -> 
                         new IllegalArgumentException("Unknown code " + arg0 + " for enum " + enumClass.getName())
                     );
    }
    

}
