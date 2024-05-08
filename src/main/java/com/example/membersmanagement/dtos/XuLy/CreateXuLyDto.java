package com.example.membersmanagement.dtos.XuLy;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
public class CreateXuLyDto {
    @NotEmpty(message = "Mã sinh viên không được trống")
    @Pattern(regexp = "^[0-9]{8,9}$", message = "Mã sinh viên gồm 8-9 chữ số")
    private String maTV;

    private String hinhThucXL;

    private Integer soTien;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayXL;

    private Integer trangThaiXL;
}
