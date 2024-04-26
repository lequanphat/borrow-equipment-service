package com.example.membersmanagement.validators;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ChangePasswordValidator {

    @NotEmpty(message = "Mật khẩu không được để trống.")
    @Size(min = 6, max = 8, message = "Mật khẩu phải từ 6 - 8 kí tự.")
    private String password;

    @NotEmpty(message = "Mật khẩu mới không được để trống.")
    @Size(min = 6, max = 8, message = "Mật khẩu mới phải từ 6 - 8 kí tự.")
    private String newPassword;

}
