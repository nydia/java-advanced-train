package com.nydia.memorydb.h2;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;

/**
 * @author lvhq
 * @date 2024.07.03
 */
@Slf4j
public class H2Demo {

    /**
     * 简单计算测试
     */
    public static void temp() {
        try {
            Connection con = H2Connect.connect();
            assert con != null;
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT 1+1");
            if (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    /**
     * 测试函数
     */
    public static void funcTest() {
        try {
            Connection con = H2Connect.connect();
            assert con != null;
            Statement stm = con.createStatement();

            // >>>>>>>>>>>>>>> 基础运算

            // 基础运算： +
            ResultSet rs = stm.executeQuery("SELECT 1+1");
            if (rs.next()) {
                System.out.println("基础运算： + , SELECT 1+1 = " + rs.getInt(1));
            }

            // 基础运算： -
            rs = stm.executeQuery("SELECT 2>1");
            if (rs.next()) {
                System.out.println("基础运算： - , SELECT 2>1 = " + rs.getBoolean(1));
            }

            // 基础运算： x不支持，可以用 2*2
            rs = stm.executeQuery("SELECT 2*2");
            if (rs.next()) {
                System.out.println("基础运算： x不支持，可以用 2*2 = " + rs.getInt(1));
            }

            // 基础运算： /
            rs = stm.executeQuery("SELECT 4/2");
            if (rs.next()) {
                System.out.println("基础运算： / , SELECT 4/2 = " + rs.getInt(1));
            }

            // >>>>>>>>>>>>>>> 逻辑判断

            //＝、<> 、 <、 >、 <=、 >=

            // 逻辑判断： =
            rs = stm.executeQuery("SELECT 1=2");
            if (rs.next()) {
                System.out.println("逻辑判断： = , SELECT 1=2 = " + rs.getBoolean(1));
            }

            // 逻辑判断： <>
            rs = stm.executeQuery("SELECT 1<>2");
            if (rs.next()) {
                System.out.println("逻辑判断： <> , SELECT 1<>2 = " + rs.getBoolean(1));
            }

            // 逻辑判断： <
            rs = stm.executeQuery("SELECT 1<2");
            if (rs.next()) {
                System.out.println("逻辑判断： < , SELECT 1<2 = " + rs.getBoolean(1));
            }

            // 逻辑判断： >
            rs = stm.executeQuery("SELECT 1>2");
            if (rs.next()) {
                System.out.println("逻辑判断： > , SELECT 1>2 = " + rs.getBoolean(1));
            }

            // 逻辑判断： <=
            rs = stm.executeQuery("SELECT 1<=2");
            if (rs.next()) {
                System.out.println("逻辑判断： > , SELECT 1<=2 = " + rs.getBoolean(1));
            }

            // 逻辑判断： >=
            rs = stm.executeQuery("SELECT 1>=2");
            if (rs.next()) {
                System.out.println("逻辑判断： > , SELECT 1>=2 = " + rs.getBoolean(1));
            }

            // >>>>>>>>>>>>>>> 函数

            //SUM （求和）、AVG （平均值） 、MAX （最大值）、MIN （最小值）、MOD（求余）、COUNT（计数）、COUNTA（非空白单元格计数）、SUMPRODUCT（加权平均）、MEDIAN（中位数）、QUARTILE(分位数)、排序后取数

            // 函数： SUM
            rs = stm.executeQuery("SELECT sum(1+1)");
            if (rs.next()) {
                System.out.println("函数： SUM , SELECT sum(1+1) = " + rs.getInt(1));
            }

            // 函数： AVG()
            rs = stm.executeQuery("SELECT AVG(age) from EMP");
            if (rs.next()) {
                System.out.println("函数： AVG , SELECT AVG(age) from EMP = " + rs.getInt(1));
            }

            // 函数： MAX()
            rs = stm.executeQuery("SELECT MAX(age) from EMP");
            if (rs.next()) {
                System.out.println("函数： MAX , SELECT MAX(age) from EMP = " + rs.getInt(1));
            }

            // 函数： MOD() 求余
            rs = stm.executeQuery("SELECT MOD(100, 23)");
            if (rs.next()) {
                System.out.println("函数： MOD 求余 , SELECT MOD(100, 23) = " + rs.getInt(1));
            }

            // 函数： COUNT()
            rs = stm.executeQuery("SELECT COUNT(age) from EMP");
            if (rs.next()) {
                System.out.println("函数： COUNT , SELECT COUNT(age) from EMP = " + rs.getInt(1));
            }

            // 函数： COUNTA（非空白单元格计数）
            System.out.println("函数： COUNTA（非空白单元格计数)(可能来源于Excel) 不支持");

            // 函数：SUMPRODUCT（加权平均）
            System.out.println("函数： SUMPRODUCT（加权平均） 不支持");

            // 函数：MEDIAN（中位数）
            rs = stm.executeQuery("SELECT MEDIAN(age) from EMP");
            if (rs.next()) {
                System.out.println("函数： MEDIAN（中位数） , SELECT MEDIAN(age) from EMP = " + rs.getInt(1));
            }

            // 函数：QUARTILE(分位数)
            System.out.println("函数：QUARTILE(分位数)（可能来源于Excel） 不支持");

            // 函数：排序后取数
            rs = stm.executeQuery("select id from emp order by id  desc limit 2");
            if (rs.next()) {
                System.out.println("函数： 排序后取数(怎么保证是返回一个结果) , select id from emp order by id  desc limit 2 = " + rs.getInt(1));
            }

            //IN（在集合中）、 CONTAINS（是否有交集）、 LIKE（包含关键字）、ISBLANK （判断是否为空）

            // 函数：IN（在集合中）
            rs = stm.executeQuery("SELECT 1 in(1,2)");
            if (rs.next()) {
                System.out.println("函数：IN（在集合中） , SELECT 1 in(1,2) = " + rs.getBoolean(1));
            }
            rs = stm.executeQuery("SELECT 3 in(1,2)");
            if (rs.next()) {
                System.out.println("函数：IN（在集合中） , SELECT 3 in(1,2) = " + rs.getBoolean(1));
            }

            // 函数：CONTAINS（是否有交集）
            System.out.println("函数：CONTAINS（是否有交集） 不支持");

            // 函数：LIKE（包含关键字）
            rs = stm.executeQuery("select '测试'  like '测%'");
            if (rs.next()) {
                System.out.println("函数：IN（在集合中） , select '测试'  like '测%' = " + rs.getBoolean(1));
            }
            rs = stm.executeQuery("select '测试'  like '测2%'");
            if (rs.next()) {
                System.out.println("函数：IN（在集合中） , select '测试'  like '测2%' = " + rs.getBoolean(1));
            }

            // 函数：ISBLANK （判断是否为空）
            rs = stm.executeQuery("select 'ss' isblank");
            if (rs.next()) {
                System.out.println("函数：ISBLANK （判断是否为空）(返回具体的值，需要手工逻辑判断) , select 'ss' isblank = " + rs.getString(1));
            }
            rs = stm.executeQuery("select '' isblank");
            if (rs.next()) {
                System.out.println("函数：ISBLANK （判断是否为空）(返回具体的值，需要手工逻辑判断) , select '' isblank = " + rs.getString(1));
            }

            // ROUND (四舍五入保留n位小数)、ROUNDUP (向上取整)、ROUNDDOWN(向下取整)

            // 函数：ROUND (四舍五入保留n位小数)
            rs = stm.executeQuery("select ROUND(1111.1111, 2)");
            if (rs.next()) {
                System.out.println("函数：ROUND (四舍五入保留n位小数) , select ROUND(1111.1111, 2) = " + rs.getString(1));
            }

            // 函数：ROUNDUP (向上取整)
            rs = stm.executeQuery("select CEILING(1111.23545)");
            if (rs.next()) {
                System.out.println("函数：ROUNDUP (向上取整) 没有这个函数，可以用CEILING替代 , select CEILING(1111.23545) = " + rs.getInt(1));
            }

            // 函数：ROUNDDOWN(向下取整)
            rs = stm.executeQuery("select floor(1111.23545)");
            if (rs.next()) {
                System.out.println("函数：ROUNDDOWN(向下取整) 没有这个函数，可以用FLOOR替代 , select floor(1111.23545) = " + rs.getInt(1));
            }

            //POWER（幂）、ABS(a)（绝对值）

            // 函数：POWER（幂）
            rs = stm.executeQuery("select POWER(2,3)");
            if (rs.next()) {
                System.out.println("函数：POWER（幂） , select POWER(2,3) = " + rs.getInt(1));
            }

            // 函数：ABS(a)（绝对值）
            rs = stm.executeQuery("select ABS(-100)");
            if (rs.next()) {
                System.out.println("函数：ABS(a)（绝对值） , select ABS(-100) = " + rs.getInt(1));
            }

            //函数： NOT（求反值）
            rs = stm.executeQuery("select NOT(false)");
            if (rs.next()) {
                System.out.println("函数：ABS(a)（绝对值）仅仅针对bool值 , select NOT(false) = " + rs.getInt(1));
            }
            rs = stm.executeQuery("select NOT(true)");
            if (rs.next()) {
                System.out.println("函数：ABS(a)（绝对值）仅仅针对bool值 , select NOT(true) = " + rs.getInt(1));
            }

            // CONCATENATE、SUBSTITUTE、LOWER、UPPER、FIND、LEN、LEFT、RIGHT、MID、TRIM等（文本函数）

            // 函数：QUARTILE
            System.out.println("函数：CONCATENATE(字符连接)（可能来源于Excel） 不支持");

            // 函数：SUBSTITUTE   excel : SUBSTITUTE(text, old_text, new_text, [instance_num])
            System.out.println("函数：SUBSTITUTE(字符串替换)（可能来源于Excel） 不支持");

            // 函数：LOWER
            rs = stm.executeQuery("select LOWER('UPPD')");
            if (rs.next()) {
                System.out.println("函数：LOWER , select LOWER('UPPD') = " + rs.getString(1));
            }

            // 函数：UPPER
            rs = stm.executeQuery("select UPPER('uppd')");
            if (rs.next()) {
                System.out.println("函数：UPPER , select UPPER('uppd') = " + rs.getString(1));
            }

            // 函数：FIND
            System.out.println("函数：FIND（可能来源于mongodb） 不支持, 可考虑使用 REGEXP_LIKE 函数替代");

            // 函数：LEN
            rs = stm.executeQuery("select length('rule')");
            if (rs.next()) {
                System.out.println("函数：LEN 不支持，可考虑使用 LENGTH替代, select length('rule') = " + rs.getInt(1));
            }

            // 函数：LEFT
            rs = stm.executeQuery("select LEFT('rule', 2)");
            if (rs.next()) {
                System.out.println("函数：LEFT, select LEFT('rule', 2) = " + rs.getString(1));
            }

            // 函数：RIGHT
            rs = stm.executeQuery("select RIGHT('rule', 2)");
            if (rs.next()) {
                System.out.println("函数：RIGHT, select RIGHT('rule', 2) = " + rs.getString(1));
            }

            // 函数：MID
            System.out.println("函数：MID 不支持");

            // 函数：TRIM
            rs = stm.executeQuery("select TRIM('   ru le   ')");
            if (rs.next()) {
                System.out.println("函数：TRIM(去除两端的空字符串), select TRIM('   ru le   ') = " + rs.getString(1));
            }

            //TODAY、DAY、MONTH、YEAR、NOW、WEEKDAY、DATEIF、BOMONTH、EOMONTH、BOYEAR、EOYEAR、ADDDAYS、ADDMONTHS、ADDYEARS、DAYSBETWEEN、MONTHSBETWEEN、YEARSBETWEEN等（日期函数）

            //函数：TODAY
//            rs = stm.executeQuery("select TODAY");
//            if (rs.next()) {
//                System.out.println("函数：TODAY, select TODAY() = " + rs.getString(1));
//            }
            System.out.println("函数：TODAY，H2数据库支持，但是jdbc里面不支持");

            //函数：DAY
            rs = stm.executeQuery("select  DAY('2024-01-01')");
            if (rs.next()) {
                System.out.println("函数：DAY, select  DAY('2024-01-01') = " + rs.getString(1));
            }

            //函数：MONTH
            rs = stm.executeQuery("select  MONTH('2024-01-01')");
            if (rs.next()) {
                System.out.println("函数：MONTH, select  MONTH('2024-01-01') = " + rs.getString(1));
            }

            //函数：YEAR
            rs = stm.executeQuery("select  YEAR('2024-01-01')");
            if (rs.next()) {
                System.out.println("函数：YEAR, select  YEAR('2024-01-01') = " + rs.getString(1));
            }

            //函数：NOW
            rs = stm.executeQuery("select  NOW()");
            if (rs.next()) {
                System.out.println("函数：YEAR, select NOW() = " + rs.getString(1));
            }

            //函数：WEEKDAY
            rs = stm.executeQuery("select DAY_OF_WEEK('2024-01-01')");
            if (rs.next()) {
                System.out.println("函数：WEEKDAY 不支持，可以用DAY_OF_WEEK代替 , select DAY_OF_WEEK('2024-01-01') = " + rs.getString(1));
            }

            //函数：DATEIF（计算两个日期之间的差值）
            System.out.println("函数：DATEIF（计算两个日期之间的差值），不支持，可能来源于excel");

            //函数：BOMONTH
            System.out.println("函数：BOMONTH，不支持，作用未知");

            //函数：EOMONTH（返回某个月份最后一天的序列号）
            System.out.println("函数：EOMONTH(start_date, months)（返回某个月份最后一天的序列号），不支持，可能来源于excel");

            //函数：BOYEAR
            System.out.println("函数：BOYEAR，不支持，作用未知(猜测可能是返回某个年的第一天的序列号)");

            //函数：EOYEAR
            System.out.println("函数：EOYEAR，不支持，作用未知(猜测可能是返回某个年的最后一天的序列号)");

            //函数：ADDDAYS
            rs = stm.executeQuery("select DATEADD('DAY',1,'2024-01-01')");
            if (rs.next()) {
                System.out.println("函数：ADDDAYS 不支持，可以用DATEADD代替 , select DATEADD('DAY',1,'2024-01-01') = " + rs.getString(1));
            }

            //函数：ADDMONTHS
            rs = stm.executeQuery("select DATEADD('MONTH',1,'2024-01-01')");
            if (rs.next()) {
                System.out.println("函数：ADDMONTHS 不支持，可以用DATEADD代替 , select DATEADD('MONTH',1,'2024-01-01') = " + rs.getString(1));
            }

            //函数：ADDYEARS
            rs = stm.executeQuery("select DATEADD('YEAR',1,'2024-01-01')");
            if (rs.next()) {
                System.out.println("函数：ADDYEARS 不支持，可以用DATEADD代替 , select DATEADD('YEAR',1,'2024-01-01') = " + rs.getString(1));
            }

            //函数：DAYSBETWEEN
            System.out.println("函数：DAYSBETWEEN，不支持，作用未知(猜测可能是返回两个日期的间隔天数)");

            //函数：MONTHSBETWEEN
            System.out.println("函数：MONTHSBETWEEN，不支持，作用未知(猜测可能是返回两个日期的间隔月数)");

            //函数：YEARSBETWEEN
            System.out.println("函数：YEARSBETWEEN，不支持，作用未知(猜测可能是返回两个日期的间隔年数)");

            stm.close();
            con.close();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }


    /**
     * 创建table & 插入数据
     */
    public static void createTableAndInsertDataWithJdbc() {
        try {
            Connection conn = H2Connect.connect();
            assert conn != null;

            //创建表
            System.out.println("create table  >>>>>>>>>");
            Statement stmSchema = conn.createStatement();
            String sql = "CREATE TABLE  EMP " +
                    "(id INTEGER not null, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmSchema.executeUpdate(sql);
            stmSchema.close();

            System.out.println("insert data  >>>>>>>>>");

            // 插入数据
            Statement stmtInsert = conn.createStatement();
            String sqlInsert = "insert into emp (id,first,last,age) " + "values (100, 'zara', 'ali', 18)";
            sqlInsert += ",(101, 'mahnaz', 'fatma', 25)";
            sqlInsert += ",(102, 'mahnaz', 'fatma', 25)";
            sqlInsert += ",(103, 'zaid', 'khan', 30)";
            sqlInsert += ",(104, 'sumit', 'mittal', 28)";

            stmtInsert.executeUpdate(sqlInsert);

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 性能测试:
     * 10000条：1秒以内 （可以达到0.3秒）
     * 100000条：17秒
     */
    public static void performance() {
        try {
            Connection conn = H2Connect.connect();
            assert conn != null;

            //创建表
            System.out.println("create table  >>>>>>>>>");
            Statement stmSchema = conn.createStatement();
            String sql = "CREATE TABLE  EMP " +
                    "(id INTEGER not null, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmSchema.executeUpdate(sql);
            stmSchema.close();

            // 插入数据
            System.out.println("insert data  >>>>>>>>>");
            long startTime = new Date().getTime();//开始时间
            Statement stmtInsert = conn.createStatement();
            String sqlInsert = "insert into emp (id,first,last,age) ";
            int totalNum = 10000;//总条数
            for (int i = 1; i <= totalNum; i++) {
                if (i == 1) {
                    sqlInsert += " values(" + i + ", 'mahnaz', 'fatma', 25)";
                } else {
                    sqlInsert += " ,(" + i + ", 'mahnaz', 'fatma', 25)";
                }
            }
            stmtInsert.executeUpdate(sqlInsert);
            long endTime = new Date().getTime();//结束时间

            System.out.println("插入" + totalNum + "条数据耗时：" + (endTime - startTime));

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义函数
     */
    public static void extFun() {
        try {
            Connection conn = H2Connect.connect();
            assert conn != null;

            //创建自定义函数
            System.out.println("创建自定义函数  >>>>>>>>>");
            Statement statement = conn.createStatement();
            String sql = "CREATE ALIAS if not exists vhr_uuid FOR \"com.ciicsh.vhr.demo.memorydb.H2ExtFun.vhrUuid\"";
            statement.executeUpdate(sql);

            //使用自定义函数
            ResultSet rs = statement.executeQuery("select vhr_uuid()");
            if (rs.next()) {
                System.out.println("自定义函数调用 vhr_uuid() = " + rs.getString(1));
            }

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //1. 简单函数测试
        temp();

        //2. 创建表和插入数据测试
        //createTableAndInsertDataWithJdbc();

        //3. 性能测试
        //performance();

        //4. 函数测试
        //createTableAndInsertDataWithJdbc();
        //funcTest();

        //5. 自定义函数
        //extFun();
    }

}
