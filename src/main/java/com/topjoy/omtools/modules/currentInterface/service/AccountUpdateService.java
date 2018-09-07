package com.topjoy.omtools.modules.currentInterface.service;

import com.topjoy.omtools.common.entity.MyException;
import com.topjoy.omtools.modules.currentInterface.dao.AccountUpdateDao;
import com.topjoy.omtools.modules.currentInterface.entity.AccountUpdate;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class AccountUpdateService {

    @Autowired
    private AccountUpdateDao accountUpdateDao;

    /**
     * 添加账号修改
     * @paramdata
     * @return
     */
    public Boolean addAccountUpdate( List<Map > data){
        try {
            //接口调用
            //如果接口调用成功 则插入数据库
            accountUpdateDao.addAccountUpdate(data);
        }catch ( MyException e){
            System.out.println("==》service fail《==");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 玩家修改批量导入功能
     * @param excelName
     * @param accountUpdate
     * @return
     * @throws FileNotFoundException
     */
    public Boolean insertUploadExcelData(String excelName , AccountUpdate accountUpdate) throws FileNotFoundException {

        //path为xlsx文件路径
        File file = new File(excelName);
        InputStream inputStream = new FileInputStream(file);
        //add the Workbook
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //拼装参数
        List<AccountUpdate> arr = new ArrayList<>();
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {

            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null) {
                xssfRow.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                if(xssfRow.getCell(0).getStringCellValue().isEmpty()  || xssfRow.getCell(1).getStringCellValue().isEmpty()){continue;}
                try {
                    AccountUpdate accUpdate = (AccountUpdate) accountUpdate.clone();
                    accUpdate.setPartition(xssfRow.getCell(0).getStringCellValue());
                    accUpdate.setRoleId(xssfRow.getCell(1).getStringCellValue());
                    arr.add(accUpdate);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        accountUpdateDao.addExcelAccountUpdate(arr);
        return true;
    }
}
