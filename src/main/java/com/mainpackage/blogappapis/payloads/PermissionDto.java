package com.mainpackage.blogappapis.payloads;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PermissionDto {

    private Long id;

    private String name;

    private boolean status;

}
