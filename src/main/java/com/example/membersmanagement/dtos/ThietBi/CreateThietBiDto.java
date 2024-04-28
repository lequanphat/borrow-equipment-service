package com.example.membersmanagement.dtos.ThietBi;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateThietBiDto {
    @NotEmpty(message = "Mã thiết bị không được để trống.")
    @Size(min = 6, max = 6, message = "Mã thiết bị phải 6 kí tự.")
    private String maTB;

    @NotEmpty(message = "Tên thiết bị không được để trống.")
    @Size(max = 255, message = "Tên thiết bị không được quá 255 kí tự.")
    private String tenTB;

    @Size(max = 255, message = "Mô tả không được quá 255 kí tự.")
    private String moTaTB;
}
