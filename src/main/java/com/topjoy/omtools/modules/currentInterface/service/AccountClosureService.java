package com.topjoy.omtools.modules.currentInterface.service;

import com.topjoy.omtools.common.entity.MyException;
import com.topjoy.omtools.modules.currentInterface.dao.AccountClosureDao;
import com.topjoy.omtools.modules.currentInterface.entity.AccountClosureEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.topjoy.omtools.modules.currentInterface.util.ToDateUtil.dateToString;

@Service
public class AccountClosureService {

    @Autowired
    private AccountClosureDao accountClosureDao;

    /**
     * 添加封禁账号
     * @param data
     * @return
     */
    public Boolean addAccountClosure( List<Map > data){
        try {
            //接口调用
            //如果接口调用成功 则插入数据库
            accountClosureDao.addAccountClosure(data);
        }catch ( MyException e){
            System.out.println("service "+ e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 调用接口获取用户数据
     * @param platId
     * @param partition
     * @param roleId
     * @return
     */
    public Map searchUserInfo(String platId, String partition, String roleId){
        Map<String ,Object> userInfo = new HashMap<>();
        userInfo.put("platId",platId);
        userInfo.put("partition",partition);
        userInfo.put("roleId","rid_"+roleId);
        userInfo.put("closureStatus",1);
        userInfo.put("closureType",1);
        userInfo.put("remarks","this is beizhu");
        userInfo.put("startTime","2018-01-12");
        userInfo.put("endTime","2018-01-12");
        return userInfo;
    }

    /**
     * 导入excel 数据插入数据库
     * @param excelName
     * @param accountClosureEntity
     * @return
     * @throws FileNotFoundException
     */
    public Boolean insertUploadExcelData(String excelName , AccountClosureEntity accountClosureEntity) throws FileNotFoundException {
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

        String  batchNumber = "accountExcel"+System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String createTime = df.format(new Date());

        List<AccountClosureEntity> arr = new ArrayList<>();
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null) {
                xssfRow.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                if(xssfRow.getCell(0).getStringCellValue().isEmpty()  || xssfRow.getCell(1).getStringCellValue().isEmpty()){continue;}

                try {
                    AccountClosureEntity acc = (AccountClosureEntity) accountClosureEntity.clone();
                    acc.setPartition(xssfRow.getCell(0).getStringCellValue());
                    acc.setRoleId(xssfRow.getCell(1).getStringCellValue());
                    acc.setBatchNumber(batchNumber);
                    acc.setCreateTime(createTime);
                    acc.setStartTime(dateToString(accountClosureEntity.getStartTime()));
                    acc.setEndTime(dateToString(accountClosureEntity.getEndTime()));
                    arr.add(acc);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        accountClosureDao.addExcelAccountClosure(arr);
        return true;
    }

    /**
     * 获取批次列表
     * @param closureStatus
     * @param closureType
     * @param remarks
     * @param pageNum
     * @return
     */
    public Map<String,Object> getAccountLog(Integer closureStatus, Integer closureType, String startTime,String endTime, String remarks, int pageNum) throws ParseException {
        return accountClosureDao.getAccountLog(closureStatus,closureType,startTime,endTime,remarks, pageNum);
    }
}
