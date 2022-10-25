package edu.miu.property.controller;

import edu.miu.property.dto.PropertyRequest;
import edu.miu.property.dto.ReservationResponse;
import edu.miu.property.dto.ReservationStatusUpdate;
import edu.miu.property.dto.UpdateDto;
import edu.miu.property.model.Address;
import edu.miu.property.model.Property;
import edu.miu.property.service.PropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/property")
public class PropertyController {


    @Autowired
    private PropertyServiceImpl propertyService;

//    @PostMapping("/update")
//    public String update(@RequestParam String id){
//        return propertyService.update(id);
//    }

    @PostMapping("/updateStatus")
    public String update(@RequestBody ReservationStatusUpdate reservationStatusUpdate){
        return propertyService.update(reservationStatusUpdate);
    }

    @PostMapping("/updateProperty")
    public String updateProperty(@RequestBody UpdateDto updateDto){
        return propertyService.updateProperty(updateDto);
    }

    @PostMapping(value = "/create",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public String saveProperty(
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

        return propertyService.add(propertyRequest,images,Double.parseDouble(latitude),Double.parseDouble(longitude));
    }


//    @PostMapping("/add")
//    public String add(@RequestBody Property property){
//
//        return propertyService.add(property);
//    }

    @GetMapping("/{id}")
    public ReservationResponse getProperty(@PathVariable String id){
//        System.out.println();
        return propertyService.getProperty(id);
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

    @GetMapping("/nearby")
    public List<Property> getNearAvailable(@RequestBody Point location){
        return propertyService.getNearByAvailable(location);
    }



//    @PostMapping("/image")
//    String uploadFile(@RequestParam(value = "file") MultipartFile file){
//        return propertyService.uploadFile(file);
//    }

//    @PostMapping("/image/multiple")
//    List<String> uploadMultipleFiles(@RequestParam(value = "file") List<MultipartFile> files){
//        return propertyService.uploadMultipleFiles(files);
//    }

}
