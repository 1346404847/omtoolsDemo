package com.topjoy.omtools.modules.currentInterface.service;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.topjoy.omtools.common.entity.JsonRpcData;
import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.common.service.Timestamp;
import com.topjoy.omtools.modules.currentInterface.dao.SendMailDao;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import static com.topjoy.omtools.common.util.RandomBatchUtil.mailRandomBatch;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SendMailService {

    @Resource
    private SendMailDao sendMailDao;


    @Value("${fileuploadpath}")
    private String fileUploadPath;

    public Result saveData(List<Map > data)
    {
        return Result.success(sendMailDao.saveSendMail(data));

    }

    /**
     * 查询数据详情
     * @param id
     * @return
     */
    public Result findSendMailInfo(String id)
    {
        return Result.success(sendMailDao.findSendMailById(id));
    }

    /**
     * 返回查询列表
     * @return
     */
    public Result mailSearchList(String params)
    {
        Criteria criteria = new Criteria();
        JSONObject jsonObject = JSONObject.parseObject(params);
        if (jsonObject.getString("approvalStatus").isEmpty()) {
            criteria = Criteria.where("approvalStatus").in(1, 2, 3, 4);
        }else {
            int approvalStatus = Integer.parseInt(jsonObject.getString("approvalStatus"));
            criteria = criteria.where("approvalStatus").is(approvalStatus);
        }
        Result result = commonMailSearch(criteria,params);
        return result;
    }


    /**
     * 邮件审批功能，待审核列表
     * @return
     */
    public Result pendingSendList(String params)
    {

        //默认初始化显示的是待审批的数据
        Criteria criteria = Criteria.where("approvalStatus").is(1);
        Result result = commonMailSearch(criteria,params);
        return result;

    }

    /**
     * 修改审批状态
     * @param params
     * @return
     */
    public Result sendMailUpdate(String params)
    {
        if (params.isEmpty()) {
            Result.updateFailure();
        }
        return Result.success(sendMailDao.updateSendMail(params));
    }


    /**
     * 显示已审核列表
     * @return
     */
    public Result auditedList(String params)
    {
        //approvalStatus 2.已审核 、3.已拒绝
        Criteria criteria = new Criteria();
        JSONObject jsonObject = JSONObject.parseObject(params);
        if (jsonObject.getString("approvalStatus").isEmpty()) {
            criteria = Criteria.where("approvalStatus").in(2,3);
        }else {
            int approvalStatus = Integer.parseInt(jsonObject.getString("approvalStatus"));
            criteria.and("approvalStatus").is(approvalStatus);
        }
        Result result = commonMailSearch(criteria,params);
        return result;

    }


    /**************公共查询*******************/

    public  Result commonMailSearch(Criteria criteria ,String params)
    {

        Query query = new Query();
        int pageNumber = 1;
        if (!params.isEmpty()) {

            JSONObject jsonObject = JSONObject.parseObject(params);
            if (!jsonObject.getString("pageNum").isEmpty()) {
                 pageNumber = Integer.parseInt(jsonObject.getString("pageNum"));
            }

            if (!jsonObject.getString("sendType").isEmpty()) {
                int sendMailType = Integer.parseInt(jsonObject.getString("sendType"));
                criteria.and("sendType").is(sendMailType);
            }

            if (!jsonObject.getString("batch").isEmpty()) {
                criteria.and("batch").is(jsonObject.getString("batch"));
            }

            if (!jsonObject.getString("createTime").isEmpty()) {
                String[] timeString = jsonObject.getString("createTime").split("--");

                Long startTime = Timestamp.dateToTimestamp(timeString[0]);
                Long endTime = Timestamp.dateToTimestamp(timeString[1]);
                if (startTime !=null && endTime !=null) {
                    criteria.andOperator(
                            Criteria.where("createTime").gte(startTime),
                            Criteria.where("createTime").lt(endTime)
                    );
                }
            }

        }
        System.out.println(query.addCriteria(criteria));
//        query.addCriteria(criteria);
        return Result.success(sendMailDao.pendingList(pageNumber,query));

    }



    /**
     * 导入Execl
     *
     * @param data
     * @return
     * @throws FileNotFoundException
     */
    public Boolean addSendPlayerItem(List<Map> data) throws FileNotFoundException {

        //组装玩家列表
        List<Map> list = new ArrayList<Map>();
        //添加数据
        List<Map> playerList = new ArrayList<Map>();
        //添加集合
        Map<String, Object> makeMap = new HashMap<>();

        //批次
        String batch = mailRandomBatch();
        //文件名
        String fileName = "";
        //平台
        makeMap.put("batch", batch);

        for (Map<String, Object> item : data) {
            fileName = fileUploadPath + item.get("fileName").toString();
            makeMap.put("sendType", Integer.parseInt(item.get("sendType").toString()));
            makeMap.put("startTime", item.get("startTime").toString());
            makeMap.put("effectiveTime", item.get("effectiveTime").toString());
            makeMap.put("title", item.get("title").toString());
            makeMap.put("sendPerson", item.get("sendPerson").toString());
            makeMap.put("content", item.get("content").toString());

        }
        //读取文件
        File file = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);

            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            if (xssfSheet != null) {

                for (int i = 1; i < xssfSheet.getLastRowNum() + 1; i++) {

                    Map<String, Object> map = new HashMap<>();
                    XSSFRow xssfRow = xssfSheet.getRow(i);
                    String platId = xssfRow.getCell(0).getStringCellValue();
                    //区服
                    String partition = xssfRow.getCell(1).getStringCellValue();
                    //玩家角色
                    String roleId = xssfRow.getCell(2).getStringCellValue();
                    String itemList = xssfRow.getCell(3).getStringCellValue();
                    map.put("platId", platId);
                    map.put("roleId", roleId);
                    map.put("partition", partition);
                    map.put("itemList",playerItemList(itemList));
                    list.add(map);
                }
            }
            makeMap.put("playerLists", list);
            playerList.add(makeMap);
            if (!playerList.isEmpty()) {
                sendMailDao.addSendImportItem(playerList);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 处理文件上传道具格式
     * @param itemList
     * @return
     */

    public List<Map> playerItemList(String itemList)
    {
        String[] items  = itemList.split(";");
        List<Map> list1 = new ArrayList<Map>();
        for (int j=0; j<items.length;j++) {
            String[] itemProp = items[j].split(",");
            Map<String,String> map1 = new HashMap<>();
            for (int k=0; k<itemProp.length;k++) {
                map1.put("propId",itemProp[0]);
                map1.put("propNum",itemProp[1]);
            }
            list1.add(map1);
        }
        if (list1.isEmpty()) {
            return null;
        }
        return list1;
    }


}