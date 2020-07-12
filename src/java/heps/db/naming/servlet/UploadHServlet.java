/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.servlet;

import heps.db.naming.api.FullInsertAPI;
import heps.db.naming.api.InsertAPI;
import heps.db.naming.excel.DataInsertDB;
import heps.db.naming.excel.ExcelTool;
import heps.db.naming.excel.PrintDuplicate;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author BaiYu
 */
@WebServlet(name="UploadHServlet", urlPatterns={"/UploadHServlet"})
public class UploadHServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
//            out.println("<title>Servlet UploadHServlet</title>");  
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet UploadHServlet at " + request.getContextPath () + "</h1>");
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
//        response.setContentType("application/json");
        
        //得到上传文件的保存目录，上传的文件保存在WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        Integer status = 0;
        int rowBegin = 2;
        int cellBegin = 0;
        
        PrintWriter out = response.getWriter();
        String savePath = this.getServletContext().getRealPath("/WEB-INF");
        String dir = "/upload";
        File file = new File(savePath,dir);
        //判断文件的上传路径是否存在
        if (!file.exists() && !file.isDirectory() ) {
            System.out.println(savePath + "目录不存在，需要创建！");
            //创建目录
            file.mkdir();
        }
        ArrayList<String> filename_new, filename_old;
        String name;
        filename_new = new ArrayList<>();
        filename_old = new ArrayList<>();
        try {
            //使用apache文件上传组件处理文件上传步骤
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名中文乱码
             upload.setHeaderEncoding(request.getCharacterEncoding());
            //3、判断提交的数据是否是上传表单数据
            
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
//            @SuppressWarnings("unchecked")
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                //如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String foname = item.getFieldName();
                    //解决普通输入项数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    System.out.println(foname + "=" + value);
                }else{//如果fileitem中封装的是上传文件
                    //得到上传文件的文件名
                    String filename = item.getName();
                    filename = filename.toLowerCase();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }                    
                    // 也许有的文件没有扩展名
                    String extName = "";
                    int position = filename.lastIndexOf(".");
                    if (position != -1) { //如果没有扩展名的话 position = -1
                        extName = filename.substring(position); //此扩展名格式：.xlsx(举例) 
                    }
                        //获取扩展名及当前的纳秒数整合为新的保存文件的名称
                        name = filename.substring(0, position) + "-" + System.nanoTime() + extName;
                        File nowfile = new File(file, name);
                        if (!nowfile.exists()) {
                            nowfile.createNewFile();
                        }
                        try {
                            item.write(nowfile);// 保存到指定的目录中去  
                        } catch (Exception ex) {
                            Logger.getLogger(UploadHServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        item.write(nowfile); //保存到指定的目录
                        filename_new.add(name);
                        filename_old.add(filename);                       
                        try {
                            Workbook wb = ExcelTool.getWorkbook(nowfile);
                            if (wb == null) {
                                response.getWriter().write("<script>parent.callback(false,'未找到Workbook','')</script>");
                            }
                            PrintDuplicate print = new PrintDuplicate(wb);
                            ArrayList data = print.checkAndGetSheetData(wb, rowBegin, cellBegin);
                            DataInsertDB ddb = new DataInsertDB(print.splitNum(wb, rowBegin, cellBegin,data ),nowfile);
                            status = ddb.allDataInsertDB();
                            if (status == 1) {
                                response.getWriter().write("<script>parent.callback(false,'全部上传','')</script>");
                            } else {
                                response.getWriter().write("<script>parent.callback(false,'未全部上传','')</script>");
                            }
                            ddb.deviceSubLocDataInsertDB();
                        } catch (Exception ex) {
                            Logger.getLogger(UploadHServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    
                }
                
            }
            
        } catch (Exception e) {
            response.getWriter().write("<script>parent.callback(false,'上传失败','')</script>");
        }
        response.getWriter().write("<script>parent.callback(true,'文件:" + filename_old.get(0) + "上传成功!','" + filename_new.get(0) + "')</script>");
        
        

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
