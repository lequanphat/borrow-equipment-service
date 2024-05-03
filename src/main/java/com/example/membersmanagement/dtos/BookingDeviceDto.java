package com.example.membersmanagement.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingDeviceDto {
    @NotNull(message = "Mã thiết bị không được để trống.")
    private Integer MaTB;
    @NotNull(message = "Vui lòng chọn thời gian đặt chỗ")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime TGDatCho;
    private Integer MaTV;
}
