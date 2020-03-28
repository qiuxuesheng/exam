package com.qiuxs.huanhuan;

import com.qiuxs.base.action.BaseAction;
import com.qiuxs.base.util.ExcelUtil;
import com.qiuxs.exam.entity.Bill;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillAction extends BaseAction {

    public String list(){

        return "list";
    }


    public void exportExcel(){

        String indexStr = getString("index");
        //获取数据
        List<Bill> bills = new ArrayList<Bill>();
        for (String index : indexStr.split(",")) {
            Bill bill = new Bill();
            bill.setName(getString("name_"+index));
            bill.setMoney(getDouble("money_"+index));
            bill.setPayType(getInt("payType_"+index));
            bill.setDrugType(getInt("drugType_"+index));
            bill.setDocType(getInt("docType_"+index));
            bill.setRemark(getString("remark_"+index));
            bills.add(bill);
        }

        //获取表1 key:docType-drugType
        int docSize = 5;
        int drugSize = 6;
        Map<String,Double> map = new HashMap<String, Double>();
        for (int i = 0; i < docSize; i++) {
            map.put(i+"",0D);
            for (int j = 0; j < drugSize; j++) {
                map.put(i+"-"+j,0D);
            }
        }
        Map<Integer,Double> map2 = new HashMap<Integer, Double>();
        for (int i = 0; i < 3; i++) {
            map2.put(i,0D);
        }

        double allMoney = 0;

        for (Bill bill : bills) {
            String key = bill.getDocType()+"-"+bill.getDrugType();
            map.put(key,map.get(key)+bill.getMoney());
            map.put(bill.getDocType()+"",map.get(bill.getDocType()+"")+bill.getMoney());
            map2.put(bill.getPayType(),map2.get(bill.getPayType())+bill.getMoney());
            allMoney += bill.getMoney();
        }






        String titleValue = getString("billName");

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //2.创建工作簿
        HSSFSheet sheet = hssfWorkbook.createSheet();

        int rowIndex = 1;
        //3.创建标题行


        List<String> titles = new ArrayList<String>();

        titles.add("");
        titles.add("中药");
        titles.add("西药");
        titles.add("推拿");
        titles.add("针灸");
        titles.add("理疗");
        titles.add("小儿推拿");
        titles.add("总金额");

        int cellSize = titles.size();
        Map<Integer, String> params = new HashMap<Integer, String>();
        params.put(1, titleValue);
        ExcelUtil.createRow(sheet, rowIndex++, cellSize, params, ExcelUtil.getTitleStyle(hssfWorkbook));




        params = new HashMap<Integer, String>();
        for (int i=0; i< titles.size() ; i++) {
            params.put(i+1, titles.get(i));
        }
        ExcelUtil.createRow(sheet, rowIndex++, cellSize, params, ExcelUtil.getCellStyle(hssfWorkbook));

        for (int i = 0; i < docSize; i++) {
            params = new HashMap<Integer, String>();
            params.put(1,getDocTypeStr(i));
            for (int j = 0; j < 6; j++) {
                params.put(j+2,map.get(i+"-"+j)+"");
            }
            params.put(drugSize+2,map.get(i+"")+"");
            ExcelUtil.createRow(sheet, rowIndex++, cellSize, params, ExcelUtil.getCellStyle(hssfWorkbook));
        }

        //表1结束


        //详情:
        params = new HashMap<Integer, String>();
        params.put(1, "详情如下:");
        ExcelUtil.createRow(sheet, rowIndex++, cellSize, params, ExcelUtil.getCellStyle(hssfWorkbook));
        ExcelUtil.createRow(sheet, rowIndex++, cellSize, new HashMap<Integer, String>(), ExcelUtil.getCellStyle(hssfWorkbook));

        List<String> titles2 = new ArrayList<String>();
        titles2.add("姓名");
        titles2.add("金额");
        titles2.add("支付种类");
        titles2.add("药品种类");
        titles2.add("医生");
        titles2.add("备注");


        params = new HashMap<Integer, String>();
        for (int i=0; i< titles2.size() ; i++) {
            params.put(i+1, titles2.get(i));
        }
        ExcelUtil.createRow(sheet, rowIndex++, titles2.size(), params, ExcelUtil.getCellStyle(hssfWorkbook));
        //4.遍历数据,创建数据行
        for (Bill bill : bills) {
            params = new HashMap<Integer, String>();
            int i = 1;
            params.put(i++, bill.getName());
            params.put(i++, bill.getMoney()+"");
            params.put(i++, getPayTypeStr(bill.getPayType()));
            params.put(i++, getDrugTypeStr(bill.getDrugType()));
            params.put(i++, getDocTypeStr(bill.getDocType()));
            params.put(i++, bill.getRemark());
            ExcelUtil.createRow(sheet, rowIndex++, titles2.size(), params, ExcelUtil.getCellStyle(hssfWorkbook));
        }


        params = new HashMap<Integer, String>();
        params.put(1, "总金额:"+allMoney+",  医保:"+map2.get(0)+", 现金:"+map2.get(1)+", 支付宝:"+map2.get(2));
        ExcelUtil.createRow(sheet, rowIndex++, titles2.size(), params, ExcelUtil.getCellStyle(hssfWorkbook));
        ExcelUtil.createRow(sheet, rowIndex++, titles2.size(), new HashMap<Integer, String>(), ExcelUtil.getCellStyle(hssfWorkbook));



        //列宽
        for (int i = 0; i < cellSize; i++) {
            sheet.setColumnWidth(i, 3766);
        }

        //合并单元格
        ExcelUtil.mergedRows(sheet,0, 0, 0, cellSize-1);
        ExcelUtil.mergedRows(sheet,docSize+2, docSize+3, 0, cellSize-1);
        ExcelUtil.mergedRows(sheet,docSize+5+bills.size(), docSize+6+bills.size(), 0, titles2.size()-1);

        ExcelUtil.exportExcel(hssfWorkbook, titleValue);


    }

    public String getDrugTypeStr(int drugType){
        String result = "";
        switch (drugType) {
            case 0 :
                result = "中药";
                break;
            case 1 :
                result = "西药";
                break;
            case 2 :
                result = "推拿";
                break;
            case 3 :
                result = "针灸";
                break;
            case 4 :
                result = "理疗";
                break;
            case 5 :
                result = "小儿推拿";
                break;
            default:
                break;
        }
        return result;
    }

    public String getDocTypeStr(int docType){

        String result = "";
        switch (docType) {
            case 0 :
                result = "凡";
                break;
            case 1 :
                result = "顾";
                break;
            case 2 :
                result = "马";
                break;
            case 3 :
                result = "汪";
                break;
            case 4 :
                result = "零售";
                break;
            default:
                break;
        }
        return result;
    }
    public String getPayTypeStr(int payType){
        String result = "";
        switch (payType) {
            case 0 :
                result = "医保";
                break;
            case 1 :
                result = "现金";
                break;
            case 2 :
                result = "支付宝";
                break;
            default:
                break;
        }
        return result;
    }




}
