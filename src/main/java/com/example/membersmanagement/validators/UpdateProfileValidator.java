package com.example.membersmanagement.validators;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateProfileValidator {
    private String maTV;
    private String email;

    @NotEmpty(message = "Họ tên không được để trống.")
    @Size(min = 5, max = 30, message = "Họ tên phải từ 5 - 30 kí tự.")
    private String hoTen;

    @NotEmpty(message = "Số điện thoại không được để trống.")
    @Pattern(regexp = "^0[0-9]{9,10}$", message = "Số điện thoại không hợp lệ.")
    private String sdt;

    @NotEmpty(message = "Ngành không được để trống.")
    private String nganh;

    @NotEmpty(message = "Khoa không được để trống.")
    private String khoa;

}
