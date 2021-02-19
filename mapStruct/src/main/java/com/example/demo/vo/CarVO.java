package com.example.demo.vo;


import lombok.*;

@Data
public class CarVO {

    private Long id;

    private String vin;

    //添加可变类，验证是否是深复制
    private StringBuilder name;

    private Double price;

    private String totalPrice;

    private String publishDate;

    private String color;

    private String brandName;

    private Boolean hasPart;

    private DriverVO deiverVO;

}
