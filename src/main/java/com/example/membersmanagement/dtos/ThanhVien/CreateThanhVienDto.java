package com.example.membersmanagement.dtos.ThanhVien;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Email;

@Data
@Builder
public class CreateThanhVienDto {
    @NotEmpty(message = "Mã sinh viên không được trống.")
    @Pattern(regexp = "^[0-9]{8,9}$", message = "Mã sinh viên gồm 8 - 9 chữ số.")
    private String maTV;

    @Size(min = 5, max = 30, message = "Họ tên phải từ 5 - 30 kí tự.")
    private String hoTen;

    @NotEmpty(message = "Khoa không được trống.")
    private String khoa;

    @NotEmpty(message = "Ngành không được trống.")
    private String nganh;

    @Size(min = 10, max = 11, message = "Số điện thoại không hợp lệ")
    private String sdt;

    @NotEmpty(message = "Email không được để trống.")
    @Email(message = "Email không đúng định dạng.")
    private String email;

    @NotEmpty(message = "Mật khẩu không được để trống.")
    @Size(min = 6, max = 8, message = "Mật khẩu phải từ 6 - 8 kí tự.")
    private String password;

}
