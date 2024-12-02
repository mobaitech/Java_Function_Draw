# 使用方法

1. 在下拉选项框中选择要处理的函数，在文本框中输入函数，回车保存函数信息，点击OK键进行函数绘制。
2. 函数可以是 `y=f(x)` 形式，也可以直接是 `f(x)` 形式。
3. `Enlarge` 实现放大，`Reduce` 实现缩小，或者用鼠标滑轮的滚动实现放大或缩小，向上滑动实现放大，向下滑动实现缩小。
4. 点击 `Clear` 实现清空函数图像并重置缩放比例。
5. 也可以通过上下键选择函数，一次最多绘制三个有效函数。
6. 根据弹出的对话框了解函数是否有效，若要清除某个函数，在对应的选项框将文本域清空或输入空格即可。
7. 点击 `OK` 在函数绘制的同时保存绘图文件到项目文件下的 `images` 文件夹中，用日期和时间进行文件区分。

**注：**
1. 函数不区分乘号是否书写，`xx` 和 `x^2` 是一个函数，`xxx` 和 `x^3` 是一个函数。
2. 不区分 `y=f(x)` 形式和 `f(x)` 形式，但对于三角函数，`sinx` 和 `sin(x)` 是一个函数，但 `sinx+1` 和 `sin(x)+1` 是两个函数，`sinx+1` 对应着 `sin(x+1)`。严格书写函数表达式将得到更好的体验。

# 代码原理

1. 使用 Java 绘图 AWT 和 SWING。
2. 插件主窗口 `JFrame jf`。
3. 设置布局管理器模式为 `BorderLayout()`。
4. 创建下拉选择框 `functionChooser`，三个选项 `function1`、`function2` 和 `function3`，分别用于区分三个函数的输入保存信息。
5. 创建容器 `jPanel1`，向其中添加两个文本域 (`tips` 不可更改，`functionField` 函数输入域)，在两个文本域中间添加下拉选项框 `functionChooser` 对输入的文本进行解析绘制，和一个 `okButton` 按键，为 `okButton` 添加一个监控器，当按下 `okButton` 时对函数解析绘制。
6. 将 `jPanel1` 添加到 `jf` 中（SOUTH 位置处）。
7. 创建容器 `jPanel2`，向其中添加一个画布对象 `mc`（`MyCanvas` 继承自 `Canvas`），将 `Panel2` 置于 `jf` 的 CENTER 位置处。
8. 创建容器 `jPanel3`，向其中添加按键 `biggerButton`、按键 `smallerButton` 和按键 `clearButton`，对这三个按键创建一个方法实现添加监控器，分别实现放大、缩小和清空。实现原理是通过一个缩放比例参数 `scale`，再向 `jPanel3` 添加文本域 `scaleShow`，实时展示用户调节后的 `scale`。
9. 将 `jPanel3` 添加到 `jf` 中，再分别设置 `jf` 的长宽度、可见、默认关闭方式，置于 Windows 窗口中间位置。

## functionChooser 的检测原理

添加一个监控器，每次选择的时候进行判断，将文本域的内容呈现所选的函数。

## functionField 的检测原理

1. 为 `functionField` 添加一个回车按键监控器。
2. 回车之后调用 `saveFunction` 方法，进行函数解析，若函数为空则弹出函数为空警告对话框，若函数式不为空，则进行函数式子有效性的检验，无效则弹出函数无效对话框，文本域为空，`canDraw` 置为 `false`，有效则保存函数到对应选项，`canDraw` 置为 `true`。
3. 为 `functionField` 添加上下键的监控器，实现快速切换对应的函数选项，方便人机交互（实现原理为更改选项的下标，更改选项后将文本域置为更改之后的选项）。

## okButton 的监控器原理

点击 `ok` 之后，进行两种判断，若三个函数式都为空，则弹出三个函数式都为空警告对话框，否则调用 `mc` 的 `repaint()` 方法实现绘制。点击 `ok` 的同时调用 `FunctionSave` 对象 `fSave` 中的 `saveImage` 方法，传递 `mc` 对象，传递三个函数，实现函数绘制与保存。

## 鼠标滑动放缩的实现原理

