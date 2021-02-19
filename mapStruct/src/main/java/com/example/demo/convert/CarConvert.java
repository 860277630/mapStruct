package com.example.demo.convert;
/*
 * 使用mapStruct步骤
 * 1.导入依赖
 * 2.新建一个抽象类或者接口，并标注@mapper-----mapstruct包下的注解
 * 3.写一个转换方法
 * 4.获取对象并使用
 * */

import com.example.demo.beans.CarDTO;
import com.example.demo.beans.DriverDTO;
import com.example.demo.beans.PartDTO;
import com.example.demo.service.UserService;
import com.example.demo.vo.CarVO;
import com.example.demo.vo.DriverVO;
import com.example.demo.vo.VehicleVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;


//添加componentModel=spring就可以是使工具类和Spring结合，就可以利用注入来使用
//不添加的话是不会被注入到spring中的
@Mapper(componentModel = "spring")
public abstract class CarConvert {

    public static CarConvert INSTANCE = Mappers.getMapper(CarConvert.class);//可以静态的调用
    /*
     *
     *carDTO   ---->  carVO
     * */

    @Autowired
    private UserService userService;
    //Mappings   指定映射规则   ，不同属性名之间的映射，属性之间映射格式问题，如时间到字符串格式问题
    @Mappings(
            value = {
                    @Mapping(source = "totalPrice", target = "totalPrice", numberFormat = "#.00"),//保留两位小数
                    @Mapping(source = "publishDate", target = "publishDate", dateFormat = "yyyy-MM-dd HH:mm:ss"),
                    @Mapping(target = "color",ignore = true),//忽略这个属性的映射
                    @Mapping(source = "brand",target = "brandName"),//不同名之间的映射
                    @Mapping(source = "driverDTO",target = "deiverVO")//必须有转换的方法driverDTO2DriverVO
            }
    )
    public abstract CarVO abc(CarDTO carDTO);

    @AfterMapping//表示让mapstruct在调用完自动转换方法之后，会来自动调用本方法
    public void dto2voAfter(CarDTO carDTO, @MappingTarget CarVO carVO){
        //@MappingTarget:表示传来的carVO是已经转换后的，是赋值后的
        System.out.println("========执行AfterMapping==============");
        List<PartDTO> partsDTOS = carDTO.getPartsDTOS();
        carVO.setHasPart(Objects.nonNull(partsDTOS));
    };

    /**
     * driverDTO -> DriverVO
     * @param driverDTO
     * @return
     */
    @Mapping(source = "id",target = "deiverId")
    @Mapping(source = "name",target = "fullName")
    public abstract DriverVO driverDTO2DriverVO(DriverDTO driverDTO);




    /**
     * dto2vo这个方法的批量转换
     * 这个批量转换会先在配置类里面寻找单体转换，利用配置类里的单体转换，来完成批量转换
     */
    public abstract  List<CarVO> dtos2vos(List<CarDTO> carDTOs);


    /**
     * 配置忽略mapstruct的默认映射行为，只映射那些配置了@Mapping的属性
     *@BeanMapping
     */
    @BeanMapping(ignoreByDefault = true)//禁止默认配置，只支持自定义的映射行为
    @Mapping(source = "id",target = "id")//只映射id
    @Mapping(source = "brand",target = "brandName")//只映射brand
    public abstract VehicleVO carDTO2vehicleVO(CarDTO carDTO);


    @InheritConfiguration
    //继承上一转换的配置，例如在测试的例子中，上一次的配置是屏蔽掉默认配置，值转换ID和brandName
    public abstract void updatevehicleVO(CarDTO carDTO,@MappingTarget VehicleVO vehicleVO);


    /**
     * 测试 @InheritInverseConfiguration   反向继承
     *name :指定使用哪一个方法的配置
     */

    @BeanMapping(ignoreByDefault = true)//禁止默认配置，只支持自定义的映射行为
    @InheritInverseConfiguration(name = "carDTO2vehicleVO")
    //指定继承哪一个方法的配置，继承@mapping注解且source和target是相反的
    //不会继承@BeanMapping等其他注解
    public abstract  CarDTO vehicleVO2CarDTO(VehicleVO vehicleVO);
}
