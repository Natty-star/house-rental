package edu.miu.property.service;

import edu.miu.property.dto.PropertyRequest;
import edu.miu.property.dto.ReservationResponse;
import edu.miu.property.dto.ReservationStatusUpdate;
import edu.miu.property.dto.UpdateDto;
import edu.miu.property.model.Property;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Propertyservice {

    String add(PropertyRequest propertyRequest, List<MultipartFile> images, Double latitude, Double longitude);
    String update(ReservationStatusUpdate ReservationStatusUpdate);
    ReservationResponse getProperty(String id);
//    List<Property> getAll();

    String uploadFile(MultipartFile file);

    List<String> uploadMultipleFiles(List<MultipartFile> files);

    String updateProperty(UpdateDto updateDto);
}
