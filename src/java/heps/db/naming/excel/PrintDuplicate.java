/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author BaiYu
 */
public class PrintDuplicate {
    
    public Workbook wb;

    public PrintDuplicate(Workbook wb) {
        this.wb = wb;
        
    }

    public Workbook getWb() {
        return wb;
    }

    public void setWb(Workbook wb) {
        this.wb = wb;
    }
    
    public void checkTitle(File file){
        Workbook wb = ExcelTool.getWorkbook(file);
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            int rowNum = sheet.getPhysicalNumberOfRows();
            Cell cell0 = sheet.getRow(1).getCell(0);
            String str = cell0.getStringCellValue();
            if (! str.trim().equals("系统代号") ) {
                JOptionPane.showMessageDialog(null, "表格不符合标准模板！", "系统提示", JOptionPane.WARNING_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "通过！可继续操作！", "系统提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
//    对想要检测重复数据的列单独取出
    public ArrayList readExcelData(File file,int rowBegin){
        ArrayList excelData = new ArrayList();
        Workbook wb = ExcelTool.getWorkbook(file);
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            int rowNum = sheet.getPhysicalNumberOfRows();
            for (int j = rowBegin; j < rowNum; j++) {
                if (sheet.getRow(j) != null) {
                    Row row = sheet.getRow(j);
                    ArrayList oneRowList = new ArrayList();    
                        for (int k = 0; k < 6; k++) {                                  
                            if (sheet.getRow(j).getCell(k) != null) {
                                Cell cell = row.getCell(k);
                                Object o = "";
                                switch(cell.getCellTypeEnum()){
                                    case STRING:
                                        o = cell.getStringCellValue().trim();
                                        oneRowList.add(o);
                                        break;
                                    case NUMERIC:
                                        oneRowList.add(cell.getNumericCellValue());
                                        break;
                                    case BOOLEAN:
                                        oneRowList.add(cell.getBooleanCellValue());
                                        break;
                                    case FORMULA:
                                        oneRowList.add(cell.getCellFormula());
                                        break;
                                    case BLANK:
                                        o = "";
                                        oneRowList.add(o);
                                        break;
                                    case ERROR:
                                        System.out.println("Error");
                                        break;
                                } 
                            }
                        }

                    excelData.add(sheetName);
                    excelData.add(j);
                    excelData.add(oneRowList.get(0).toString()); 
                    excelData.add(oneRowList.get(1).toString());                   
                }
                              
            }
        }       
        System.out.println("#####################");
//        System.out.println(excelData);
        return excelData;        
    }
    
 //调用此方法可将excel数据读取   
    public ArrayList readExcel(File file,int rowBegin){
//        checkTitle(file);
        ArrayList excelData = new ArrayList();
        Workbook wb = ExcelTool.getWorkbook(file);
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            int rowNum = sheet.getPhysicalNumberOfRows();
            for (int j = rowBegin; j < rowNum; j++) {
                if (sheet.getRow(j) != null) {
                    Row row = sheet.getRow(j);
                    ArrayList oneRowList = new ArrayList();
//                    oneRowList.add(sheetName);
                    for (int k = 0; k < sheet.getRow(j).getLastCellNum(); k++) {
//                        int k = 5;
                        if (sheet.getRow(j).getCell(k) != null) {
                            Cell cell = row.getCell(k);
                            Object o = "";
                            switch(cell.getCellTypeEnum()){
                                case STRING:
                                    o = cell.getStringCellValue().trim();
                                    oneRowList.add(o);
                                    break;
                                case NUMERIC:
                                    oneRowList.add(cell.getNumericCellValue());
                                    break;
                                case BOOLEAN:
                                    oneRowList.add(cell.getBooleanCellValue());
                                    break;
                                case FORMULA:
                                    oneRowList.add(cell.getCellFormula());
                                    break;
                                case BLANK:
                                    o = "";
                                    oneRowList.add(o);
                                    break;
                                case ERROR:
                                    System.out.println("Error");
                                    break;
                            } 
                            
                        }
                    }
                    excelData.add(oneRowList);
                }
                              
            }
        }
        System.out.println(excelData);
        return excelData;        
    }
    //解析后判断是否符合cell规范（利用各正则表达式）
    /**
     * 
     * @param file 读取文件路径
     * @param rowBegin 表格开始行数，包括列名行
     * @param cellBegin 表格开始列数，从有内容的列开始
     * @return 
     */
    public ArrayList checkAndGetSheetData(Workbook wb,int rowBegin,int cellBegin){
        ArrayList data = new ArrayList();
//        Workbook wb = ExcelTool.getWorkbook(file);
        Sheet sheet = null;
        Row row = null;
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            sheet = wb.getSheetAt(i);
            int rownum = sheet.getPhysicalNumberOfRows();
            String sysID, Chiname, Engname, deviname, mainPart, position, larnum, another, remark;
            int number;
                for (int j = rowBegin; j < rownum; j++) {
                    ArrayList oneRow = new ArrayList();
                    row = sheet.getRow(j);
                    Cell cell1 = row.getCell(0);
//                    Cell cell2 = row.getCell(1);
                    Cell cell3 = row.getCell(2);
                    Cell cell4 = row.getCell(3);
                    Cell cell5 = row.getCell(4);
                    Cell cell6 = row.getCell(5);
                    Cell cell7 = row.getCell(6);
                    Cell cell8 = row.getCell(7);
                    Cell cell9 = row.getCell(8);
                    Cell cell10 = row.getCell(9);
                    sysID = cell1.toString();
//                    number = Double.valueOf(cell2.toString()).intValue();
//                    num = cell2.toString();
//                    num = String.valueOf(number);
                    Chiname = cell3.toString();
                    Engname = cell4.toString();
                    deviname = cell5.toString();
                    mainPart = cell6.toString();
                    position = cell7.toString();
                    larnum = cell8.toString();
                    another = cell9.toString();
                    remark = cell10.toString();
                            
                    try {     
                        if(! isSerialNumber(sysID)){
                            System.out.println("表格第" + (j+1) + "行，系统代号列内容不符合规范，请核查！");
                        }
//                        if (String.valueOf(Double.valueOf(num).intValue()) == null || String.valueOf(Double.valueOf(num).intValue()).equals("")) {
//                            System.out.println("表格第" + (j+1) + "行，序号列内容为空，请核查！");
//                        }else if (! isNumber(String.valueOf(Double.valueOf(num).intValue()))) {
//                            System.out.println("表格第" + (j+1) + "行，序号列内容不是数字，请核查！");
//                        }
                        if (! isChineseWord(Chiname)) {
                            System.out.println("表格第" + (j+1) + "行，中文名称列内容不是中文，请核查！");
                        }
                        if (! isEnglishWord(Engname)) {
                            System.out.println("表格第" + (j+1) + "行，英文名称列内容不是英文，请核查！");
                        }
                        if (! isAZ(deviname)) {
                            System.out.println("表格第" + (j+1) + "行，设备代号列内容不是大写字母，请核查！");
                        }
                        if (! isMainPart(mainPart)) {
                            System.out.println("表格第" + (j+1) + "行，主体分区列内容不符合规则，请核查！");
                        }
                        if (! isLocation(position)) {
                            System.out.println("表格第" + (j+1) + "行，位置列内容不符合规则，请核查！");
                        }
                        if (! isYes(larnum)) {
                            System.out.println("表格第" + (j+1) + "行，是否>9列内容只能是Yes/No，请核查！");
                        }
                        else if (isSerialNumber(sysID) && isChineseWord(Chiname) && isEnglishWord(Engname) && isAZ(deviname) && isMainPart(mainPart) && isLocation(position) && isYes(larnum)) {
                            System.out.println("表格第" + (j+1) + "行，无错误信息！");
                            oneRow.add(sysID);
//                            oneRow.add(String.valueOf(Double.valueOf(num).intValue()));
                            oneRow.add(Chiname);
                            oneRow.add(Engname);
                            oneRow.add(deviname);
                            oneRow.add(mainPart);
                            oneRow.add(position);
                            oneRow.add(larnum);
                            oneRow.add(another);
                            oneRow.add(remark);
                            data.add(oneRow);
                        }                        
                        
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }                                      
            }   
            System.out.println("###########################");
//            System.out.println(data);
            return data;        
    }
    
    //数据解析（location的数字部分）
    /**
     * 
     * @param wb 工作簿
     * @param rowBegin 表格开始行数，包括列名行
     * @param cellBegin 表格开始列数，从有内容的列开始 
     * @param data 要解析的数据
     * @return 
     */
    public ArrayList splitNum(Workbook wb, int rowBegin,int cellBegin, ArrayList data){ 
        ArrayList title = new ArrayList();
//        Workbook wb = ExcelTool.getWorkbook(file);
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(rowBegin-1);
        for (int col = cellBegin; col < sheet.getRow(rowBegin-1).getLastCellNum(); col++) {
            if (sheet.getRow(rowBegin-1).getCell(col) != null) {
                Cell cell = row.getCell(col);
                Object o = "";
                switch(cell.getCellTypeEnum()){
                    case STRING:
                        o = cell.getStringCellValue().trim();
                        title.add(o);
                        break;
                    case NUMERIC:
                        title.add(cell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        title.add(cell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        title.add(cell.getCellFormula());
                        break;
                    case BLANK:
                        o = "";
                        title.add(o);
                        break;
                    case ERROR:
                        System.out.println("Error");
                        break;
                } 
                            
            }
        }
        title.remove(1);
        int locationNum = new ListTool(title).getOneRowColNum("位置");
//        System.out.println(locationNum);
        String location;
        ArrayList arraydata = new ArrayList(); //
        
        ArrayList klist = new ArrayList(); //添加每一行的index 
//        locationNum = 6;//作为参数放入方法中
        for (int k = 0; k < data.size(); k++) {
            ArrayList datapiece = new ArrayList(); //datapiece是将传入的data划分成的各个片段，相当于将excel中每一行划分出来
            datapiece = (ArrayList) data.get(k);
            location = datapiece.get(locationNum).toString();
            //判断01-48或者1-4这种包含“-”的数据
            List numpiece = new ArrayList();
            //有的cell中只有“-”，看做空格处理
            if (location.equals("-")) {
                location = "";
            }
            if (location.contains("-")) {
                klist.add(k);
                String[] piece = location.split("-");
//                List numpiece = new ArrayList();
                //判断为01或者两位数开始的数据，两位数开头的即使是个位数字也要在前一位补0
                if (piece[0].contains("0")) {
                    for (int i = Integer.parseInt(piece[0]); i <= Integer.parseInt(piece[1]); i++) {
                    //DeviceAPI a = new DeviceAPI();
                        Format f = new DecimalFormat("00");
                        String si = f.format(i);
                        if (i==Integer.parseInt(piece[1])) {
                            numpiece.add(si);
                        }else{
                            numpiece.add(si);
    //                        numpiece.add(si+"、");
                        }
                    }
                }else{
                    for (int i = Integer.parseInt(piece[0]); i <= Integer.parseInt(piece[1]); i++) {
                        if (i==Integer.parseInt(piece[1])) {
                            numpiece.add(i);
                        }else{
                            numpiece.add(i);
    //                        numpiece.add(i+"、");
                        }
                    }
                }
                
            }
            //判断为不连续数字，以“，”为分隔符的
            else if (location.contains(",")) {
                klist.add(k);
                String[] piece = location.split(",");
//                List numpiece = new ArrayList();
                for (String p : piece) {     
                    numpiece.add(p);
                }
            }
            //判断为不是上面两种情况的，比如“缺省”、“ ”，要按原样添加，缺失这一步会在下方步骤中被跳过
            else{
                klist.add(k);
                numpiece = Arrays.asList(datapiece.get(locationNum));//String转array
            }
            //这里开始将几种情况的处理结果一块操作，解析开的数字插入到原来的位置
            Collections.replaceAll(datapiece, datapiece.get(locationNum), numpiece.toString());//substring.replace("[", "").replace("]", "")
                //数据补全，格式一致
                for (int j = 0; j < numpiece.size(); j++) {
                    ArrayList finaldata = new ArrayList(); //最终全部铺开的数据
                    for (int pie = 0; pie < locationNum; pie++) {
                        finaldata.add(datapiece.get(pie));     
                    }
                    finaldata.add(numpiece.get(j));
                    //若excel表格添加信息列，需要改动以下信息
                    finaldata.add(datapiece.get(locationNum+1));
                    finaldata.add(datapiece.get(locationNum+2));
                    finaldata.add(datapiece.get(locationNum+3));
                    arraydata.add(finaldata);

                } 
        }
//        System.out.println(arraydata);
        Collections.sort(klist,Collections.reverseOrder());//将klist中元素翻转，从最后的index开始删除，防止每删除一个元素index就变换一次
        //遍历用迭代
        for (Iterator it = klist.iterator(); it.hasNext();) {
            int kl = (int) it.next();
//            System.out.println("kl:" + kl);
            data.remove(data.get(kl));
        }
//        System.out.println("删除后的data:" + data);
        for(Object ar : arraydata){
            data.add(ar);
        }
        
        System.out.println("##################");

        ArrayList temp = new ArrayList();//存放比较的列
        for (Object data1 : data) {
            List lii = new ArrayList();
            List li = (List) data1;
            lii.add(li.get(0));
            lii.add(li.get(1));
            lii.add(li.get(2));
            lii.add(li.get(3));
            lii.add(li.get(4));
            lii.add(li.get(5));
            lii.add(li.get(6));
            lii.add(li.get(7));
//            lii.add(li.get(8));
            temp.add(lii);
        }
//        System.out.println(temp.size());
        for (int m = 0; m < arraydata.size()-1; m++) {
            for (int du = m+1; du < arraydata.size(); du++) {             
                if (temp.get(m).equals(temp.get(du))) {
                    System.out.println(arraydata.get(du) + "已重复，请于excel表中核查！");
                    arraydata.remove(du);    
                    temp.remove(du);
                    du--;
                }      
            }        
        }
        System.out.println("**************************");
        
        arraydata.add(0,title);
        System.out.println(arraydata);
        return arraydata;   
    }
    
    //各列cell的正则表达式
    //系统代号是大写字母与数字的结合
    public static boolean isSerialNumber(String str) {
	String pattern = "(^[A-Z0-9]+$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    //序号是非零正整数

    public static boolean isNumber(String str) {
	String pattern = "(^\\+?[1-9][0-9]*$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    //中文名称中存在部分数字及字母
    public static boolean isChineseWord(String str) {
	String pattern = "(^[A-Za-z0-9-_\\u4e00-\\u9fa5]+$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }

    public static boolean isEnglishWord(String str) {
	String pattern = "(^[A-Za-z0-9-_\\s]+$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }

    //代号只能为大写字母
    public static boolean isAZ(String str) {
	String pattern = "(^[A-Z]+$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    //主体分区规则	
    public static boolean isMainPart(String str) {
	String pattern = "(^\\d{2}+[BIWU]+\\d+[A-Z])|(R|BS|LA|LB|BR|RB)|(^[\\u4e00-\\u9fa5]{0,}$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    //位置规则
    public static boolean isLocation(String str) {
	String pattern = "([1-4]+[,]*)*|((([0]+[1-9])|([1-3]\\d)|([4]+[0-8]))+[,]*)*|([-])|([1-4]+[-]+[1-4])|(FE|BL|ES)|((([0]+[1-9])|([1-3]\\d)|([4]+[0-8]))+[-]+(([0]+[1-9])|([1-3]\\d)|([4]+[0-8])))|(^[\\u4e00-\\u9fa5]{0,}$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    //最大台套数yes/no
    public static boolean isYes(String str) {
	String pattern = "(Yes|No)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
}
