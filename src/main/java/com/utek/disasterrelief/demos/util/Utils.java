package com.utek.disasterrelief.demos.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Utils {
    private static final String ORG_NUMBER = "1099";

    public static Map<String , String> labelMap = new HashMap<String , String>(){
        {
//            put("宏观", "131");
//            put("黑色", "129");
//            put("能化", "32");
//            put("农产品", "33");
//            put("金属", "127");
//            put("期权", "133");

         put("宏观", "183");
		 put("黑色", "115");
	     put("能化","138");
	     put("农产品","175");
	     put("金属","135");
	     put("期权","178");
        }};
    public static boolean isBlank(String s) {
        if (s == null) {
            return true;
        }
        if (s.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static String getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static <T> List<T> subList(List<T> list, int page, int rows){
        List<T> listSort =new ArrayList<T>();
        if(list.size() == 0){
            return listSort;
        }
        int size=list.size();
        int pageStart=page==1?0:(page-1)*rows;;//截取的开始位置
        int pageEnd=size<page*rows?size:page*rows;
        if(size>pageStart){
            listSort =list.subList(pageStart, pageEnd);
        }
        return listSort;
    }
    public static String getCurrentTimeHour(){
        Calendar calendar = Calendar.getInstance();
        System.out.println("Current Date = " + calendar.getTime());
        //将小时数增加5-
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        System.out.println("Updated Date = " + calendar.getTime());
        Date date=calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
    public static Long dateToStamp(String s) {
        //设置时间模版
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date.getTime();
    }
    public static String getSysDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    public static String tranSysDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String tranSysDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String tranHHSysDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String tranSysDate(Date date,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    public static Date tranSysDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDate tranSysLocalDate(String date) {
        if(date.length()>10){
            date =date.substring(0,10);
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateParam = LocalDate.parse(date, df);
        return dateParam;
    }

    public static Date tranHHSysDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean CompareDate(Date date1, Date date2) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date date2 = sdf.parse(date3);
        if (date1.compareTo(date2)>0) {
            return true;
        }else {
            return false;
        }
    }


    public static String getYesterDay() {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date d=cal.getTime();

        SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(d);//获取昨天日期
    }

    public static String getAfterMonthDay() {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,+30);
        Date d=cal.getTime();

        SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(d);//获取昨天日期
    }

    public static Date getYesterDate() {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        return  cal.getTime();
    }




    public static <T> void setObject(T t, Map map,String timeFormat){
        Class cl = t.getClass();
        map.forEach((k,v)->{
            try {
                String name = k.toString().substring(0,1).toUpperCase() + k.toString().substring(1);
                Method mo = cl.getMethod("get"+name);
                //类型判断可扩展
                if(mo.getReturnType().getName().equals(Integer.class.getName())){
                    mo = cl.getMethod("set"+name,Integer.class);
                    v=Integer.parseInt(v.toString());
                    mo.invoke(t,v);
                }else if(mo.getReturnType().getName().equals(String.class.getName())){
                    mo = cl.getMethod("set"+name,String.class);
                    mo.invoke(t,v.toString());
                }else if(mo.getReturnType().getName().equals(Long.class.getName())){
                    mo = cl.getMethod("set"+name,Long.class);
                    v=Long.parseLong(v.toString());
                    mo.invoke(t,v);
                }else if(mo.getReturnType().getName().equals(Double.class.getName())){
                    mo = cl.getMethod("set"+name,Double.class);
                    mo.invoke(t,Double.parseDouble(v.toString()));
                }else if(mo.getReturnType().getName().equals(Date.class.getName())){
                    Date date =new SimpleDateFormat(timeFormat).parse(v.toString());
                    mo = cl.getMethod("set"+name,Date.class);
                    mo.invoke(t,date);
                }else if(mo.getReturnType().getName().equals(Float.class.getName())){
                    mo = cl.getMethod("set"+name,Float.class);
                    mo.invoke(t,Float.parseFloat(v.toString()));
                }
            } catch (NoSuchMethodException e) {
//                System.out.println("当前字段属性:"+k+",不在类中");
            } catch (IllegalAccessException | ParseException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
    public static String getSysYear() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return formatter.format(date);
    }
    public static String getBefore90day() {
        //过去90 天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -90);
        Date d = calendar.getTime();
        String day = format.format(d);
        return day;
    }

    public static String getBefore90day(Date date) {
        //过去90 天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -90);
        Date d = calendar.getTime();
        String day = format.format(d);
        return day;
    }

    //将时间戳转换为时间
    public static String stampToTime(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        long lt = new Long(s+"000");
        //将时间戳转换为时间
        Date date = new Date(lt);
        //将时间调整为yyyy-MM-dd HH:mm:ss时间样式
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToTime(Long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //将时间戳转换为时间
        Date date = new Date(s);
        //将时间调整为yyyy-MM-dd HH:mm:ss时间样式
        res = simpleDateFormat.format(date);
        return res;
    }

    public static Object getGetMethod(Object ob, String name) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (int i = 0; i < m.length; i++) {
            if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                return m[i].invoke(ob);
            }
        }
        return null;
    }
    public static void setValue(Object obj, Class<?> clazz, String filedName, Class<?> typeClass, Object value) {
        filedName = removeLine(filedName);
        String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        try {
            Method method = clazz.getDeclaredMethod(methodName, new Class[] { typeClass });
            method.invoke(obj, new Object[] { getClassTypeValue(typeClass, value) });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static String removeLine(String str) {
        if (null != str && str.contains("_")) {
            int i = str.indexOf("_");
            char ch = str.charAt(i + 1);
            char newCh = (ch + "").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i + 1), newCh);
            String newStr2 = newStr.replace("_", "");
            return newStr2;
        }
        return str;
    }


    /**
     * 通过class类型获取获取对应类型的值
     *
     * @param typeClass class类型
     * @param value     值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value) {
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value;
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else {
            return typeClass.cast(value);
        }
    }

    public static void createFile(File file) {
        if (file.exists()) {
            System.out.println("File exists");
        } else {
            System.out.println("File not exists, create it ...");
            //getParentFile() 获取上级目录(包含文件名时无法直接创建目录的)
            if (!file.getParentFile().exists()) {
                System.out.println("not exists");
                //创建上级目录
                file.getParentFile().mkdirs();
            }
            try {
                //在上级目录里创建文件
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将List<map>转换成List<java对象>
     * * @param list
     * * @param cls
     * * @param <T>
     * * @return
     */
    public static <T> List<T> getListObject(List<Map<String,Object>> list,Class<T> cls){
        List<T> paramList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)){
            for (Map<String, Object> map : list) {
                paramList.add(parseMapObject(map,cls));
            }
        }
        return paramList;
    }
    /**
     * 将map转换成java对象
     * @param paramMap
     * * @param cls
     * * @param <T>
     * * @return
     * */
    public static <T> T parseMapObject(Map<String,Object> paramMap, Class<T> cls) {
        return JsonUtil.parse(JsonUtil.toJSONString(paramMap),cls);
    }


    public static String getValue(Cell hssfCell) {
        hssfCell.setCellType(CellType.STRING);
        return hssfCell.getStringCellValue();
    }

    /**
     * 将超长的报文内容转为clob可以保存的句式
     * @param msgItemLength  每次截取的长度
     * @param msg  原始长报文
     */
    public static String convertClobMsg(int msgItemLength, String msg) {
        int i = msg.length() / msgItemLength;
        StringBuilder sb = new StringBuilder(); //不能给初始值，报文可能超长未知。
        for (int j = 0; j < 1 + i; j++) {
            int starSize = j * msgItemLength;
            int endSize = (j + 1) * msgItemLength;
            if (endSize <= msg.length()) { //  前面的分批截取
                String substring = msg.substring(starSize, endSize);
                sb.append("to_clob('").append(substring).append("')||");
            } else if (starSize != msg.length()) { // 最后一个批次，本次截取至结尾，如果startSize与字符串长度相等，就不再截取
                String substring = msg.substring(starSize);
                sb.append("to_clob('").append(substring).append("')||");
            }
        }
        return sb.append("to_clob('')").toString();
    }

    public static <T> List<List<T>> groupListByQuantity(List<T> list, int quantity) {
        if (list == null || list.size() == 0) {
            return null;
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Wrong quantity.");
        }

        List<List<T>> wrapList = new ArrayList<List<T>>();
        int count = 0;
        while (count < list.size()) {
            wrapList.add(new ArrayList<T>(list.subList(count, Math.min((count + quantity), list.size()))));
            count += quantity;
        }

        return wrapList;
    }

    public static String mapToString(Map<String, Object> map) {

        // 设置map的系统参数
        map.put("orgNumber", ORG_NUMBER);

        boolean bool = false;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> m : map.entrySet()) {
            if (bool) {
                sb.append("&");
            } else {
                bool = true;
            }
            sb.append(m.getKey());
            sb.append("=");
            sb.append(m.getValue());
        }
        return sb.toString();
    }
    public static  String DepartmentReplace(String s) {
        if (!isBlank(s)) {
            if (s.equals("公共客户1队")) {
                return "CZ-常州营业部";
            }
            if (s.equals("公共客户2队")) {
                return "FZ-福州营业部";
            }
            if (s.equals("公共客户3队")) {
                return "南宁";
            }
            if (s.equals("公共客户4队")) {
                return "杭州营业部";
            }
            if (s.equals("公共客户5队")) {
                return "GZ-广州营业部";
            }
            if (s.equals("公共客户6队")) {
                return "北京营业部";
            }
            if (s.equals("公共客户7队")) {
                return "SH-上海张杨路营业部";
            }
            if (s.equals("公共客户8队")) {
                return "北京vip";
            }
            if (s.equals("公共客户9队")) {
                return "QZ-泉州营业部";
            }
            if (s.equals("公共客户10队")) {
                return "CD-成都营业部";
            }
            if (s.equals("公共客户11队")) {
                return "莆田";
            }
            if (s.equals("公共客户12队")) {
                return "NJ-南京分公司";
            }
            if (s.equals("公共客户13队")) {
                return "HZYGL-杭州玉古路营业部";
            }
            if (s.equals("公共客户14队")) {
                return "深圳营业部";
            }
            if (s.equals("公共客户15队")) {
                return "SHLJZ-上海陆家嘴东路营业部";
            }
            if (s.equals("公共客户16队")) {
                return "JN-济南分公司";
            }
            if (s.equals("公共客户17队")) {
                return "财务部";
            }
            if (s.equals("公共客户18队")) {
                return "人力资源部";
            }
            if (s.equals("公共客户19队")) {
                return "信息技术中心";
            }
            if (s.equals("公共客户20队")) {
                return "汉口市场部";
            }
            if (s.equals("公共客户21队")) {
                return "创新研究部";
            }
            if (s.equals("公共客户22队")) {
                return "南昌";
            }
            if (s.equals("公共客户23队")) {
                return "深圳营业部";
            }
            if (s.equals("公共客户24队")) {
                return "浙江管理总部";
            }
            if (s.equals("公共客户25队")) {
                return "杭州营业部";
            }
            if (s.equals("公共客户26队")) {
                return "富田营业部";
            }
            if (s.equals("公共客户27队")) {
                return "AH-安徽分公司";
            }
            if (s.equals("公共客户28队")) {
                return "投资咨询";
            }
            if (s.equals("公共客户29队")) {
                return "风险子公司";
            }
            if (s.equals("公共客户30队")) {
                return "汉口";
            }
            if (s.equals("公共客户31队")) {
                return "汉口四部";
            }
            if (s.equals("公共客户32队")) {
                return "上海金融机构部";
            }
        } else {
            return "--";
        }
        return s;
    }
}
