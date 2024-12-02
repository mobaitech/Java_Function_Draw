package practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class FunctionDraw {

    public static final int MYWIDTH = 1000;
    public static final int MYHEIGHT = 800;
    //主窗口尺寸
    public static final int MAXSIZE = 5000;
    //取最长直径为5000用来满足缩放需求

    public String function1 = "";
    public String function2 = "";
    public String function3 = "";
    //用来保存函数解析式
    public double myScale = 1;
    //缩放比例

    Choice functionChooser = new Choice();
    //下拉选择框，实现一次绘制多个函数图像

    boolean canDraw1 = false;
    boolean canDraw2 = false;
    boolean canDraw3 = false;
    //能否绘制函数参数，clear按键需要使用

    JFrame jf = new JFrame("Function_Draw");
    //主窗口

    TextField tips = new TextField("请输入函数表达式，回车保存，点击OK键绘制：");
    //提示用不可更改的文本域
    TextField functionField = new TextField();
    //函数输入文本域空间
    TextField showScale = new TextField();
    //提示文本域，函数输入文本域，缩放比例系数显示文本域

    Button okButton = new Button("OK");
    //开始绘制按键

    JButton biggerButton = new JButton("Enlarger");
    JButton smallerButton = new JButton("Reduce");
    JButton claerButton = new JButton("Clear");
    //放大，缩小，清空按钮

    MyCanvas mc = new MyCanvas();
    //自定义画布对象

    FunctionSolution fs = new FunctionSolution();
    //函数处理类对象

    ShowDialog sd = new ShowDialog();
    //对话框处理类对象

    public FunctionSave fSave = new FunctionSave();
    //创建函数保存类的对象：取名fSave是避免与fs同名重复

    //对象初始化及处理方法
    public void init() {

        tips.setColumns(31);
        tips.setForeground(Color.RED);
        tips.setEditable(false);
        //提示文本域列数，字体颜色，不可编辑

        functionField.setColumns(15);
        functionField.setText(function1);
        showScale.setColumns(13);
        //函数输入域的列数，和缩放比例显示信息列数

        functionChooser.add("function1");
        functionChooser.add("function2");
        functionChooser.add("function3");
        //System.out.println(functionChooser.getSelectedItem().toString());
        //增加三个选项

        JPanel jPanel1 = new JPanel();
        jPanel1.add(tips);
        jPanel1.add(functionChooser);
        jPanel1.add(functionField);
        okButton.setBackground(Color.GREEN);
        functionField.setForeground(Color.RED);
        jPanel1.add(okButton);
        jf.setLayout(new BorderLayout());
        //将jf的布局管理器设置为BorderLayout模式
        jf.add(jPanel1, BorderLayout.NORTH);
        //tips，functionField和okButton添加到jPanel1中

        JPanel jPanel2 = new JPanel();
        jPanel2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(mc, BorderLayout.CENTER);
        jf.add(jPanel2, BorderLayout.CENTER);
        //将画布mc添加到jPanel2中，再将jPanel设置为中部区域

        biggerButton.setBackground(Color.PINK);
        smallerButton.setBackground(Color.PINK);
        claerButton.setBackground(Color.PINK);
        //设置背景颜色
        biggerButton.setActionCommand("Enlarger");
        smallerButton.setActionCommand("Reduce");
        claerButton.setActionCommand("Clear");
        //设置ActionCommand，方便方法调用是getActionCommand

        addButtonListener(biggerButton);
        addButtonListener(smallerButton);
        addButtonListener(claerButton);
        // 添加按钮监听器

        JPanel jPanel3 = new JPanel();
        jPanel3.setBorder(BorderFactory.createLineBorder(Color.PINK));
        //设置边框颜色
        jPanel3.setLayout(new FlowLayout());
        //设置布局管理器：顺序水平放置
        jPanel3.add(biggerButton);
        jPanel3.add(smallerButton);
        jPanel3.add(claerButton);
        //添加三个按钮

        JLabel label = new JLabel(" scale:");
        jPanel3.add(label);
        jPanel3.add(showScale);
        jPanel1.add(jPanel3);
        //显示”scale“的一个简单便签

        jf.setSize(MYWIDTH, MYHEIGHT);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        //设置主窗口的大小，可见，默认关闭方式，位于windows窗口中间

        functionChooser.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String chosedFunction = functionChooser.getSelectedItem().toString();
                if (chosedFunction.equals("function1")) {
                    functionField.setText(function1);
                } else if (chosedFunction.equals("function2")) {
                    functionField.setText(function2);
                } else if (chosedFunction.equals("function3")) {
                    functionField.setText(function3);
                }
            }
        });
        //获取选择的选项：将文本域展示选项对应的函数

        functionField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //点击了ENTER键之后进行下列的处理
                    String chosedFunction = functionChooser.getSelectedItem().toString();
                    saveFunction(chosedFunction);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    int currentIndex = functionChooser.getSelectedIndex();
                    //System.out.println(currentIndex);  //打印查看
                    if (currentIndex > 0) {
                        functionChooser.select(currentIndex - 1);
                        if (currentIndex == 1) {
                            functionField.setText(function1);
                        }
                        if (currentIndex == 2) {
                            functionField.setText(function2);
                        }
                    }
                    // 当按下上键时，如果当前选项不是第一个选项，那么选项向上移动一位
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    int currentIndex = functionChooser.getSelectedIndex();
                    if (currentIndex < functionChooser.getItemCount() - 1) {
                        functionChooser.select(currentIndex + 1);
                        if (currentIndex == 0) {
                            functionField.setText(function2);
                        }
                        if (currentIndex == 1) {
                            functionField.setText(function3);
                        }
                    }
                    // 当按下下键时，如果当前选项不是最后一个选项，那么选项向下移动一位
                }
            }
        });
        //为函数输入文本域点击回车事件监控器
        //点击回车键和OK按钮之后进行解析，解析成功则保存函数表达式
        //添加上下左右键的检查，实现快速更换下拉选择框

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (function1.isEmpty() && function2.isEmpty() && function3.isEmpty()) {
                    //三个表达式均为空的处理
                    mc.repaint();
                    sd.showThreeFunctionsEmpty(jf);

                } else {
                    mc.repaint();
                    //每点击OK绘制的时候进行一次绘制保存
                    try {
                        fSave.saveImage(mc,function1,function2,function3);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    //处理空异常
                }
            }
        });
        //为OK按键田间事件监控器

        jf.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                if (notches < 0) {
                    if (myScale > 5.0 || myScale < 0.25) {
                        myScale = 1;
                        sd.showScaleMessageDialog(jf);
                    } else myScale *= 1.1;
                } else {
                    if (myScale > 5.0 || myScale < 0.25) {
                        myScale = 1;
                        sd.showScaleMessageDialog(jf);
                    } else
                        myScale /= 1.1;
                }
                mc.repaint();
            }
        });
        //为主窗口jf添加鼠标滚轮监控器，处理放大缩小事务
    }

    //有太多公共变量：Canvas不方便移动到外面去
    public class MyCanvas extends Canvas {
        @Override
        public void paint(Graphics g) {
            showScale.setText(Double.toString(myScale));

            //获取画笔
            Graphics2D g2 = (Graphics2D) g;
            g2.translate(MYWIDTH / 2, MYHEIGHT / 2 - 30); //主要是考虑到上面有个panel模块
            //将中心坐标轴移动到MYWIDTH/2 MYHEIGHT/2-30的位置处

            g2.setColor(Color.LIGHT_GRAY);
            for (int i = -MAXSIZE; i <= MAXSIZE; i += 20) {
                g2.drawLine(i, -MAXSIZE / 2, i, MAXSIZE / 2);  //-25到25左右
            }
            for (int i = -MAXSIZE / 2; i <= MAXSIZE / 2; i += 20) {
                g2.drawLine(-MAXSIZE / 2, i, MAXSIZE / 2, i);  //-19到19左右
            }
            //绘制小方格，不需要改动

            g2.scale(myScale, myScale);
            //设置缩放

            //绘制x轴和y轴
            g2.setColor(Color.BLACK);
            g2.drawLine(-MAXSIZE / 2, 0, MAXSIZE / 2, 0);
            g2.drawLine(0, -MAXSIZE / 2, 0, MAXSIZE / 2);

            //绘制刻度
            for (int i = -MAXSIZE / 2; i <= MAXSIZE / 2; i += 20) {
                g2.drawLine(i, -5, i, 5);
                g2.drawString(Integer.toString(i / 20), i, -10);
                //每20个像素点为1个刻度单位
            }
            for (int i = -MAXSIZE / 2; i <= MAXSIZE / 2; i += 20) {
                g2.drawLine(-5, i, 5, i);
                g2.drawString(Integer.toString(-i / 20), 10, i);
            }

            //绘制原点
            g2.setColor(Color.RED);
            g2.fillArc(-4, -4, 8, 8, 0, 360);
            //用宽8长8的实心小圆点覆盖0 0 坐标实现原点的绘制

            g2.setColor(Color.RED);
            if (canDraw1) {
                for (int x = -MAXSIZE * 2; x <= MAXSIZE * 2; x++) {
                    double y = fs.calculateFunction(function1, (double) x / 100);  //该函数这里传入的是一个double的值
                    if (y >= -MAXSIZE / 2 && y <= MAXSIZE / 2) {
                        //System.out.println("here x/5="+x/5+"  y*20="+y*20);
                        g2.fillOval(x / 5, -(int) (y * 100) / 5, 2, 2);
                        //区分x,y的实际值和图像的值    
                        //y的计算处/100之后又*20相当于x/5，保持了一样的缩放比例
                        //每拖动一次计算一次造成计算冗余
                    }
                }
            }
            //canDraw1参数为true是绘制函数，处理函数的缩放，实现于坐标轴相对应
            g2.setColor(Color.BLUE);
            if (canDraw2) {
                for (int x = -MAXSIZE * 2; x <= MAXSIZE * 2; x++) {
                    double y = fs.calculateFunction(function2, (double) x / 100);
                    if (y >= -MAXSIZE / 2 && y <= MAXSIZE / 2) {
                        g2.fillOval(x / 5, -(int) (y * 100) / 5, 2, 2);
                    }
                }
            }
            g2.setColor(Color.GREEN);
            if (canDraw3) {
                for (int x = -MAXSIZE * 2; x <= MAXSIZE * 2; x++) {
                    double y = fs.calculateFunction(function3, (double) x / 100);
                    if (y >= -MAXSIZE / 2 && y <= MAXSIZE / 2) {
                        g2.fillOval(x / 5, -(int) (y * 100) / 5, 2, 2);
                    }
                }
            }
            //三个绘制代码互不相干
        }
    }
    //自定义的画布类，实现对画布对象mc的绘制功能

    public void addButtonListener(JButton b) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Enlarger")) {
                    if (myScale > 5.0 || myScale < 0.25) {
                        myScale = 1;
                        sd.showScaleMessageDialog(jf);
                    } else
                        myScale *= 1.1;
                } else if (e.getActionCommand().equals("Reduce")) {
                    if (myScale > 5.0 || myScale < 0.25) {
                        myScale = 1;
                        sd.showScaleMessageDialog(jf);
                    } else
                        myScale /= 1.1;
                } else if (e.getActionCommand().equals("Clear")) {
                    myScale = 1;
                    functionField.setText("");
                    function1 = "";
                    function2 = "";
                    function3 = "";
                    canDraw1 = false;
                    canDraw2 = false;
                    canDraw3 = false;
                }
                mc.repaint();
            }
        });
        //自定义方法，为放大，缩小清空按钮添加监控事件，用一个方法去处理，减少代码冗余
    }

    public void saveFunction(String chosedFunction) { //传入chosedFunction
        if (chosedFunction.equals("function1")) {
            if (fs.getFunction(functionField.getText()).isEmpty()) {
                sd.showEmptyWarningDialog(jf);
                //考虑到主动将某个文本域置空的情况，输入为空那么就将对应函数置空
                canDraw1 = false;
                functionField.setText("");
                function1 = "";
            } else {
                function1 = fs.getFunction(functionField.getText());
                //获取文本域中输入的函数式子
                if (!new FunctionSolution().checkFunction(function1)) {
                    //若解析不通过
                    canDraw1 = false;
                    //弹出提示对话框
                    sd.showEnterWarningDialog(jf);
                    functionField.setText("");
                    function1 = "";
                    //输入了错误函数表达式之后自动将文本框清空
                    //同时表达式也置为空
                } else {
                    canDraw1 = true;  //解析通过
                    sd.showFunctionSavedDialog(jf, function1);
                }
            }
        }
        if (chosedFunction.equals("function2")) {
            if (fs.getFunction(functionField.getText()).isEmpty()) {
                sd.showEmptyWarningDialog(jf);
                canDraw2 = false;
                functionField.setText("");
                function2 = "";
            } else {
                function2 = fs.getFunction(functionField.getText());
                if (!new FunctionSolution().checkFunction(function2)) {
                    canDraw2 = false;
                    sd.showEnterWarningDialog(jf);
                    functionField.setText("");
                    function2 = "";
                } else {
                    canDraw2 = true;
                    sd.showFunctionSavedDialog(jf, function2);
                }
            }
        }
        if (chosedFunction.equals("function3")) {
            if (fs.getFunction(functionField.getText()).isEmpty()) {
                sd.showEmptyWarningDialog(jf);
                canDraw3 = false;
                functionField.setText("");
                function3 = "";
            } else {
                function3 = fs.getFunction(functionField.getText());
                if (!new FunctionSolution().checkFunction(function3)) {
                    canDraw3 = false;
                    sd.showEnterWarningDialog(jf);
                    functionField.setText("");
                    function3 = "";
                } else {
                    canDraw3 = true;
                    sd.showFunctionSavedDialog(jf, function3);
                }
            }
        }
    }

    public static void main(String[] args) {
        new FunctionDraw().init();
    }
    //main方法
}