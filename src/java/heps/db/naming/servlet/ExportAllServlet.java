/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.naming.servlet;

import heps.db.naming.api.NoPaginationAPI;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author root
 */
public class ExportAllServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ExportAllServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ExportAllServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        
        String result = new String();
        String sysname ;
        String subname ;
        String devname ;
        String locname ;
        Integer bysys;
        Integer bysub;
        Integer bydevice;
        Integer bylocation;
        
        NoPaginationAPI np = new NoPaginationAPI();
        np.init();
        sysname = request.getParameter("system");
        subname = request.getParameter("subsystem");
        devname = request.getParameter("devicename");
        locname = request.getParameter("location");
        
        if (sysname.equals("none")) {
            bysys = 0;
        }else{
            bysys = 1;
        }
        if (subname.equals("none")) {
            bysub = 0;
        }else{
            bysub = 1;
        }
        if (devname.equals("none")) {
            bydevice = 0;
        } else {
            bydevice = 1;
        }
        if (locname.equals("none")) {
            bylocation = 0;
        } else {
            bylocation = 1;
        }
        if (bysys == 0 && bysub == 0 && bydevice == 0 && bylocation == 0) {
            result = np.queryAll();  
        }else if (bysys == 1 && bysub == 0 && bydevice == 0 && bylocation == 0) {
            result = np.queryDeviceBySystemAcc(sysname);
        }else if (bysys == 0 && bysub == 1 && bydevice == 0 && bylocation == 0) {
            result = np.queryDeviceBySubsystem(subname);
        }else if (bysys == 0 && bysub == 0 && bydevice == 1 && bylocation == 0) {
            result = np.queryDeviceByDevname(devname);
        }else if (bysys == 0 && bysub == 0 && bydevice == 0 && bylocation == 1) {
            result = np.queryDeviceByLocation(locname);
        }else if (bysys == 1 && bysub == 1 && bydevice == 0 && bylocation == 0) {
            result = np.queryDeviceBySystemAccSubsystem(sysname, subname);
        }else if (bysys == 1 && bysub == 0 && bydevice == 1 && bylocation == 0) {
            result = np.queryDeviceBySystemAccDevname(sysname, devname);
        }else if (bysys == 1 && bysub == 0 && bydevice == 0 && bylocation == 1) {
            result = np.queryDeviceBySystemAccLocation(sysname, locname);
        }else if (bysys == 0 && bysub == 1 && bydevice == 1 && bylocation == 0) {
            result = np.queryDeviceBySubsystemDevname(subname, devname);
        }else if (bysys == 0 && bysub == 1 && bydevice == 0 && bylocation == 1) {
            result = np.queryDeviceBySubsystemLocation(subname, locname);
        }else if (bysys == 0 && bysub == 0 && bydevice == 1 && bylocation == 1) {
            result = np.queryDeviceByDevnameLocation(devname, locname);
        }else if (bysys == 1 && bysub == 1 && bydevice == 1 && bylocation == 0) {
            result = np.querySystemAccSubsystemDevname(sysname, subname, devname);
        }else if (bysys == 1 && bysub == 1 && bydevice == 0 && bylocation == 1) {
            result = np.querySystemAccSubsystemLocation(sysname, subname, locname);
        }else if (bysys == 0 && bysub == 1 && bydevice == 1 && bylocation == 1) {
            result = np.querySubsystemDevnameLocation(subname, devname, locname);
        }else if (bysys == 1 && bysub == 0 && bydevice == 1 && bylocation == 1) {
            result = np.querySystemAccDevnameLocation(sysname, devname, locname);
        }else if (bysys == 1 && bysub == 1 && bydevice == 1 && bylocation == 1) {
            result = np.querySystemAccSubsystemDevnameLocation(sysname, subname, devname, locname);
        }
        JSONObject obj = new JSONObject();
        obj.put("rows", result);
