package com.topjoy.omtools.modules.currentInterface.dao.impl;

import com.alibaba.fastjson.JSON;
import com.topjoy.omtools.common.entity.MyException;
import com.topjoy.omtools.modules.currentInterface.dao.CommunityQueryDao;
import com.topjoy.omtools.modules.currentInterface.entity.CommunityEntity;
import com.topjoy.omtools.modules.currentInterface.entity.CommunityQueryEntity;
import com.topjoy.omtools.modules.currentInterface.util.ExcelUtil;
import com.topjoy.omtools.modules.currentInterface.util.LocalFileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class CommunityQueryImpl implements CommunityQueryDao {

    @Value("classpath:json/communityData.json")

    private Resource resource;

    @Value("${excelfileuploadpath}")
    private String excelFileUploadPath;

    @javax.annotation.Resource
    private MongoTemplate mongoTemplate;

    /**
     * 社团数据入库
     *
     * @return result
     */
    @Override
    public int insertCommunityData(CommunityQueryEntity communityQueryEntity)
    {
        try {
            mongoTemplate.save(communityQueryEntity);
            return 1;
        }catch (Exception e){
            return e.hashCode();
        }
    }

    /**
     * 根据本地文件获取社团数据
     * 获取社团查询列表
     *
     * @return result
     */
    @Override
    public CommunityQueryEntity getCommunityQueryList() throws IOException {
        CommunityQueryEntity communityEntity = new CommunityQueryEntity();
        File file       = resource.getFile();
        String jsonData = LocalFileUtil.jsonRead(file);
        Object object   = JSON.parse(jsonData);    //先转换成Object
        communityEntity.setData(object);
        return communityEntity;
    }

    /**
     * 获取社团查询列表
     * 测试用的
     *
     * @param platformId
     * @param serviceWorkerId
     * @param inputText
     * @return result
     */
    @Override
    public List<CommunityEntity> getCommunitySearchList(String platformId, String serviceWorkerId, String inputText) {
        List<CommunityEntity> communitiesList = null;
        //1.平台不为空，区服为空，搜索内容为空
        if(!platformId.trim().isEmpty() && serviceWorkerId.trim().isEmpty() && inputText.trim().isEmpty()){
            communitiesList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("platformId").is(Integer.parseInt(platformId.trim()))),CommunityEntity.class);
        }//2.平台不为空，区服不为空，搜索内容为空
        else if(!platformId.trim().isEmpty() && !serviceWorkerId.trim().isEmpty() && inputText.trim().isEmpty()){
            communitiesList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("platformId").is(Integer.parseInt(platformId.trim())).and("serviceWorkerId").is(Integer.parseInt(serviceWorkerId.trim()))),CommunityEntity.class);
        }//3.平台不为空，区服不为空，搜索内容不为空
        else if (!platformId.trim().isEmpty() && !serviceWorkerId.trim().isEmpty() && !inputText.trim().isEmpty()){
            communitiesList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("platformId").is(Integer.parseInt(platformId.trim())).and("serviceWorkerId").is(Integer.parseInt(serviceWorkerId.trim())).and("inputText").regex(".*?" +inputText.trim()+ ".*")),CommunityEntity.class);
        }//4.平台为空，区服为空，搜索内容不为空
        else if (platformId.trim().isEmpty() && serviceWorkerId.trim().isEmpty() && !inputText.trim().isEmpty()){
            communitiesList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("inputText").regex(".*?" +inputText.trim()+ ".*")),CommunityEntity.class);
        }//5.平台不为空，区服为空，搜索内容不为空
        else if(!platformId.trim().isEmpty() && !inputText.trim().isEmpty() && serviceWorkerId.trim().isEmpty()){
            communitiesList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("platformId").is(Integer.parseInt(platformId.trim())).and("inputText").regex(".*?" +inputText.trim()+ ".*")),CommunityEntity.class);
        }//6.平台为空，区服为空，搜索内容为空
        else {
            communitiesList = this.mongoTemplate.findAll(CommunityEntity.class);
        }
        return communitiesList;
    }

    /**
     * 导入角色查询列表
     *
     * @return result
     * @param data
     */
    @Override
    public List<CommunityEntity> importExcelCommunities(Map<String,Object> data) throws FileNotFoundException {
        String fileName;
        Workbook workbook  = null;
        if(data.get("fileName") == null){
            return null;
        }
        fileName = excelFileUploadPath + data.get("fileName").toString();
        long size = fileName.length();
        if(fileName.isEmpty() || size == 0){
            return null;
        }
        boolean isExcel2003 = ExcelUtil.isExcel2003(fileName);
        boolean isExcel2007 = ExcelUtil.isExcel2007(fileName);
        boolean valid       = ExcelUtil.validateUpdateExcel(fileName);
        if(!valid){
            throw new MyException("4002","上传文件格式不正确");
        }else{
            //读取文件
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);

            try {
                if(isExcel2003){
                    workbook = new HSSFWorkbook(fileInputStream);
                }else if (isExcel2007){
                    workbook = new XSSFWorkbook(fileInputStream);
                }
                XSSFSheet xssfSheet = (XSSFSheet) workbook.getSheetAt(0);
                DecimalFormat df = new DecimalFormat("######0");
                if (xssfSheet != null) {
                    ArrayList communitiesList = new ArrayList();
                    for (int i = 1; i < xssfSheet.getLastRowNum() + 1; i++) {
                        CommunityEntity communityEntity   = new CommunityEntity();
                        XSSFRow xssfRow = xssfSheet.getRow(i);
                        //游戏ID
                        if (xssfRow.getCell(0) == null){
                            communityEntity.setGameId(0);
                        }else{
                            communityEntity.setGameId(Integer.parseInt(df.format(xssfRow.getCell(0).getNumericCellValue())));
                        }
                        //平台ID
                        if (xssfRow.getCell(1) == null){
                            communityEntity.setPlatformId(0);
                        }else{
                            communityEntity.setPlatformId(Integer.parseInt(df.format(xssfRow.getCell(1).getNumericCellValue())));
                        }
                        //渠道ID
                        if (xssfRow.getCell(2) == null){
                            communityEntity.setChannelId(0);
                        }else{
                            communityEntity.setChannelId(Integer.parseInt(df.format(xssfRow.getCell(2).getNumericCellValue())));
                        }
                        //子渠道ID
                        if (xssfRow.getCell(3) == null){
                            communityEntity.setChildChannelId(0);
                        }else{
                            communityEntity.setChildChannelId(Integer.parseInt(df.format(xssfRow.getCell(3).getNumericCellValue())));
                        }
                        //区服ID
                        if (xssfRow.getCell(4) == null){
                            communityEntity.setServiceWorkerId(0);
                        }else{
                            communityEntity.setServiceWorkerId(Integer.parseInt(df.format(xssfRow.getCell(4).getNumericCellValue())));
                        }
                        //输入搜索
                        if (xssfRow.getCell(5) == null){
                            communityEntity.setInputText("");
                        }else{
                            communityEntity.setInputText(xssfRow.getCell(5).getStringCellValue());
                        }
                        //用户openID
                        if (xssfRow.getCell(6) == null){
                            communityEntity.setOpenId("");
                        }else{
                            communityEntity.setOpenId(xssfRow.getCell(6).getStringCellValue());
                        }
                        //用户UID
                        if (xssfRow.getCell(7) == null){
                            communityEntity.setUID("");
                        }else{
                            communityEntity.setUID(xssfRow.getCell(7).getStringCellValue());
                        }
                        //区服
                        if (xssfRow.getCell(8) == null){
                            communityEntity.setService(0);
                        }else{
                            communityEntity.setService(Integer.parseInt(df.format(xssfRow.getCell(8).getNumericCellValue())));
                        }
                        //角色名称
                        if (xssfRow.getCell(9) == null){
                            communityEntity.setRoleName("");
                        }else{
                            communityEntity.setRoleName(xssfRow.getCell(9).getStringCellValue());
                        }
                        //VIP等级
                        if (xssfRow.getCell(10) == null){
                            communityEntity.setVipRange(0);
                        }else{
                            communityEntity.setVipRange(Integer.parseInt(df.format(xssfRow.getCell(10).getNumericCellValue())));
                        }
                        //钻石数量
                        if (xssfRow.getCell(11) == null){
                            communityEntity.setDiamonds(0);
                        }else{
                            communityEntity.setDiamonds(Integer.parseInt(df.format(xssfRow.getCell(11).getNumericCellValue())));
                        }
                        //金币数量
                        if (xssfRow.getCell(12) == null){
                            communityEntity.setGoldCoin(0);
                        }else{
                            communityEntity.setGoldCoin(Integer.parseInt(df.format(xssfRow.getCell(12).getNumericCellValue())));
                        }
                        //用户状态
                        if (xssfRow.getCell(13) == null){
                            communityEntity.setAccountStatus(0);
                        }else{
                            communityEntity.setAccountStatus(Integer.parseInt(df.format(xssfRow.getCell(13).getNumericCellValue())));
                        }
                        //禁言状态
                        if (xssfRow.getCell(14) == null){
                            communityEntity.setAnExcuseStatus(0);
                        }else{
                            communityEntity.setAnExcuseStatus(Integer.parseInt(df.format(xssfRow.getCell(14).getNumericCellValue())));
                        }
                        //账户
                        if(xssfRow.getCell(15) == null){
                            communityEntity.setAccount("");
                        }else{
                            communityEntity.setAccount(xssfRow.getCell(15).getStringCellValue());
                        }
                        //来源
                        if(xssfRow.getCell(16) == null){
                            communityEntity.setSource("");
                        }else{
                            communityEntity.setSource(xssfRow.getCell(16).getStringCellValue());
                        }

                        //格式化时间
                        Date date = new Date();
                        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        communityEntity.setLastLoginTime(formatTime.format(date));
                        communityEntity.setRegisterTime(formatTime.format(date));

                        // todo 一条一条数据入库
                        // this.mongoTemplate.save(communityEntity, "community");

                        // todo 批量入库数据时检测数据是否已经在库中存有一份相同的数据，用户openID以及UID唯一，作为检测指标
                        List<CommunityEntity> checkCommunity = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("openId").is(communityEntity.getOpenId()).and("UID").is(communityEntity.getUID())),CommunityEntity.class);

                        // System.out.println(checkCommunity.isEmpty());
                        if(checkCommunity.isEmpty()){
                            communitiesList.add(communityEntity);
                        }
                    }
                    // todo 数据批量入库
                    this.mongoTemplate.insertAll(communitiesList);
                    // todo 入库之后查询数据
                    List<CommunityEntity> communitiesAll = this.mongoTemplate.findAll(CommunityEntity.class);
                    return communitiesAll;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 获取导入社团查询列表
     *
     * @return result
     */
    @Override
    public List<CommunityEntity> getCommunities(){
        List<CommunityEntity> communitiesList = this.mongoTemplate.findAll(CommunityEntity.class);
        return communitiesList;
    }

}