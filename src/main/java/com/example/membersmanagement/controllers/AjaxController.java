package com.example.membersmanagement.controllers;

import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.helpers.AjaxResponse;
import com.example.membersmanagement.services.ThanhVienService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {
    @Autowired
    ThietBiService thietBiService;
    @Autowired
    ThanhVienService thanhVienService;

    @PostMapping("/api/import-devices")
    public ResponseEntity<AjaxResponse> importExcelFileThietBi(@RequestParam("file") MultipartFile files) throws IOException {
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
                        new AjaxResponse("Mã thiết bị " + idStr + " đã tồn tại"),
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

    @DeleteMapping("/api/delete-multiple-devices/{maLoai}")
    public ResponseEntity<AjaxResponse> deleteMultipleDevices(@PathVariable("maLoai") int maLoai) {
        thietBiService.multipleDelete(maLoai);
        return new ResponseEntity<>(
                new AjaxResponse("Deleted successfully"),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/api/delete-multiple-members/{khoa}")
    public ResponseEntity<AjaxResponse> deleteMultipleMembers(@PathVariable("khoa") int khoa) {
        thanhVienService.multipleDelete(khoa);
        return new ResponseEntity<>(
                new AjaxResponse("Deleted successfully"),
                HttpStatus.OK
        );
    }

    @PostMapping("/api/import-members")
    public ResponseEntity<AjaxResponse> importExcelFileThanhVien(@RequestParam("file") MultipartFile files) throws IOException {
        List<ThanhVienEntity> listThanhVien = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();

        for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
            XSSFRow row = worksheet.getRow(index);

            String idStr = formatter.formatCellValue(row.getCell(0));
            String hoTen = formatter.formatCellValue(row.getCell(1));
            String khoa = formatter.formatCellValue(row.getCell(2));
            String nganh = formatter.formatCellValue(row.getCell(3));
            String sdt = formatter.formatCellValue(row.getCell(4));
            String email = formatter.formatCellValue(row.getCell(5));
            String password = formatter.formatCellValue(row.getCell(6));

            if (idStr.isEmpty() || !idStr.matches("\\d+")) {
                return new ResponseEntity<>(
                        new AjaxResponse("Mã thành viên không hợp lệ"),
                        HttpStatus.BAD_REQUEST
                );
            } else if (thanhVienService.existsByMaTV(Integer.parseInt(idStr))) {
                return new ResponseEntity<>(
                        new AjaxResponse("Mã thành viên " + idStr + " đã tồn tại"),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (hoTen.isEmpty()) {
                return new ResponseEntity<>(
                        new AjaxResponse("Tên thành viên không được để trống"),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (khoa.isEmpty()) {
                return new ResponseEntity<>(
                        new AjaxResponse("Khoa không được để trống"),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (nganh.isEmpty()) {
                return new ResponseEntity<>(
                        new AjaxResponse("Ngành không được để trống"),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (sdt.isEmpty()) {
                return new ResponseEntity<>(
                        new AjaxResponse("Số điện thoại không được để trống"),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (email.isEmpty()) {
                return new ResponseEntity<>(
                        new AjaxResponse("Email không được để trống"),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (password.isEmpty()) {
                return new ResponseEntity<>(
                        new AjaxResponse("Mật khẩu không được để trống"),
                        HttpStatus.BAD_REQUEST
                );
            }

            ThanhVienEntity thanhVienEntity = new ThanhVienEntity();
            thanhVienEntity.setMaTV(Integer.parseInt(idStr));
            thanhVienEntity.setHoTen(hoTen);
            thanhVienEntity.setKhoa(khoa);
            thanhVienEntity.setNganh(nganh);
            thanhVienEntity.setSdt(sdt);
            thanhVienEntity.setEmail(email);
            thanhVienEntity.setPassword(password);
            listThanhVien.add(thanhVienEntity);
        }

        thanhVienService.saveAll(listThanhVien);

        return new ResponseEntity<>(
                new AjaxResponse("Imported successfully"),
                HttpStatus.OK
        );
    }
}
