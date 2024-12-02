package practice;

import javax.swing.*;

public class ShowDialog {
    public void showEnterWarningDialog(JFrame jf){
        String message = "你输入了错误的函数表达式！\n正确的函数表达式如下：\n" +
                "y=x\n" +
                "y=sinx 或 y=sin(x)\n"+
                "y=cosx 或 y=cos(x)\n"+
                "y=tanx\n"+
                "y=x^2\n" +
                "y=2^x\n"+
                "y=sinx+x^2+2^x\n"+
                "......";
        JOptionPane.showMessageDialog(jf, message, "WARNING", JOptionPane.WARNING_MESSAGE);
    }
    //处理表达式错误

    public void showEmptyWarningDialog(JFrame jf){
        JOptionPane.showMessageDialog(jf, "你输入的函数表达式为空！\n"+
                "空表达式已保存", "WARNING", JOptionPane.WARNING_MESSAGE);
    }
    //处理输入的表达式为空

    public void showScaleMessageDialog(JFrame jf){
        JOptionPane.showMessageDialog(jf, "尺寸过大/过小\n" +
                "scale>=0.25 && scale<=5", "WARNING", JOptionPane.WARNING_MESSAGE);
    }
    //处理放缩过度

    public  void showThreeFunctionsEmpty(JFrame jf){
        JOptionPane.showMessageDialog(jf, "你的三个函数表达式均为空！", "WARNING", JOptionPane.WARNING_MESSAGE);
    }
    //处理三个表达式均为空的情况

    public void showFunctionSavedDialog(JFrame jf,String function){
        JOptionPane.showMessageDialog(jf, "你的表达式:"+function+"有效，已保存", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
    }
    //弹出保存信息
}
//用来展示在绘制函数过程中遇见的各种问题
