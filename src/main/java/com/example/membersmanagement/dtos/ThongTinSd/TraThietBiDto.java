package com.example.membersmanagement.dtos.ThongTinSd;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TraThietBiDto {
    @NotNull(message = "Mã thiết bị không được để trống.")
    @Min(value = 100000, message = "Mã thiết bị phải 6 kí tự.")
    @Max(value = 999999, message = "Mã thiết bị phải 6 kí tự.")
    private Integer maTB;
}
