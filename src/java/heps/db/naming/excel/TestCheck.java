/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.excel;

import java.io.File;
import heps.db.naming.api.DesignAPI;
import heps.db.naming.entity.Device;
import heps.db.naming.entity.Location;
import heps.db.naming.entity.MoreThanNine;
import heps.db.naming.entity.Subsystem;
import heps.db.naming.entity.Accsystem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author BaiYu
 */
public class TestCheck {
    
    File file;
    
        
    public static void main(String[] args) {
        
        File file = new File(System.getProperty("user.dir")+"/【第一批上传】20191128 HEPS工程设备代号（加速器部分系统）-20200408.xlsx");
        
        
        Workbook wb = ExcelTool.getWorkbook(file);
        PrintDuplicate print = new PrintDuplicate(wb);
        int rowBegin = 2;
        int cellBegin = 0;

        ArrayList data = print.checkAndGetSheetData(wb, rowBegin, cellBegin);
        print.splitNum(wb, rowBegin, cellBegin, print.checkAndGetSheetData(wb, rowBegin, cellBegin));

    }

}
