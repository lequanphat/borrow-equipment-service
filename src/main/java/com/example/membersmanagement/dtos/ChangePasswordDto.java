package com.example.membersmanagement.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ChangePasswordDto {

    @NotEmpty(message = "Mật khẩu không được để trống.")
    @Size(min = 6, max = 8, message = "Mật khẩu phải từ 6 - 8 kí tự.")
    private String oldPassword;

    @NotEmpty(message = "Mật khẩu mới không được để trống.")
    @Size(min = 6, max = 8, message = "Mật khẩu mới phải từ 6 - 8 kí tự.")
    private String newPassword;

    @NotEmpty(message = "Xác nhận mật khẩu không được để trống.")
    private String confirmNewPassword;

    public boolean isNewPasswordConfirmed() {
        return newPassword.equals(confirmNewPassword);
    }
}