为 `jf` 添加一个鼠标滚轮监控器：鼠标滚轮上滑时增大 `myScale`（缩放因子），鼠标滚轮下滑时减小 `myScale`，但控制 `myScale` 的大小位于 0.25 到 5.0 之间防止过度缩放。当过度缩放时调用 `sd`（`showDialog` 类的对象）的 `showScaleMessageDialog` 方法弹出警告对话框。

## 两个按钮的放缩及清空的实现原理

`biggerButton`（`Enlarge`）按键和 `smallerButton`（`Reduce`）按键（对外展示 `Enlarge` 和 `Reduce` 这两个词会更高级一些）调用方法 `addButtonListener` 为这两个按键添加监控器，具体实现和鼠标监控器类似。再用 `addButtonListener` 为 `clearButton` 添加监控器，对于 `clearButton` 的监控器，只需要将 `myScale` 重置为 1.0，再将 `canDraw` 设置为 `false`，清空函数文本域即可。

## 核心代码

`MyCanvas` 的自定义 `paint`，获取画笔，再重置原点坐标，先绘制背景方格再为画布设置缩放比例，然后绘制坐标轴：核心是绘制刻度，绘制刻度是通过 `for` 循环每 20 个像素点设置为一个刻度单位 1，随后绘制原点，再通过 `for` 循环来绘制函数的每个点，用对象 `fs` 调用方法 `calculateFunction` 来计算出每个 `x` 对应的 `y` 值，再绘制小圆点。用 `MAXSIZE`（5000）来代替 `MYWIDTH`/`MYHEIGHT` 来绘制是为了满足缩放要求。

## 关于函数的绘制

若 `canDraw` 为 `true` 则进行函数绘制。

为了使 `y` 的绘制能够与刻度相对应，并且使原点绘制清晰，每次计算时对 `x/100` 相当于刻度上的 -50 到 50。举个例子：此时 `x` 为 20，20/100 为 0.2，此时 `y` 为 0.2，绘制图像时 `x/5` 为 4，`y*100/5` 为 4，就是在 4，4 处绘制了一个点。代码核心是实现与图像匹配，20 个像素点为单位刻度 1，若 1 个像素点为单位刻度，那么图像显示就靠近 `x` 轴（如 `sinx`）。

需要明确区分实际 `x` 和 `y` 的值和图像上 `x` 和 `y` 的值。

## 关于函数绘制保存的原理

主要是利用 Java IO 流中 `ImageIO` 和 `FileWrite` 实现文件的读写。

创建 `BufferedImage` 的对象 `image`，从 `image` 中获取画笔 `g2`，再同等的绘制 `MyCanvas` 中的 `paint` 即可，再将 `image` 保存为 `jpg` 文件。

同样，利用文件 `FileWrite` 类中的 `write` 实现将字符串写入到 `functions.txt` 中。

将上述的 `.jpg` 文件和 `.txt` 文件保存在多级文件夹 `image//date//time` 中实现，即使多次绘制的文件的位置也非常清晰。

# 四个类的解释以及外部包资源

## FunctionDraw 类

主类，实现主要功能，最主要的方法为其中的 `init()` 方法，将设置的变量初始化，实现各种监控器功能以及绘图功能。

## FunctionSolution 类

主要用于处理函数，计算函数，其中包含 3 个方法：
1. `getFunction` 方法用来解析函数文本域，用来获取函数解析式，解析 `y=f(x)` 和 `f(x)` 两种格式。
2. `checkFunction` 方法用来检查函数是否正确，调用了 `exp4j` 包的 `Expression` 类来解析表达式。
3. `calculateFunction` 方法用来计算函数值，将 `Expression` 里面的 `x` 变量值更改实现计算功能。

## ShowDialog 类

主要包含五个方法：
1. `showEnterWarningDialog` 方法
2. `showEmptyWarningDialog` 方法
3. `showScaleMessageDialog` 方法
4. `showThreeFunctionsEmpty` 方法
5. `showFunctionSavedDialog` 方法

分别实现弹出函数输入错误警告对话框、函数输入为空警告对话框、缩放过度警告对话框、三个函数均为空警告对话框、函数已保存提示对话框。

## FunctionSave 类

主要包含 `saveImage` 方法，实现将绘制的函数保存到 `images` 对应的文件中，实现原理见代码原理的最后一部分。