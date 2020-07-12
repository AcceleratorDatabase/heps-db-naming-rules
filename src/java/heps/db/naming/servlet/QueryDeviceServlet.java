/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.servlet;

import heps.db.naming.api.DesignAPI;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author BaiYu
 */
@WebServlet(name="QueryDesign", urlPatterns={"/QueryDesign"})
public class QueryDeviceServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet QueryDeviceServlet</title>");  
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet QueryDeviceServlet at " + request.getContextPath () + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        int page;
            int rows;
            String pag=request.getParameter("page");
            if(Integer.parseInt(pag)==0){
   		page=1;
            }else{
   		page=Integer.parseInt(pag);
            }
            String row=request.getParameter("rows");
            if(row==null){
   		rows=10;
            }else{
   		rows=Integer.parseInt(row);
            }
        
       String result = new String();
       String count = null;
        String sysname ;
        String subname ;
        String devname ;
        String locname ;
        Integer bysys;
        Integer bysub;
        Integer bydevice;
        Integer bylocation;
        
        DesignAPI d = new DesignAPI();
        d.init();
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
            result = d.queryAll(page,rows);  
            count = d.queryAllCount();
        }else if (bysys == 1 && bysub == 0 && bydevice == 0 && bylocation == 0) {
            result = d.queryDeviceBySystemAcc(sysname,page,rows);
            count = d.queryDeviceBySystemAccCount(sysname);
        }else if (bysys == 0 && bysub == 1 && bydevice == 0 && bylocation == 0) {
            result = d.queryDeviceBySubsystem(subname,page,rows);
            count = d.queryDeviceBySubsystemCount(subname);
        }else if (bysys == 0 && bysub == 0 && bydevice == 1 && bylocation == 0) {
            result = d.queryDeviceByDevname(devname,page,rows);
            count = d.queryDeviceByDevnameCount(devname);
        }else if (bysys == 0 && bysub == 0 && bydevice == 0 && bylocation == 1) {
            result = d.queryDeviceByLocation(locname,page,rows);
            count = d.queryDeviceByLocationCount(locname);
        }else if (bysys == 1 && bysub == 1 && bydevice == 0 && bylocation == 0) {
            result = d.queryDeviceBySystemAccSubsystem(sysname, subname,page,rows);
            count = d.queryDeviceBySystemAccSubsystemCount(sysname, subname);
        }else if (bysys == 1 && bysub == 0 && bydevice == 1 && bylocation == 0) {
            result = d.queryDeviceBySystemAccDevname(sysname, devname,page,rows);
            count = d.queryDeviceBySystemAccDevnameCount(sysname, devname);
        }else if (bysys == 1 && bysub == 0 && bydevice == 0 && bylocation == 1) {
            result = d.queryDeviceBySystemAccLocation(sysname, locname,page,rows);
            count = d.queryDeviceBySystemAccLocationCount(sysname, locname);
        }else if (bysys == 0 && bysub == 1 && bydevice == 1 && bylocation == 0) {
            result = d.queryDeviceBySubsystemDevname(subname, devname,page,rows);
            count = d.queryDeviceBySubsystemDevnameCount(subname, devname);
        }else if (bysys == 0 && bysub == 1 && bydevice == 0 && bylocation == 1) {
            result = d.queryDeviceBySubsystemLocation(subname, locname,page,rows);
            count = d.queryDeviceBySubsystemLocationCount(subname, locname);
        }else if (bysys == 0 && bysub == 0 && bydevice == 1 && bylocation == 1) {
            result = d.queryDeviceByDevnameLocation(devname, locname,page,rows);
            count = d.queryDeviceByDevnameLocationCount(devname, locname);
        }else if (bysys == 1 && bysub == 1 && bydevice == 1 && bylocation == 0) {
            result = d.querySystemAccSubsystemDevname(sysname, subname, devname,page,rows);
            count = d.querySystemAccSubsystemDevnameCount(sysname, subname, devname);
        }else if (bysys == 1 && bysub == 1 && bydevice == 0 && bylocation == 1) {
            result = d.querySystemAccSubsystemLocation(sysname, subname, locname,page,rows);
            count = d.querySystemAccSubsystemLocationCount(sysname, subname, locname);
        }else if (bysys == 0 && bysub == 1 && bydevice == 1 && bylocation == 1) {
            result = d.querySubsystemDevnameLocation(subname, devname, locname,page,rows);
            count = d.querySubsystemDevnameLocationCount(subname, devname, locname);
        }else if (bysys == 1 && bysub == 0 && bydevice == 1 && bylocation == 1) {
            result = d.querySystemAccDevnameLocation(sysname, devname, locname,page,rows);
            count = d.querySystemAccDevnameLocationCount(sysname, devname, locname);
        }else if (bysys == 1 && bysub == 1 && bydevice == 1 && bylocation == 1) {
            result = d.querySystemAccSubsystemDevnameLocation(sysname, subname, devname, locname,page,rows);
            count = d.querySystemAccSubsystemDevnameLocationCount(sysname, subname, devname, locname);
        }

//        String[] header_referer = request.getHeader("Referer").split("/");
//        String lastpage = header_referer[header_referer.length - 1];
//        //System.out.println(header_referer[header_referer.length-1]);
//        if (lastpage.equals("device.jsp")||lastpage.equals("device.jsp#")) {
//            processRequest(request, response);
//        } else {
//            request.getSession().setAttribute("count", count);
//            request.getSession().setAttribute("devicerows", result);
//            request.getSession().setAttribute("devicevalue", "{\"total\":" + count + "," + "\"rows\":" + result + "}");
            request.getSession().setAttribute("system", sysname);
            request.getSession().setAttribute("subsystem", subname);
            request.getSession().setAttribute("devicename", devname);
            request.getSession().setAttribute("location", locname);
//            JSONObject obj = new JSONObject();
//            obj.put("total", count);
//            obj.put("rows", result);
//            out.print(obj);
            request.getRequestDispatcher("deviceresult.jsp").forward(request, response);
//        }
        d.destory();
        
        
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
