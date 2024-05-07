package com.example.membersmanagement.controllers;

import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.helpers.AjaxResponse;
import com.example.membersmanagement.services.ThietBiService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {
    @Autowired
    ThietBiService thietBiService;

    @PostMapping("/api/import-devices")
    public ResponseEntity<AjaxResponse> importExcelFile(@RequestParam("file") MultipartFile files) throws IOException {
        List<ThietBiEntity> listThietBi = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();

        for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
            XSSFRow row = worksheet.getRow(index);

            String idStr = formatter.formatCellValue(row.getCell(0));
            String tenTB = formatter.formatCellValue(row.getCell(1));
            String moTaTB = formatter.formatCellValue(row.getCell(2));

            if (idStr.isEmpty() || !idStr.matches("\\d+")) {
                return new ResponseEntity<>(
                        new AjaxResponse("Mã thiết bị không hợp lệ"),
                        HttpStatus.BAD_REQUEST
                );
            } else if (thietBiService.existsByMaTb(Integer.parseInt(idStr))) {
                return new ResponseEntity<>(
                        new AjaxResponse(STR."Mã thiết bị \{idStr} đã tồn tại"),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (tenTB.isEmpty()) {
                return new ResponseEntity<>(
                        new AjaxResponse("Tên thiết bị không được để trống"),
                        HttpStatus.BAD_REQUEST
                );
            }

            ThietBiEntity thietBiEntity = new ThietBiEntity();
            thietBiEntity.setMaTB(Integer.parseInt(idStr));
            thietBiEntity.setTenTB(tenTB);
            thietBiEntity.setMoTaTB(moTaTB);
            listThietBi.add(thietBiEntity);
        }

        thietBiService.saveAll(listThietBi);

        return new ResponseEntity<>(
                new AjaxResponse("Imported successfully"),
                HttpStatus.OK
        );
    }
}
