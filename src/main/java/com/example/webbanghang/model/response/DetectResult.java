package com.example.webbanghang.model.response;

public class DetectResult {
    private int age;
    private boolean gender;
    private String shape;
    private String body_shape;

    public DetectResult(int age, String body_shape, boolean gender, String shape) {
        this.age = age;
        this.body_shape = body_shape;
        this.gender = gender;
        this.shape = shape;
    }

    public DetectResult() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getBody_shape() {
        return body_shape;
    }

    public void setBody_shape(String body_shape) {
        this.body_shape = body_shape;
    }
    
    
}
