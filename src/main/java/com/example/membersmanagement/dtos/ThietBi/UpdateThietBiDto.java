package com.example.membersmanagement.dtos.ThietBi;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UpdateThietBiDto {
    private String maTB;

    @NotEmpty(message = "Tên thiết bị không được để trống.")
    @Size(max = 255, message = "Tên thiết bị không được quá 255 kí tự.")
    private String tenTB;

    @Size(max = 255, message = "Mô tả không được quá 255 kí tự.")
    private String moTaTB;
}
