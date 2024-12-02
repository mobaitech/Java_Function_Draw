> Java课设项目，虽不复杂，却意义深远。

## 功能说明

### 基本功能

1. **选择与输入函数**
   - 在下拉选项框中选择需要处理的函数编号。
   
   - 在文本框中输入函数表达式后，按回车键保存函数信息。
   
   - 点击 OK 按钮绘制函数图像。
   
2. **支持的函数表达式**
   - 函数表达式可以是 y=f(x) 的形式，也可以直接使用 f(x) 的形式。
   
3.	**缩放功能**

   - 点击 Enlarge 按钮可放大图像，点击 Reduce 按钮可缩小图像。

   - 也可以通过鼠标滚轮调整缩放比例：

   - 向上滚动放大图像。

   - 向下滚动缩小图像。

4. **清空图像**

   - 点击 Clear 按钮清空当前绘制的函数图像，并重置缩放比例。

5. **多函数绘制**

   - 通过上下箭头键切换要绘制的函数编号。

   - 支持同时绘制最多三个有效函数。

6. **验证与清除函数**

   - 根据弹出对话框确认函数表达式是否有效。

   - 如需清除某个函数，在对应选项框的文本区域中删除内容或输入空格即可。

7. **保存图像**

   - 点击 OK 按钮后，不仅绘制函数图像，还会将图像保存到项目目录下的 images 文件夹中，文件以日期和时间命名以便区分。

### 注意事项

1. **函数表达式书写规则**

   - 系统支持省略乘号：如 xx 等同于 x^2，xxx 等同于 x^3。

   - 对于三角函数，sinx 等同于 sin(x)，但表达式 sinx+1 视为 sin(x+1)，与 sin(x)+1 不同。

   - 为确保系统正确解析函数，建议严格书写函数表达式。

2. **支持的函数形式**

   - 可以使用 y=f(x) 或 f(x) 表达形式，均可被正确识别。

## 项目流程

1. **使用 Java 绘图 AWT 和 SWING**
   - 本项目使用 Java 的 AWT 和 SWING 库进行绘图操作。

2. **插件主窗口**
   - 创建主窗口 `JFrame jf`。

3. **设置布局管理器**
   - 将主窗口的布局管理器设置为 `BorderLayout()`。

4. **创建下拉选择框**
   - 创建下拉选择框 `functionChooser`，包含三个选项 `function1`、`function2` 和 `function3`，用于区分三个函数的输入和保存信息。

5. **创建容器 jPanel1**
   - 创建容器 `jPanel1`，并向其中添加两个文本域：
     - `tips`：不可更改的提示文本域。
     - `functionField`：函数输入文本域。
   - 在两个文本域中间添加下拉选择框 `functionChooser`，用于解析和绘制输入的文本。
   - 添加一个 `okButton` 按钮，并为其添加监控器，当按下 `okButton` 时解析并绘制函数。

6. **将 jPanel1 添加到主窗口**
   - 将 `jPanel1` 添加到主窗口 `jf` 的 SOUTH 位置。

7. **创建容器 jPanel2**
   - 创建容器 `jPanel2`，并向其中添加一个画布对象 `mc`（`MyCanvas` 继承自 `Canvas`）。
   - 将 `jPanel2` 置于主窗口 `jf` 的 CENTER 位置。

8. **创建容器 jPanel3**
   - 创建容器 `jPanel3`，并向其中添加三个按钮：
     - `biggerButton`：放大按钮。
     - `smallerButton`：缩小按钮。
     - `clearButton`：清空按钮。
   - 为这三个按钮创建方法添加监控器，分别实现放大、缩小和清空功能。通过一个缩放比例参数 `scale` 实现缩放，并向 `jPanel3` 添加文本域 `scaleShow`，实时展示用户调节后的 `scale`。

9. **将 jPanel3 添加到主窗口**
   - 将 `jPanel3` 添加到主窗口 `jf` 中，并分别设置主窗口的长宽度、可见性、默认关闭方式，使其位于 Windows 窗口的中间位置。

## 四个类解释

### 1.FunctionDraw 类

主类，实现主要功能，最主要的方法为其中的 `init()` 方法，将设置的变量初始化，实现各种监控器功能以及绘图功能。

### 2.FunctionSolution 类

主要用于处理函数，计算函数，其中包含 3 个方法：

1. `getFunction` 方法用来解析函数文本域，用来获取函数解析式，解析 `y=f(x)` 和 `f(x)` 两种格式。
2. `checkFunction` 方法用来检查函数是否正确，调用了 `exp4j` 包的 `Expression` 类来解析表达式。
3. `calculateFunction` 方法用来计算函数值，将 `Expression` 里面的 `x` 变量值更改实现计算功能。

### 3.ShowDialog 类

主要包含五个方法：

1. `showEnterWarningDialog` 方法
2. `showEmptyWarningDialog` 方法
3. `showScaleMessageDialog` 方法
4. `showThreeFunctionsEmpty` 方法
5. `showFunctionSavedDialog` 方法

分别实现弹出函数输入错误警告对话框、函数输入为空警告对话框、缩放过度警告对话框、三个函数均为空警告对话框、函数已保存提示对话框。

### 4.FunctionSave 类

主要包含 `saveImage` 方法，实现将绘制的函数保存到 `images` 对应的文件中，实现原理见代码原理的最后一部分。

## 项目架构

```
.
├── FunctionDraw.iml
├── images
│   └── 2023-12-12
│       └── 21-29-21
│           ├── functions.txt
│           └── image.jpg
├── resources
│   ├── FunctionDraw.java
│   ├── FunctionSave.java
│   ├── FunctionSolution.java
│   ├── ShowDialog.java
│   └── exp4j-0.4.8.jar
└── src
    ├── META-INF
    │   └── MANIFEST.MF
    ├── lib
    │   └── exp4j-0.4.8.jar
    └── practice
        ├── FunctionDraw.java
        ├── FunctionSave.java
        ├── FunctionSolution.java
        └── ShowDialog.java

9 directories, 14 files
```

images保存绘制的结果，按照时间自动创建文件夹，时间格式为：年-月-日，然后下一级就是每次绘制的时间点：时-分-秒

- functions.txt为绘制的函数
- image为绘制的函数图片

> Java项目打包工具：
>
> https://www.ej-technologies.com/exe4j/download
>
> 使用教程可在B站上找到，打包时注意打包依赖（jre）和不打包依赖（依靠机器系统环境）两种情况。