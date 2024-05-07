package com.example.membersmanagement.dtos.ThanhVien;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateThanhVienDto {

    private String maTV;

    @Size(min = 5, max = 30, message = "Họ tên phải từ 5 - 30 kí tự.")
    private String hoTen;

    @NotEmpty(message = "Khoa không được trống.")
    private String khoa;

    @NotEmpty(message = "Ngành không được trống.")
    private String nganh;

    @Size(min = 10, max = 11, message = "Số điện thoại không hợp lệ")
    private String sdt;

    @Email(message = "Địa chỉ email không hợp lệ.")
    private String email;

    @NotEmpty(message = "Mật khẩu không được để trống.")
    @Size(max = 10, message = "Mật khẩu không được quá 10 kí tự.")
    private String password;

}
