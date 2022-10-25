package edu.miu.property.controller;

import edu.miu.property.dto.PropertyRequest;
import edu.miu.property.dto.ReservationResponse;
import edu.miu.property.dto.ReservationStatusUpdate;
import edu.miu.property.dto.UpdateDto;
import edu.miu.property.model.Address;
import edu.miu.property.model.Property;
import edu.miu.property.service.PropertyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@CacheConfig(cacheNames = {"property"})
@RestController
@RequestMapping("/api/property")
@Slf4j
public class PropertyController {


    @Autowired
    private PropertyServiceImpl propertyService;


    @CachePut(value = "property",key = "#reservationStatusUpdate.id")

    @PostMapping("/updateStatus")
    public ReservationResponse update(@RequestBody ReservationStatusUpdate reservationStatusUpdate){
        log.info("property status updated!");
        return propertyService.update(reservationStatusUpdate);
    }

    @PostMapping("/updateProperty")
    @CachePut(value = "property",key = "#updateDto.id")
    public ReservationResponse updateProperty(@RequestBody UpdateDto updateDto){
        log.info("property updated!");
        return propertyService.updateProperty(updateDto);
    }

    @PostMapping(value = "/create",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @CachePut(value = "propertyByEmail",key = "#userEmail")
    public Property saveProperty(
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("propertyName") String propertyName,
            @RequestPart("title") String title,
            @RequestPart("price") String price,
            @RequestPart("status") String status,
            @RequestPart("userEmail") String userEmail,
            @RequestPart("latitude") String latitude,
            @RequestPart("longitude") String longitude

            //Address
            ,@RequestPart("city") String city
            ,@RequestPart(value = "state", required = false) String state
            ,@RequestPart(value = "street_number", required = false) String street_number
            ,@RequestPart(value = "zip_code", required = false) String zip_code
    )throws Exception {
        Address address = new Address(city,state,zip_code,street_number);
        PropertyRequest propertyRequest = new PropertyRequest(propertyName,title,Double.parseDouble(price)
                ,Boolean. parseBoolean(status),address,userEmail);
        log.info("propety ${} added!",propertyName);
        return propertyService.add(propertyRequest,images,Double.parseDouble(latitude),Double.parseDouble(longitude));
    }




    @Cacheable(value = "property",key = "#id")
    @GetMapping("/{id}")
    public ReservationResponse getProperty(@PathVariable String id){
        return propertyService.getProperty(id);
    }

    @GetMapping("/getByEmail")
    @Cacheable(value = "propertyByEmail",key = "#userEmail")
    public List<Property> getPropertyByUserEmail(@RequestParam String userEmail){
        return propertyService.getPropertyByEmail(userEmail);
    }

    @GetMapping("/getAll")
    public List<Property> getAll(){
        return propertyService.getAll();
    }

    @GetMapping("/reserved")
    public List<Property> getReserved(){
        return propertyService.getReserved();
    }

    @GetMapping("/available")
    public List<Property> getAvailable(){
        return propertyService.getAvailable();
    }


}
