package com.topjoy.omtools.modules.currentInterface.dao.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.WriteResult;
import com.topjoy.omtools.common.dao.SpringbootPageable;
import com.topjoy.omtools.common.entity.MyException;
import com.topjoy.omtools.common.entity.PageModel;
import com.topjoy.omtools.modules.currentInterface.dao.RoleQueryDao;
import com.topjoy.omtools.modules.currentInterface.entity.CardEntity;
import com.topjoy.omtools.modules.currentInterface.entity.RoleEntity;
import com.topjoy.omtools.modules.currentInterface.entity.RoleQueryEntity;
import com.topjoy.omtools.modules.currentInterface.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RoleQueryImpl implements RoleQueryDao {

    // @Value("classpath:json/roleData.json")

    // private Resource resource;

    @Value("${excelfileuploadpath}")
    private String excelFileUploadPath;

    @javax.annotation.Resource
    private MongoTemplate mongoTemplate;

    /**
     * 角色入库
     *
     * @param roleQueryEntity
     * @return result
     */
    @Override
    public int insertRoleData(RoleQueryEntity roleQueryEntity) {
        try {
            mongoTemplate.save(roleQueryEntity);
            return 1;
        } catch (Exception e) {
            return e.hashCode();
        }
    }

    /**
     * 获取角色查询列表
     *
     * 备注：使用此方法，在java打jar包时，读取不了线上的json文件，本地读取没有问题，所以重写方法使用下面的方法读取json文件。
     *
     * 如果静态资源文件在文件系统里，则支持解析为 java.io.File，程序是能正常工作的，
     *
     * 到项目打包成 Jar 包放到服务器上运行就报找不到资源的错误提示了，
     *
     * 解决法案是：不获取 java.io.File 对象，而是直接获取输入流。
     *
     * @return result
     */
    /*@Override
    public RoleQueryEntity getRoleQueryList()
    {
        RoleQueryEntity roleQueryEntity = new RoleQueryEntity();

        try {
            File file       = resource.getFile();
            //开始执行时间
            long start      = System.nanoTime();
            String jsonData = LocalFileUtil.jsonRead(file);
            //执行结束时间
            long end        = System.nanoTime();
            //执行时间间隔
            long millis     = TimeUnit.NANOSECONDS.toMillis(end-start);
            //打印时间间隔
            System.out.println(millis + "ms");
            Object object   = JSON.parse(jsonData);    //先转换成Object
            roleQueryEntity.setData(object);
            return roleQueryEntity;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }*/

    /**
     * 获取角色查询列表
     *
     * @return result
     */
    @Override
    public RoleQueryEntity getRoleQueryList()
    {
        RoleQueryEntity roleQueryEntity = new RoleQueryEntity();
        BufferedReader bufferedReader   = null;
        try {
            // Resource resource = new ClassPathResource("/json/roleData.json");
            Resource resource = new ClassPathResource("");
            bufferedReader    = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String message    = "";
            String line;
            while((line = bufferedReader.readLine()) != null) {
                message += line;
            }
            //开始执行时间
            long start      = System.nanoTime();
            //执行结束时间
            long end        = System.nanoTime();
            //执行时间间隔
            long millis     = TimeUnit.NANOSECONDS.toMillis(end-start);
            //打印时间间隔
            // System.out.println(millis + "ms");
            Object object   = JSON.parse(message);    //先转换成Object
            roleQueryEntity.setData(object);
            return roleQueryEntity;
        } catch (IOException e) {
                e.printStackTrace();
                return null;
        }finally{
                try {
                    bufferedReader.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                    e2.printStackTrace();
                    return null;
                }
        }
    }

    /**
     * 获取角色查询列表
     * 测试用的
     *
     * @return result
     * @param platformId
     * @param serviceWorkerId
     * @param inputText
     */
    @Override
    public List<RoleEntity> getRoleSearchList(String platformId,String serviceWorkerId,String inputText)
    {
        List<RoleEntity> rolesList = null;
        //1.平台不为空，区服为空，搜索内容为空
        if(!platformId.trim().isEmpty() && serviceWorkerId.trim().isEmpty() && inputText.trim().isEmpty()){
            rolesList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("platformId").is(Integer.parseInt(platformId.trim()))),RoleEntity.class);
        }//2.平台不为空，区服不为空，搜索内容为空
        else if(!platformId.trim().isEmpty() && !serviceWorkerId.trim().isEmpty() && inputText.trim().isEmpty()){
            rolesList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("platformId").is(Integer.parseInt(platformId.trim())).and("serviceWorkerId").is(Integer.parseInt(serviceWorkerId.trim()))),RoleEntity.class);
        }//3.平台不为空，区服不为空，搜索内容不为空
        else if (!platformId.trim().isEmpty() && !serviceWorkerId.trim().isEmpty() && !inputText.trim().isEmpty()){
            rolesList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("platformId").is(Integer.parseInt(platformId.trim())).and("serviceWorkerId").is(Integer.parseInt(serviceWorkerId.trim())).and("inputText").regex(".*?" +inputText.trim()+ ".*")),RoleEntity.class);
        }//4.平台为空，区服为空，搜索内容不为空
        else if (platformId.trim().isEmpty() && serviceWorkerId.trim().isEmpty() && !inputText.trim().isEmpty()){
            rolesList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("inputText").regex(".*?" +inputText.trim()+ ".*")),RoleEntity.class);
        }//5.平台不为空，区服为空，搜索内容不为空
        else if(!platformId.trim().isEmpty() && !inputText.trim().isEmpty() && serviceWorkerId.trim().isEmpty()){
            rolesList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("platformId").is(Integer.parseInt(platformId.trim())).and("inputText").regex(".*?" +inputText.trim()+ ".*")),RoleEntity.class);
        }//6.平台为空，区服为空，搜索内容为空
        else {
            rolesList = this.mongoTemplate.findAll(RoleEntity.class);
        }
        return rolesList;
    }

    /**
     * 导入角色查询列表
     *
     * @return result
     * @param data
     */
    @Override
    public List<RoleEntity> importExcelRoles(Map<String,Object> data) throws FileNotFoundException{
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
                DecimalFormat    df = new DecimalFormat("######0");
                if (xssfSheet != null) {
                    ArrayList rolesList     = new ArrayList();
                    ArrayList communityList = new ArrayList();
                    for (int i = 1; i < xssfSheet.getLastRowNum() + 1; i++) {
                        RoleEntity roleEntity   = new RoleEntity();
                        CardEntity cardEntity   = new CardEntity();
                        XSSFRow xssfRow = xssfSheet.getRow(i);
                        //游戏ID
                        if (xssfRow.getCell(0) == null){
                            roleEntity.setGameId(0);
                        }else{
                            roleEntity.setGameId(Integer.parseInt(df.format(xssfRow.getCell(0).getNumericCellValue())));
                        }
                        //平台ID
                        if (xssfRow.getCell(1) == null){
                            roleEntity.setPlatformId(0);
                        }else{
                            roleEntity.setPlatformId(Integer.parseInt(df.format(xssfRow.getCell(1).getNumericCellValue())));
                        }
                        //渠道ID
                        if (xssfRow.getCell(2) == null){
                            roleEntity.setChannelId(0);
                        }else{
                            roleEntity.setChannelId(Integer.parseInt(df.format(xssfRow.getCell(2).getNumericCellValue())));
                        }
                        //子渠道ID
                        if (xssfRow.getCell(3) == null){
                            roleEntity.setChildChannelId(0);
                        }else{
                            roleEntity.setChildChannelId(Integer.parseInt(df.format(xssfRow.getCell(3).getNumericCellValue())));
                        }
                        //区服ID
                        if (xssfRow.getCell(4) == null){
                            roleEntity.setServiceWorkerId(0);
                        }else{
                            roleEntity.setServiceWorkerId(Integer.parseInt(df.format(xssfRow.getCell(4).getNumericCellValue())));
                        }
                        //输入搜索
                        if (xssfRow.getCell(5) == null){
                            roleEntity.setInputText("");
                        }else{
                            roleEntity.setInputText(xssfRow.getCell(5).getStringCellValue());
                        }
                        //用户openID
                        if (xssfRow.getCell(6) == null){
                            roleEntity.setOpenId("");
                        }else{
                            roleEntity.setOpenId(xssfRow.getCell(6).getStringCellValue());
                        }
                        //用户UID
                        if (xssfRow.getCell(7) == null){
                            roleEntity.setUID("");
                        }else{
                            roleEntity.setUID(xssfRow.getCell(7).getStringCellValue());
                        }
                        //区服
                        if (xssfRow.getCell(8) == null){
                            roleEntity.setService(0);
                        }else{
                            roleEntity.setService(Integer.parseInt(df.format(xssfRow.getCell(8).getNumericCellValue())));
                        }
                        //角色名称
                        if (xssfRow.getCell(9) == null){
                            roleEntity.setRoleName("");
                        }else{
                            roleEntity.setRoleName(xssfRow.getCell(9).getStringCellValue());
                        }
                        //VIP等级
                        if (xssfRow.getCell(10) == null){
                            roleEntity.setVipRange(0);
                        }else{
                            roleEntity.setVipRange(Integer.parseInt(df.format(xssfRow.getCell(10).getNumericCellValue())));
                        }
                        //钻石数量
                        if (xssfRow.getCell(11) == null){
                            roleEntity.setDiamonds(0);
                        }else{
                            roleEntity.setDiamonds(Integer.parseInt(df.format(xssfRow.getCell(11).getNumericCellValue())));
                        }
                        //金币数量
                        if (xssfRow.getCell(12) == null){
                            roleEntity.setGoldCoin(0);
                        }else{
                            roleEntity.setGoldCoin(Integer.parseInt(df.format(xssfRow.getCell(12).getNumericCellValue())));
                        }
                        //用户状态
                        if (xssfRow.getCell(13) == null){
                            roleEntity.setAccountStatus(0);
                        }else{
                            roleEntity.setAccountStatus(Integer.parseInt(df.format(xssfRow.getCell(13).getNumericCellValue())));
                        }
                        //禁言状态
                        if (xssfRow.getCell(14) == null){
                            roleEntity.setAnExcuseStatus(0);
                        }else{
                            roleEntity.setAnExcuseStatus(Integer.parseInt(df.format(xssfRow.getCell(14).getNumericCellValue())));
                        }
                        //道具ID
                        if (xssfRow.getCell(15) == null){
                            roleEntity.setPropId(0);
                        }else{
                            roleEntity.setPropId(Integer.parseInt(df.format(xssfRow.getCell(15).getNumericCellValue())));
                        }
                        //道具图片地址
                        if (xssfRow.getCell(16) == null){
                            roleEntity.setPropPicture("");
                        }else{
                            roleEntity.setPropPicture(xssfRow.getCell(16).getStringCellValue());
                        }
                        //道具名称
                        if (xssfRow.getCell(17) == null){
                            roleEntity.setPropName("");
                        }else{
                            roleEntity.setPropName(xssfRow.getCell(17).getStringCellValue());
                        }
                        //道具数量
                        if (xssfRow.getCell(18) == null){
                            roleEntity.setPropNum(0);
                        }else{
                            roleEntity.setPropNum(Integer.parseInt(df.format(xssfRow.getCell(18).getNumericCellValue())));
                        }
                        //道具描述
                        if (xssfRow.getCell(19) == null){
                            roleEntity.setPropDescribe("");
                        }else{
                            roleEntity.setPropDescribe(xssfRow.getCell(19).getStringCellValue());
                        }
                        //账户
                        if(xssfRow.getCell(20) == null){
                            roleEntity.setAccount("");
                        }else{
                            roleEntity.setAccount(xssfRow.getCell(20).getStringCellValue());
                        }
                        //来源
                        if(xssfRow.getCell(21) == null){
                            roleEntity.setSource("");
                        }else{
                            roleEntity.setSource(xssfRow.getCell(21).getStringCellValue());
                        }

                        //卡牌图片地址
                        if(xssfRow.getCell(22) == null){
                            cardEntity.setUrl("");
                        }else{
                            cardEntity.setUrl(xssfRow.getCell(22).getStringCellValue());
                        }

                        //卡牌名称
                        if(xssfRow.getCell(23) == null){
                            cardEntity.setName("");
                        }else{
                            cardEntity.setName(xssfRow.getCell(23).getStringCellValue());
                        }

                        //卡牌ID
                        if(xssfRow.getCell(24) == null){
                            cardEntity.setCardId(0);
                        }else{
                            cardEntity.setCardId(Integer.parseInt(df.format(xssfRow.getCell(24).getNumericCellValue())));
                        }

                        //卡牌等级
                        if(xssfRow.getCell(25) == null){
                            cardEntity.setRange(0);
                        }else{
                            cardEntity.setRange(Integer.parseInt(df.format(xssfRow.getCell(25).getNumericCellValue())));
                        }

                        //卡牌品质
                        if(xssfRow.getCell(26) == null){
                            cardEntity.setQuality("");
                        }else{
                            cardEntity.setQuality(xssfRow.getCell(26).getStringCellValue());
                        }

                        //卡牌战斗力
                        if(xssfRow.getCell(27) == null){
                            cardEntity.setPower(0);
                        }else{
                            cardEntity.setPower(Integer.parseInt(df.format(xssfRow.getCell(27).getNumericCellValue())));
                        }

                        //格式化时间
                        Date date = new Date();
                        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        roleEntity.setLastLoginTime(formatTime.format(date));
                        roleEntity.setRegisterTime(formatTime.format(date));

                        // todo 一条一条数据入库
                        // this.mongoTemplate.save(roleEntity, "role");

                        // todo 批量入库数据时检测数据是否已经在库中存有一份相同的数据，用户openID以及UID唯一，作为检测指标
                        List<RoleEntity> checkRole = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("openId").is(roleEntity.getOpenId()).and("UID").is(roleEntity.getUID())),RoleEntity.class);

                        // System.out.println(checkRole.isEmpty());
                        if(checkRole.isEmpty()){
                            rolesList.add(roleEntity);
                            communityList.add(cardEntity);
                        }
                    }
                    // System.out.println(communityList);
                    // todo 数据批量入库
                    this.mongoTemplate.insertAll(rolesList);
                    this.mongoTemplate.insertAll(communityList);
                    // todo 入库之后查询数据
                    List<RoleEntity> roles = this.mongoTemplate.findAll(RoleEntity.class);
                    return roles;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 获取角色查询列表
     *
     * @return result
     */
    @Override
    public List<RoleEntity> getRolesList(){
        List<RoleEntity> rolesList = this.mongoTemplate.findAll(RoleEntity.class);
        return rolesList;
    }

    /**
     * 获取背包数据信息
     *
     * @param propName
     * @return result
     */
    public List<RoleEntity> getPacks(String propName){
        List<RoleEntity> packsList = this.mongoTemplate.find(new Query().addCriteria(Criteria.where("propName").regex(".*?" +propName.trim()+ ".*")),RoleEntity.class);
        return packsList;
    }

    /**
     * 获取单个角色信息
     *
     * @param openId
     * @return result
     */
    @Override
    public RoleEntity getOneRoleQuery(String openId){
        if(openId.trim().isEmpty()){
            return null;
        }else{
            RoleEntity role = this.mongoTemplate.findOne(new Query().addCriteria(Criteria.where("openId").is(openId.trim())),RoleEntity.class);
            if(role != null){
                return role;
            }else{
                return null;
            }
        }
    }

    /**
     * 更新单个角色信息
     *
     * @param openId
     * @return result
     */
    @Override
    public boolean updateOneRoleQuery(String openId){
        WriteResult up;
        RoleEntity roleEntity = new RoleEntity();

        //格式化时间
        Date date = new Date();
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        roleEntity.setLastLoginTime(formatTime.format(date));
        roleEntity.setRegisterTime(formatTime.format(date));

        if(openId.trim().isEmpty()){
            return false;
        }else{
            up = this.mongoTemplate.updateFirst(new Query().addCriteria(Criteria.where("openId").is(openId.trim())),new Update().set("lastLoginTime",roleEntity.getLastLoginTime()).set("registerTime",roleEntity.getRegisterTime()),RoleEntity.class);
            if(up != null){
                return true;
            }else{
                return false;
            }
        }
    }

    /**
     * 删除单个角色信息
     *
     * @param openId
     * @return result
     */
    @Override
    public boolean deleteOneRoleQuery(String openId){
        RoleEntity del;
        if(openId.trim().isEmpty()){
            return false;
        }else{
            del = this.mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("openId").is(openId.trim())),RoleEntity.class);
            if(del != null){
                return true;
            }else {
                return false;
            }
        }

    }

    /**
     * 根据页码获取对应页的角色数据信息
     *
     * 问题：这其中的"查询"是将所有结果都查询出来， 然后在将集合与分页结果封装。
     * "查询"的这步就是个坑，假如数据量上千万或亿级别，查询出来内存装不装的下就是个问题？能不能一次性查询出来也是个问题？
     * 有待寻找好的解决方案
     * @param platformId 平台ID
     * @param serviceWorkerId 区服ID
     * @param currentPage 当前页
     * @return result
     */
    @Override
    public Page<RoleEntity> getPageRoleQuery(String platformId, String serviceWorkerId, String currentPage){

        Criteria criteria = new Criteria();
        if(!platformId.trim().isEmpty()){
            criteria.and("platformId").is(Integer.parseInt(platformId.trim()));
        }
        if(!serviceWorkerId.trim().isEmpty()){
            criteria.and("serviceWorkerId").is(Integer.parseInt(serviceWorkerId.trim()));
        }

        PageModel pageModel         = new PageModel();
        List<Sort.Order> orders     = new ArrayList<>();
        SpringbootPageable pageable = new SpringbootPageable();

        orders.add(new Sort.Order(Sort.Direction.DESC,"openId"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"registerTime"));

        // 开始页
        pageModel.setPagenumber(Integer.parseInt(currentPage));
        pageable.setPage(pageModel);

        // 查询出一共的条数
        Long count                 = this.mongoTemplate.count(new Query().addCriteria(criteria).with(new Sort(orders)), RoleEntity.class);
        // 查询
        List<RoleEntity> rolesList = this.mongoTemplate.find(new Query().addCriteria(criteria).with(new Sort(orders)), RoleEntity.class);
        // 将集合与分页结果封装
        Page<RoleEntity> pageList  = new PageImpl<>(rolesList, pageable, count);

        return pageList;
    }

    /**
     * 获取卡牌数据信息
     *
     * @return result
     */
    @Override
    public List<CardEntity> getCards(){
        List<CardEntity> cards = this.mongoTemplate.findAll(CardEntity.class);
        return cards;
    }

}
