package edu.miu.property.dto;

import edu.miu.property.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDto {
    private String id;
    private String propertyName;
    private String title;
    private double price;
    private Boolean status;
    private Address address;
    private String userEmail;
}
