package com.example.membersmanagement.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ForgotPasswordDto {
    @NotEmpty(message = "Email không được để trống.")
    @Email(message = "Email không đúng định dạng.")
    private String email;
}
