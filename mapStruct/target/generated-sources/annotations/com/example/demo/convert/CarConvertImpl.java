package com.example.demo.convert;

import com.example.demo.beans.CarDTO;
import com.example.demo.beans.DriverDTO;
import com.example.demo.vo.CarVO;
import com.example.demo.vo.DriverVO;
import com.example.demo.vo.VehicleVO;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-20T03:20:55+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class CarConvertImpl extends CarConvert {

    @Override
    public CarVO abc(CarDTO carDTO) {
        if ( carDTO == null ) {
            return null;
        }

        CarVO carVO = new CarVO();

        if ( carDTO.getPublishDate() != null ) {
            carVO.setPublishDate( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( carDTO.getPublishDate() ) );
        }
        carVO.setBrandName( carDTO.getBrand() );
        carVO.setDeiverVO( driverDTO2DriverVO( carDTO.getDriverDTO() ) );
        carVO.setTotalPrice( new DecimalFormat( "#.00" ).format( carDTO.getTotalPrice() ) );
        carVO.setId( carDTO.getId() );
        carVO.setVin( carDTO.getVin() );
        carVO.setName( carDTO.getName() );
        carVO.setPrice( carDTO.getPrice() );

        dto2voAfter( carDTO, carVO );

        return carVO;
    }

    @Override
    public DriverVO driverDTO2DriverVO(DriverDTO driverDTO) {
        if ( driverDTO == null ) {
            return null;
        }

        DriverVO driverVO = new DriverVO();

        driverVO.setDeiverId( driverDTO.getId() );
        driverVO.setFullName( driverDTO.getName() );

        return driverVO;
    }

    @Override
    public List<CarVO> dtos2vos(List<CarDTO> carDTOs) {
        if ( carDTOs == null ) {
            return null;
        }

        List<CarVO> list = new ArrayList<CarVO>( carDTOs.size() );
        for ( CarDTO carDTO : carDTOs ) {
            list.add( abc( carDTO ) );
        }

        return list;
    }

    @Override
    public VehicleVO carDTO2vehicleVO(CarDTO carDTO) {
        if ( carDTO == null ) {
            return null;
        }

        VehicleVO vehicleVO = new VehicleVO();

        vehicleVO.setBrandName( carDTO.getBrand() );
        vehicleVO.setId( carDTO.getId() );

        return vehicleVO;
    }

    @Override
    public void updatevehicleVO(CarDTO carDTO, VehicleVO vehicleVO) {
        if ( carDTO == null ) {
            return;
        }

        vehicleVO.setBrandName( carDTO.getBrand() );
        vehicleVO.setId( carDTO.getId() );
    }

    @Override
    public CarDTO vehicleVO2CarDTO(VehicleVO vehicleVO) {
        if ( vehicleVO == null ) {
            return null;
        }

        CarDTO carDTO = new CarDTO();

        carDTO.setId( vehicleVO.getId() );
        carDTO.setBrand( vehicleVO.getBrandName() );

        return carDTO;
    }
}
