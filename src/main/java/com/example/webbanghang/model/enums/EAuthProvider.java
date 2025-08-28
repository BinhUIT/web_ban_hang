package com.example.webbanghang.model.enums;

public enum EAuthProvider implements IEnumCode {
    DEFAULT(1),
    GOOGLE(2); 
    private final int code;
    EAuthProvider(int code) {
        this.code=code;
    }
    @Override
    public int getCode() {
        return this.code;
    }
    
    public Object[] getValues() {
        return EAuthProvider.values();
    }
}
