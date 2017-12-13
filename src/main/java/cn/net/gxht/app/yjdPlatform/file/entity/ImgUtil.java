package cn.net.gxht.app.yjdPlatform.file.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Administrator on 2017/10/19.
 */
public class ImgUtil {
    public static final Integer OUTPUTWIDTH=200;
    public static final Integer OUTPUTHEIGHT=200;
    public static final boolean PROPORTION=true;
    /**
     * 裁剪PNG图片工具类
     *
     * @param
     *
     * @param
     *
     * @param outputWidth
     *            裁剪宽度
     * @param outputHeight
     *            裁剪高度
     * @param proportion
     *            是否是等比缩放
     */
    public static void resizePng(String fromFilePath, String toFilePath, int outputWidth, int outputHeight,
                                 boolean proportion) {
        File fromFile =new File(fromFilePath);
        File toFile   =new File(toFilePath);
        try {
            BufferedImage bi2 = ImageIO.read(fromFile);
            int newWidth;
            int newHeight;
            // 判断是否是等比缩放
            if (proportion) {
                // 为等比缩放计算输出的图片宽度及高度
                double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth + 0.1;
                double rate2 = ((double) bi2.getHeight(null)) / (double) outputHeight + 0.1;
                // 根据缩放比率大的进行缩放控制
                double rate = rate1 < rate2 ? rate1 : rate2;
                newWidth = (int) (((double) bi2.getWidth(null)) / rate);
                newHeight = (int) (((double) bi2.getHeight(null)) / rate);
            } else {
                newWidth = outputWidth; // 输出的图片宽度
                newHeight = outputHeight; // 输出的图片高度
            }
            BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = to.createGraphics();
            to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight,
                    Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = to.createGraphics();
            @SuppressWarnings("static-access")
            Image from = bi2.getScaledInstance(newWidth, newHeight, bi2.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();
            ImageIO.write(to, "png", toFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        resizePng("C:\\4695500_224140299162_2.jpg", "C:\\4695500_224140299162_2ZIP.jpg", 200, 200, true);
    }
}
