package com.example.membersmanagement.dtos.ThongTinSd;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MuonThietBiDto {
    @NotNull(message = "Mã thành viên không được để trống.")
    @Min(value = 100000000, message = "Mã thành viên phải 9 kí tự.")
    @Max(value = 999999999, message = "Mã thành viên phải 9 kí tự.")
    private Integer maTV;
    @NotNull(message = "Mã thiết bị không được để trống.")
    @Min(value = 100000, message = "Mã thiết bị phải 6 kí tự.")
    @Max(value = 999999, message = "Mã thiết bị phải 6 kí tự.")
    private Integer maTB;
}
