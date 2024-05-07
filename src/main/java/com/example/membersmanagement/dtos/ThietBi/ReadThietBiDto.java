package com.example.membersmanagement.dtos.ThietBi;

import com.example.membersmanagement.enums.TinhTrangThietBi;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadThietBiDto {
    private String maTB;
    private String tenTB;
    private String moTaTB;
    private TinhTrangThietBi tinhTrang;
}
