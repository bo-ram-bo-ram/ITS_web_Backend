package com.itsweb.backend.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotBlank
    @Size(max = 20)
    private String userId;
    @NotBlank
    @Size(max = 20)
    private String password;

}
