package com.example.demo.beans;
import lombok.Data;
import java.util.Date;
import java.util.List;
@Data
public class CarDTO {

    private Long id;

    private String vin;

    //添加可变类，验证是否是深复制
    private  StringBuilder name;

    private double price;

    private double totalPrice;

    private Date publishDate;

    private String color;

    private String brand;

    private List<PartDTO> partsDTOS;

    private DriverDTO driverDTO;
}
