package com.example.webbanghang.model.response;

import java.util.List;

import com.example.webbanghang.model.entity.ProductColor;
import com.example.webbanghang.model.entity.ProductSize;

public class GetSizeAndColorResponse {
    private List<ProductSize> listSizes;
    private List<ProductColor> listColors;
    public GetSizeAndColorResponse() {
    }
    public GetSizeAndColorResponse(List<ProductSize> listSizes, List<ProductColor> listColors) {
        this.listSizes = listSizes;
        this.listColors = listColors;
    }
    public List<ProductSize> getListSizes() {
        return listSizes;
    }
    public void setListSizes(List<ProductSize> listSizes) {
        this.listSizes = listSizes;
    }
    public List<ProductColor> getListColors() {
        return listColors;
    }
    public void setListColors(List<ProductColor> listColors) {
        this.listColors = listColors;
    }
    
}
