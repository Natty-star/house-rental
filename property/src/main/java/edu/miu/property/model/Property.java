package edu.miu.property.model;

import edu.miu.property.dto.PropertyRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Document
@Data
@Builder
public class Property implements Serializable {


    @Id
    private String id;
    private String propertyName;
    private String title;
    private double price;
    private Boolean status;
    private Address address;
    private String userEmail;
    private List<String> images;
    private Double[] location;

}
