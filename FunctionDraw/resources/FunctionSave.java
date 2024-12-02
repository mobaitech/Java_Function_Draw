package practice;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class FunctionSave {
    public void saveImage(FunctionDraw.MyCanvas canvas, String function1, String function2, String function3) throws IOException {
        LocalDate currentDate = LocalDate.now();
        String data = currentDate.toString();
        //获取data字符串
        LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        String time = currentTime.toString().replace(":", "-");
        //将time保留到秒
        //获取time字符串
        //将时间上的:改为-  因为标准的文件命名不允许:
        String dirName = "images//" + data + "//" + time;
        //以日期和时间区分绘制图片的文件夹
        String fileName = "image.jpg";
        BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        //创建image类的buffer缓冲流，image
        //获取画布的长度
        Graphics2D g2d = image.createGraphics();
        //对image对象获取画笔
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.paint(g2d);
        //用canvas的绘制形式调用画笔image的画笔对象绘制到image的缓冲流中
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
            //如果文件夹不存在则创建文件夹
            //创建多级目录
        }
        File file = new File(dir, fileName);
        ImageIO.write(image, "jpg", file);
        //保存image流为jpg文件形式到指定文件中
        //实现函数绘制的图片保存

        String saveText = "function1:" + function1 + "\n" + "function2:" + function2 + "\n" + "function3:" + function3;
        String textName = "functions.txt";

        try {
            FileWriter writer = new FileWriter(new File(dir, textName));
            writer.write(saveText);
            writer.close();  // 关闭文件
        } catch (IOException e) {
            e.printStackTrace();  // 打印错误信息
        }


    }
}
//在工程文件中创建images文件夹实现jpg文件的集中保存
