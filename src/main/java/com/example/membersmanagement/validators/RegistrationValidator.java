package com.example.membersmanagement.validators;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrationValidator {

    @NotEmpty(message = "Mã sinh viên không được trống.")
    @Pattern(regexp = "^[0-9]{8,9}$", message = "Mã sinh viên gồm 8 - 9 chữ số.")
    private String maTv;

    @Size(min = 5, max = 30, message = "Họ tên phải từ 5 - 30 kí tự.")
    private String hoTen;

    @NotEmpty(message = "Email không được để trống.")
    @Email(message = "Email không đúng định dạng.")
    private String email;

    @NotEmpty(message = "Mật khẩu không được để trống.")
    @Size(min = 6, max = 8, message = "Mật khẩu phải từ 6 - 8 kí tự.")
    private String password;

}
