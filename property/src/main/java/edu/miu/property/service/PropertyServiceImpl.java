package edu.miu.property.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import edu.miu.property.dto.PropertyRequest;
import edu.miu.property.dto.UpdateDto;
import edu.miu.property.model.Property;
import edu.miu.property.repository.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service @CacheConfig(cacheNames = {"Property"})
public class PropertyServiceImpl implements Propertyservice{

    private AmazonS3 amazonS3;

    public PropertyServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }


    @Autowired
    private PropertyRepo propertyRepo;

//    public String add(Property property){
//        propertyRepo.save(property);
//        return "property added";
//    }

    public String add(PropertyRequest propertyRequest, List<MultipartFile> images,Double latitude, Double longitude){
        Double location[] = new Double[2];
    location[0]= longitude;
    location[1]= latitude;

    List<String> imageUrls =uploadMultipleFiles(images);

        Property p = Property.builder()
            .propertyName(propertyRequest.getPropertyName())
                .title(propertyRequest.getTitle())
                .price(propertyRequest.getPrice())
                .status(propertyRequest.getStatus())
                .address(propertyRequest.getAddress())
                .userEmail(propertyRequest.getUserEmail())
                .location(location)
                .images(imageUrls).build();

         propertyRepo.save(p);
         return "Property saved";

    }

    public String update(String id){
        Property p = propertyRepo.findById(id).get();
        p.setStatus(!p.getStatus());
        propertyRepo.save(p);
        return "updated";
    }

    @Cacheable
    public Property getProperty(String id){
        Property p = propertyRepo.findById(id).get();
        return p;
    }

//    public List<Property> getAll(){
//        return propertyRepo.findAll();
//    }

    public String uploadFile(MultipartFile file) {

        String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        String key = System.currentTimeMillis() + "_" + filenameExtension;

        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(file.getSize());
        metaData.setContentType(file.getContentType());

        try {
            amazonS3.putObject("propertymanagmentportal", key, file.getInputStream(), metaData);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occured while uploading the file");
        }

        amazonS3.setObjectAcl("propertymanagmentportal", key, CannedAccessControlList.PublicRead);
        URL url = amazonS3.getUrl("propertymanagmentportal", key);

        return url.toString();
    }

    public  List<String> uploadMultipleFiles(List<MultipartFile> files){
        List<String> urlList = new ArrayList<>();

        files.forEach(file -> {
            urlList.add(uploadFile(file));
        });

        return urlList;
    }

    @Override
    public String updateProperty(UpdateDto updateDto) {
        String proId = updateDto.getId();
        Property property = propertyRepo.findById(proId).get();

        Property p = Property.builder()
                .id(updateDto.getId())
                .propertyName(updateDto.getPropertyName())
                .title(updateDto.getTitle())
                .price(updateDto.getPrice())
                .status(updateDto.getStatus())
                .address(updateDto.getAddress())
                .userEmail(updateDto.getUserEmail())
                .images(property.getImages())
                .location(property.getLocation())
                .build();

        propertyRepo.save(p);
        return "Property updated";
    }
}
