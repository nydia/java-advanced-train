package com.nydia.mybatisplus.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MyDataPermissionHandler implements DataPermissionHandler {

    private static String TEST_1 = "com.baomidou.userMapper.selectByUsername";
    private static String TEST_2 = "com.baomidou.userMapper.selectById";
    private static String TEST_3 = "com.baomidou.roleMapper.selectByCompanyId";
    private static String TEST_4 = "com.baomidou.roleMapper.selectById";
    private static String TEST_5 = "com.baomidou.roleMapper.selectByRoleId";

    private static Map<String, String> sqlSegmentMap = new HashMap<String, String>() {
        {
            put(TEST_1, "username='123' or userId IN (1,2,3)");
            put(TEST_2, "u.state=1 and u.amount > 1000");
            put(TEST_3, "companyId in (1,2,3)");
            put(TEST_4, "username like 'abc%'");
            put(TEST_5, "id=1 and role_id in (select id from sys_role)");
        }
    };


    @Override
    public net.sf.jsqlparser.expression.Expression getSqlSegment(net.sf.jsqlparser.expression.Expression where, String mappedStatementId) {
        try {
            String sqlSegment = sqlSegmentMap.get(mappedStatementId);
            Expression sqlSegmentExpression = CCJSqlParserUtil.parseCondExpression(sqlSegment);
            if (null != where) {
                System.out.println("原 where : " + where.toString());
                if (mappedStatementId.equals(TEST_4)) {
                    // 这里测试返回 OR 条件
                    return new OrExpression(where, sqlSegmentExpression);
                }
                return new AndExpression(where, sqlSegmentExpression);
            }
            return sqlSegmentExpression;
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return null;
    }
}