//        JSONArray infoJSONObject=JSONArray.fromObject(filetype);
        JSONArray infoJSONArray = obj.getJSONArray("rows");

        ServletOutputStream out = response.getOutputStream(); 
        HSSFWorkbook wb = new HSSFWorkbook(); 
        HSSFSheet sheet = wb.createSheet("设备名");
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell = null;
        String title[] = {"system","Chinesename","Englishname","devicename","subsystem","location","judge","another","remark"};
        for (int i = 0; i < title.length; i++) {
            cell = row0.createCell(i);
            cell.setCellValue(title[i]);
            
        }
        int rowNum = 1;
        String cellContent = "";
        for (int j = 0; j < infoJSONArray.size(); j++) {
            HSSFRow row = sheet.createRow(rowNum);
            JSONObject bodyObject = infoJSONArray.getJSONObject(j);
            for (int k = 0; k < title.length; k++) {
                cellContent = bodyObject.getString(title[k]);
                cell = row.createCell(k);
                cell.setCellValue(cellContent);   
            }
            
            String mainpart = bodyObject.getString("subsystem");
            String mainChinese = "(^[\\u4e00-\\u9fa5]{0,}$)";
            String mainAcc = "(^\\d{2}+[BIWU]+\\d+[A-Z])";
            String mainLine = "(R|BS|LA|LB|BR|RB)";
            if (Pattern.matches(mainChinese, mainpart)) {
                mainpart = "";
            }
            if (Pattern.matches(mainAcc, mainpart)) {
                mainpart = mainpart + "-";
            }
            if (Pattern.matches(mainLine, mainpart)) {
                mainpart = mainpart;
            }
            String dename = bodyObject.getString("devicename");
            String senumber = bodyObject.getString("judge");
            String anothername = bodyObject.getString("another");
            ArrayList newSenumber = new ArrayList();
            String anotherPattern = "([1-9]+[-]+[1-9])|((([0]+[1-9])|([1-9]+[0-9]))+[-]+(([0]+[1-9])|([1-9]+[0-9])))";
            String extractNum = "";
            if (senumber.equals("Yes") && anothername.equals("")) {
                newSenumber.add("01") ;
                newSenumber.add("02") ;
                newSenumber.add("...") ;
                newSenumber.add("99") ;
            }else if (senumber.equals( "No") && anothername.equals("")) {
                newSenumber.add("1") ;
                newSenumber.add("2") ;
                newSenumber.add("...") ;
                newSenumber.add("9") ;
            }else if(senumber.equals( "No") && !anothername.equals("")){
                Matcher m = Pattern.compile(anotherPattern).matcher(anothername);
                if (m.find()) {
                    extractNum = m.group(0);
                    String extractNumList[] = extractNum.split("-");
                    int num = Integer.parseInt(extractNumList[1])-Integer.parseInt(extractNumList[0])+1;
                    for (int i = 0; i < num; i++) {
                        newSenumber.add(i, Integer.toString(Integer.parseInt(extractNumList[0])+i));
                    }
                }
            }else if(senumber.equals("Yes") && !anothername.equals("")){
                Matcher m = Pattern.compile(anotherPattern).matcher(anothername);
                if (m.find()) {
                    extractNum = m.group(0);
                    String extractNumList[] = extractNum.split("-");
                    int num = Integer.parseInt(extractNumList[1])-Integer.parseInt(extractNumList[0])+1;
                    if (Integer.parseInt(extractNumList[0])<10) {
                        for(int k=0; k<num; k++){
                            newSenumber.add(k, Integer.toString(Integer.parseInt(extractNumList[0]) + k));
                            if(Integer.parseInt(newSenumber.get(k).toString())<10){
                                newSenumber.set(k, "0" + newSenumber.get(k).toString());
                            }
                        }
                    }else{
                        for(int k=0; k<num; k++){
                            newSenumber.add(k, Integer.toString(Integer.parseInt(extractNumList[0]) + k));
                        }
                    }     
                }
            }
            
            String locanumber = bodyObject.getString("location");
            if (Pattern.matches(mainChinese, locanumber) || locanumber.equals("-") || locanumber.equals("")){
                locanumber = ""; 
            }
            
            ArrayList sss = new ArrayList();
            ArrayList ss = new ArrayList();
            ArrayList ssM = new ArrayList();
            for(int i=0; i<newSenumber.size(); i++){
                ss.add(mainpart+locanumber+dename+newSenumber.get(i).toString());
                String reamrkMag = bodyObject.getString("remark");
                if(reamrkMag.contains("H") && reamrkMag.contains("磁铁名")){
                     ssM.add(mainpart+locanumber+dename+newSenumber.get(i).toString()+"H");
                }
                if(reamrkMag.contains("V") && reamrkMag.contains("磁铁名")){
                    ssM.add(mainpart+locanumber+dename+newSenumber.get(i).toString()+"V");
                }
                if(reamrkMag.contains("S") && reamrkMag.contains("磁铁名")){
                    ssM.add(mainpart+locanumber+dename+newSenumber.get(i).toString()+"S");
                }
            }
            sss.add(ss);
            if (ssM!=null) {
                sss.add(ssM);
            }
            
            row.createCell(title.length).setCellValue(sss.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
            
            rowNum ++;
        }
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.TAN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        String newtitle[] = {"系统代号","设备/部件中文名称","设备/部件英文名称","设备/部件名称代号","所在主体分区","位置","同一周期内个数>9","别名","备注","生成设备名称"};
        for (int i = 0; i < newtitle.length; i++) {
            cell = row0.createCell(i);
            cell.setCellValue(newtitle[i]);    
            cell.setCellStyle(style);
//            sheet.autoSizeColumn(i);
        }
        sheet.setColumnWidth(0, 2488);
        sheet.setColumnWidth(1, 4536);
        sheet.setColumnWidth(2, 5816);
        sheet.setColumnWidth(3, 4280);
        sheet.setColumnWidth(4, 3256);
        sheet.setColumnWidth(5, 1976);
        sheet.setColumnWidth(6, 4280);
        sheet.setColumnWidth(7, 3256);
        sheet.setColumnWidth(8, 4280);
        sheet.setColumnWidth(9, 12984);
        String fileName = "HEPS_naming";
        response.setHeader("Content-Disposition", "attachment;filename="+ fileName +".xls");
        wb.write(out);
        out.flush();
        np.destory();
//        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
