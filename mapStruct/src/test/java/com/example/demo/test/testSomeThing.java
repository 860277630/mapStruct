package com.example.demo.test;


import com.example.demo.MapStructApplication;
import com.example.demo.beans.CarDTO;
import com.example.demo.beans.DriverDTO;
import com.example.demo.beans.PartDTO;
import com.example.demo.convert.CarConvert;
import com.example.demo.vo.CarVO;
import com.example.demo.vo.DriverVO;
import com.example.demo.vo.VehicleVO;


import org.junit.jupiter.api.Test;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SpringBootTest(classes = {MapStructApplication.class})
public class testSomeThing {

    //注入的方式调用工具类
    @Autowired
    private CarConvert carConvert;
    /**
     * 单实体转换  用beanUtils和最基本的方法转换
     */
    @Test
    public void testBeanUtils(){

        CarDTO carDTO = buildCarDTO();
        CarVO carVO = new CarVO();

        BeanUtils.copyProperties(carDTO,carVO);

        double totalPrice = carDTO.getTotalPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        carVO.setTotalPrice(decimalFormat.format(totalPrice));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        carVO.setPublishDate(simpleDateFormat.format(carDTO.getPublishDate()));

        carVO.setBrandName(carDTO.getBrand());

        List<PartDTO> partsDTOS = carDTO.getPartsDTOS();
        carVO.setHasPart(Objects.nonNull(partsDTOS));

        DriverVO driverVO = new DriverVO();
        DriverDTO driverDTO = carDTO.getDriverDTO();
        driverVO.setDeiverId(driverDTO.getId());
        driverVO.setFullName(driverDTO.getName());
        carVO.setDeiverVO(driverVO);


        System.out.println("=====CarVO========"+carVO.toString());
    }

    /**
     * 测试@mappings进行单体的转换，同时转换完后CarVO会执行AfterMapping
     */
    @Test
    public void test2() {
        CarDTO carDTO = buildCarDTO();
        CarVO carVO = carConvert.abc(carDTO);
        //改变可变量的值，观察是否是深复制
        StringBuilder name = carVO.getName();
        name.append("公司");
        //很明显是浅复制
        System.out.println(carVO.toString());
        System.out.println(carDTO.toString());
    }


    /**
     * 测试@mapping单独使用
     */
    @Test
    public void test2_1(){
        DriverDTO peter = new DriverDTO();
        peter.setId(12L);
        peter.setName("peter");
        DriverVO driverVO = carConvert.driverDTO2DriverVO(peter);
        System.out.println("==driverVO=="+driverVO.toString());

    }


    /**
     * 测试批量转换
     * list<CarDTO>  →   list<CarVO></></>
     */
    @Test
    public void test3() {
        CarDTO carDTO = buildCarDTO();
        List<CarDTO> carDTOLists = new ArrayList<>();
        carDTOLists.add(carDTO);
        carDTOLists.add(carDTO);
        carDTOLists.add(carDTO);
        List<CarVO> carVOList = CarConvert.INSTANCE.dtos2vos(carDTOLists);
        System.out.println(carVOList);
    }




    /**
     * 测试 @BeanMapping
     */
    @Test
    public void test4() {
        CarDTO carDTO = buildCarDTO();
        VehicleVO vehicleVO = CarConvert.INSTANCE.carDTO2vehicleVO(carDTO);
        System.out.println("========"+vehicleVO.toString());
    }

    /**
     * 测试 @InheritConfiguration  继承配置
     */
    @Test
    public void test5() {
        CarDTO carDTO = buildCarDTO();
        VehicleVO vehicleVO = CarConvert.INSTANCE.carDTO2vehicleVO(carDTO);

        System.out.println("========"+vehicleVO.toString());
        CarDTO carDTO1 = new CarDTO();
        carDTO1.setBrand("迈巴赫");
        //通过carDTO1的属性值来更新已存在的VehicleVO对象
        //继承上一转换的配置，例如在测试的例子中，上一次的配置是屏蔽掉默认配置，值转换ID和brandName
        //所以在输出结果中看不到price的值
        CarConvert.INSTANCE.updatevehicleVO(carDTO1,vehicleVO);
        System.out.println("========"+vehicleVO.toString());
    }


    /**
     * 测试 @InheritInverseConfiguration  反向继承
     */
    @Test
    public void test6() {
        VehicleVO vehicleVO = new VehicleVO();
        vehicleVO.setId(9999L);
        vehicleVO.setBrandName("别克");
        vehicleVO.setPrice(66554322d);
        CarDTO carDTO = CarConvert.INSTANCE.vehicleVO2CarDTO(vehicleVO);
        System.out.println(carDTO);
    }


    /**
     * 最基础的设置办法
     */
    @Test
    public void test() {
        System.out.println("xxxxxxxxxxxxxxxxxxxxx");
        //DTO---->VO   普通设置方法
        CarDTO carDTO = buildCarDTO();
        CarVO carVO = new CarVO();
        carVO.setId(carDTO.getId());
        carVO.setVin(carDTO.getVin());
        carVO.setPrice(carDTO.getPrice());
        double totalPrice = carDTO.getTotalPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        carVO.setTotalPrice(decimalFormat.format(totalPrice));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        carVO.setPublishDate(simpleDateFormat.format(carDTO.getPublishDate()));
        carVO.setBrandName(carDTO.getBrand());
        List<PartDTO> partsDTOS = carDTO.getPartsDTOS();
        carVO.setHasPart(Objects.nonNull(partsDTOS));
        DriverVO driverVO = new DriverVO();
        DriverDTO driverDTO = carDTO.getDriverDTO();
        driverVO.setDeiverId(driverDTO.getId());
        driverVO.setFullName(driverDTO.getName());
        carVO.setDeiverVO(driverVO);

        System.out.println(carVO.toString());

    }

    private CarDTO buildCarDTO() {

        //初始化零件
        //零件一
        PartDTO partDtO1 = new PartDTO();
        partDtO1.setPartId(1L);
        partDtO1.setPartName("多功能方向盘");
        //零件二
        PartDTO partDtO2 = new PartDTO();
        partDtO1.setPartId(2L);
        partDtO1.setPartName("智能车门");
        //填充至list中
        List<PartDTO> partDTOList = new ArrayList<>();
        partDTOList.add(partDtO1);
        partDTOList.add(partDtO2);

        //初始化司机
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(1L);
        driverDTO.setName("王二狗");

        //然后把初始化后的全部填充至实体中
        CarDTO carDTO = new CarDTO();
        carDTO.setId(330L);
        carDTO.setVin("vin123456789");
        carDTO.setName(new StringBuilder("可口可乐"));
        carDTO.setPrice(123789.126d);
        carDTO.setTotalPrice(143789.126d);
        carDTO.setPublishDate(new Date());
        carDTO.setColor("白色");
        carDTO.setBrand("大众");
        carDTO.setPartsDTOS(partDTOList);
        carDTO.setDriverDTO(driverDTO);

        return carDTO;
    }
}
