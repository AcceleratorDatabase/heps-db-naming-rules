/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.excel;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *位置列的数据解析拆分
 * @author BaiYu
 */
public class SplitLocation {
    
    public ArrayList split(ArrayList deviceInfo){
        int locNum = 5;
        String location;
        ArrayList fullInfo = new ArrayList();
        location = deviceInfo.get(locNum).toString();
        //判断01-48或者1-4这种包含“-”的数据
            List numpiece = new ArrayList();
            //有的cell中只有“-”，看做空格处理
            if (location.equals("-")) {
                location = "";
            }
            if (location.contains("-")) {
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
                String[] piece = location.split(",");
//                List numpiece = new ArrayList();
                for (String p : piece) {     
                    numpiece.add(p);
                }
            }
            //判断为不是上面两种情况的，比如“缺省”、“ ”，要按原样添加，缺失这一步会在下方步骤中被跳过
            else{
                numpiece = Arrays.asList(deviceInfo.get(locNum));//String转array
            }
            Collections.replaceAll(deviceInfo, deviceInfo.get(locNum), numpiece.toString());
            for (int j = 0; j < numpiece.size(); j++) {
                ArrayList finaldata = new ArrayList(); //最终全部铺开的数据
                for (int pie = 0; pie < locNum; pie++) {
                    finaldata.add(deviceInfo.get(pie));     
                }
                finaldata.add(numpiece.get(j));
                    //若excel表格添加信息列，需要改动以下信息
                finaldata.add(deviceInfo.get(locNum+1));
                finaldata.add(deviceInfo.get(locNum+2));
                finaldata.add(deviceInfo.get(locNum+3));
                fullInfo.add(finaldata);

                } 
//            System.out.println(fullData);
            return fullInfo;
    }

}
