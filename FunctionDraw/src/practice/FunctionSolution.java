package practice;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FunctionSolution {

    ShowDialog sd = new ShowDialog();

    public String getFunction(String str) {
        str = str.replace(" ", "");
        str = str.toLowerCase();
        if (str.length() >= 2 && str.charAt(0) == 'y' && str.charAt(1) == '=') {
            return str.substring(2);
        } else
            return str;
    }

    //从str中完全获取到函数表达式
    //去掉所有空格
    public boolean checkFunction(String function) {
        boolean check = true;
        try {
            Expression e = new ExpressionBuilder(function)
                    .variables("x")
                    .build()
                    .setVariable("x", 1);  // 用一个随机值替换变量，看是否能够成功计算
            //设置变量，构建函数，选取变量值进行计算
            double result = e.evaluate();
            System.out.println("函数表达式 y=" + function + " 有效，" + "函数已保存。");
        } catch (Exception e) {
            check = false;
            System.out.println("函数表达式 y=" + function + " 无效：" + e.getMessage());
            //可以在这一步弹出提示对话框
        }
        return check;
    }
    //检查函数表达是是否正确

    public double calculateFunction(String function, double x) {
        double result = 0;
        Expression e = new ExpressionBuilder(function)
                .variables("x")
                .build()
                .setVariable("x", x);
        result = e.evaluate();
        return result;
    }
    //处理计算已经知道x的时候 y的值
    //上面已经确定函数表达式是正确的了，那么直接计算即可
}