/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.servlet;

import heps.db.naming.api.FullInsertAPI;
import heps.db.naming.api.InsertAPI;
import heps.db.naming.excel.SplitLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author BaiYu
 */
public class UpdateDeviceServlet extends HttpServlet {

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
//            out.println("<title>Servlet UpdateDeviceServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet UpdateDeviceServlet at " + request.getContextPath() + "</h1>");
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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String tableInfo;
        ArrayList deviceInfo = new ArrayList();
        tableInfo = (String)request.getParameter("hd1");
        Integer Id=Integer.parseInt(request.getParameter("hd2"));
        InsertAPI a = new InsertAPI();
        a.init();
        JSONObject infoJSONObject = JSONObject.fromObject(tableInfo);
        JSONArray infoJSONArray = infoJSONObject.getJSONArray("rows");
        if (infoJSONArray.size() > 0) {
            for (int i = 0; i < infoJSONArray.size(); i++) {
                JSONObject job = infoJSONArray.getJSONObject(i);
                if (i == 7 || i == 8) {
                    deviceInfo.add(job.get("value"));
                }else{
                    deviceInfo.add(job.get("value").toString().split("&")[0]);
                }
                
            }
        }
        
        SplitLocation spl = new SplitLocation();
        ArrayList fullInfo = spl.split(deviceInfo);
        FullInsertAPI fl = new FullInsertAPI();
        fl.updateDevice(fullInfo, Id);
        a.destory();
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<meta http-equiv=\"refresh\" content=\"3;url=index.jsp\">");
        out.println("<html>");
        out.println("<script language=\"javascript\"> ");
        out.println("var times=3;");
        out.println("function TimeClose()");
        out.println("{ window.setTimeout('TimeClose()', 1000); ");
        out.println("time.innerHTML =times+\"秒后跳转到首页\";");
        out.println("times--;}");
        out.println("</script>");
        out.println("<head>");
        out.println("<title>信息修改</title>");
        out.println("</head>");
        out.println("<body onLoad=\"TimeClose();\"  style=\"font-size:24px;text-align: center;margin-top:60px\";>");
        out.println("<h1 >设备信息修改成功</h1>");
        out.println("<div id=\"time\"></div> ");
        out.println("</body>");
        out.println("</html>");
        processRequest(request, response);
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
