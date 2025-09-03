package com.example.webbanghang.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.STRING)
public enum EProductSize {
    XS,
    XSS,
    S,
    M,
    ML,
    L,
    LXL,
    XL,
    XLXXL,
    XXL3XL,
    XL3;
    @Override
    public String toString() {
        switch(this) {
            case XSS: return "XS/S";
            case LXL: return "LX/L";
            case XLXXL: return "XL/XXL";
            case XXL3XL: return "XXL/3XL";
            case XL3: return "3XL";
            default: return super.toString();
        }
    }
}
