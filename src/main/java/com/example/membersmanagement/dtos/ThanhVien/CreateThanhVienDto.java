package com.example.membersmanagement.dtos.ThanhVien;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Email;

@Data
@Builder
public class CreateThanhVienDto {
    @NotEmpty(message = "Mã thành viên không được để trống.")
    @Size(min = 7, max = 7, message = "Mã thành viên phải 7 kí tự.")
    private String maTV;

    private String hoTen;

    private String khoa;

    private String nganh;

    @Size(min = 10, max = 11, message = "Số điện thoại không hợp lệ")
    private String sdt;

    @Email(message = "Địa chỉ email không hợp lệ.")
    private String email;

    @NotEmpty(message = "Mật khẩu không được để trống.")
    @Size(max = 10, message = "Mật khẩu không được quá 10 kí tự.")
    private String password;

}
