package com.example.membersmanagement.dtos.ThongTinSd;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VaoKhuVucHocTapDto {
    @NotEmpty(message = "Mã sinh viên không được trống.")
    @Pattern(regexp = "^[0-9]{8,9}$", message = "Mã sinh viên gồm 8 - 9 chữ số.")
    private String maTV;
}
