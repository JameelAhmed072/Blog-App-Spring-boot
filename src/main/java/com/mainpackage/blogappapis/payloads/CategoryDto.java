package com.mainpackage.blogappapis.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CategoryDto {


    private Integer category_id;
    @NotBlank
    @Size(min = 4,message = "Minimum size of title is 4")
    private String catergoryTitle;
    @NotBlank
    @Size(min = 10,message = "Minimum size of desciption is 10")
    private String catergoryDiscription;
}
