package miu.property.service;

import miu.property.dto.PropertyRequest;
import miu.property.dto.ReservationResponse;
import miu.property.dto.ReservationStatusUpdate;
import miu.property.dto.UpdateDto;
import miu.property.model.Property;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Propertyservice {

    Property add(PropertyRequest propertyRequest, List<MultipartFile> images, Double latitude, Double longitude);
    ReservationResponse update(ReservationStatusUpdate ReservationStatusUpdate);
    ReservationResponse getProperty(String id);
//    List<Property> getAll();

    String uploadFile(MultipartFile file);

    List<String> uploadMultipleFiles(List<MultipartFile> files);

    ReservationResponse updateProperty(UpdateDto updateDto);

    List<Property> getReserved();

    List<Property> getAvailable();

    List<Property> getPropertyByEmail(String userEmail);
}
