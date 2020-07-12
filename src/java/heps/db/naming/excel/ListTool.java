/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author BaiYu
 */
public class ListTool {
    
    private ArrayList arrayList;
    
    public ListTool(ArrayList arrayList) {
        this.arrayList = arrayList;
    }
    public ListTool() {
        this.arrayList = null;
    }
    
    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }
        
    /**
     *适用于[[],[],[]]格式的arraylist
     * @param name 列名
     * @return 列名对应index
     */
        public int getColNum(String name){        
        int colnumber = 0;
        if (this.arrayList != null && !"".equals(this.arrayList)) {
            Iterator it = this.arrayList.iterator();
            while (it.hasNext()) {
                colnumber = 0;
                ArrayList rowList = (ArrayList) it.next();
                Iterator itRow = rowList.iterator();
                while (itRow.hasNext()) {
                    String s = (String) itRow.next();
                    if(s.toLowerCase().contains(name.toLowerCase())||s.equals(name)){
                        return colnumber;
                 }
                 colnumber++;
                }
            }
        }
        return colnumber;
    }
    
    /**
     *适用于[]的arraylist
     * @param name 列名
     * @return 列名对应index
     */
        public int getOneRowColNum(String name){
        int colnumber = 0;
        if (this.arrayList != null && !"".equals(this.arrayList)) {
            Iterator it = this.arrayList.iterator();
            colnumber = 0;
            while (it.hasNext()) {     
                String s = (String) it.next();
                    if(s.toLowerCase().contains(name.toLowerCase())||s.equals(name)){
                        return colnumber;
                    }
                    colnumber++;
            }
        }
        return colnumber;
    }

}
