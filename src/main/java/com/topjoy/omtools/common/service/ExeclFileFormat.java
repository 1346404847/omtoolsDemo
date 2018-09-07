package com.topjoy.omtools.common.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExeclFileFormat {

    /**
     * 邮件道具批量导入
     * @return
     */
    public static Workbook sendMailFile()
    {
        Workbook xlsx = new XSSFWorkbook();

        Sheet sheet = xlsx.createSheet();
        Row row = sheet.createRow(0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("平台");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("区组");
        Cell cell2 = row.createCell(2);
        cell2.setCellValue("玩家标识roleId");
        Cell cell3 = row.createCell(3);
        cell3.setCellValue("道具列表 (道具格式: {道具id,道具数量;道具id,道具数量;道具id,道具数量}");
        return xlsx;
    }

    /**
     * 修改玩家金币数量导入
     * @return
     */
    public static Workbook playerFile() {
        Workbook xlsx = new XSSFWorkbook();
        Sheet sheet = xlsx.createSheet();
        Row row = sheet.createRow(0);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue("区组");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("玩家标识roleId");
        return xlsx;
    }
}