package com.example.membersmanagement.validators;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ForgotPasswordValidator {
    @NotEmpty(message = "Email không được để trống.")
    @Email(message = "Email không đúng định dạng.")
    private String email;
}
