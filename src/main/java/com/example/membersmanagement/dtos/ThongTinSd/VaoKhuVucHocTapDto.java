package com.example.membersmanagement.dtos.ThongTinSd;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VaoKhuVucHocTapDto {
    @NotNull(message = "Mã thành viên không được để trống.")
    @Min(value = 100000000, message = "Mã thành viên phải 9 kí tự.")
    @Max(value = 999999999, message = "Mã thành viên phải 9 kí tự.")
    private Integer maTV;
}
