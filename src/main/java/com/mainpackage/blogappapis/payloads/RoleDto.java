package com.mainpackage.blogappapis.payloads;


import com.mainpackage.blogappapis.entities.Permission;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleDto {

    private Long id;

    @NotBlank(message = "Name should not be Blank")
    private String name;

    private Set<Permission> permissions = new HashSet<>();

}
