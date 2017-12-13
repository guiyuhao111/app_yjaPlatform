package cn.net.gxht.app.yjdPlatform.pictures.entity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/25.
 */
public class QRcodeUtil {
    public static final Integer QRCODE_SIZE=400;
    public static final Integer LOGO_SIZE=80;
    public static final String QRCODE_IMG_FORMAT="png";
    public BufferedImage gengrateQRCode(String contents, int size) {
        BufferedImage targetImage = null;
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);//留白
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, size, size, hints);
            targetImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    targetImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x0AC516 : 0xFFFFFFFF);//0x0AC516绿色二维码
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetImage;
    }

    //srcQRCode为要添加logo的二维码,path为logo路径
    public BufferedImage insertLogo(BufferedImage srcQRCode, String path,
                                    int size) throws Exception {
        File logo = new File(path);
        if (!logo.exists()) {
            return srcQRCode;
        }
        Image logoImg = ImageIO.read(logo);
        Image targetLogo = logoImg.getScaledInstance(size, size, Image.SCALE_SMOOTH);//压缩logo
        BufferedImage targetBuffLogo = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        targetBuffLogo.getGraphics().drawImage(targetLogo, 0, 0, null);//重新生成压缩后的logo
        int pos = (srcQRCode.getWidth() - size) / 2;
        Graphics2D g = srcQRCode.createGraphics();
        g.drawImage(targetBuffLogo, pos, pos, size, size, null);//绘制logo
        Shape shape = new RoundRectangle2D.Float(pos, pos, size, size, 6, 6);//绘制带圆角的边框
        g.setStroke(new BasicStroke(3F));//设置画笔(边框)宽度
        g.draw(shape);
        g.dispose();
        return srcQRCode;
    }
    public void saveImage(BufferedImage targetImage, String format, String path) {
        try {
            ImageIO.write(targetImage, format, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void makeQRcode(
            String contents,
            int QRcodeSize,
            String logoPath,
            int logoSize,
            String QRcodeImageFormat,
            String newQRcodeImgPath
    ) throws Exception {
        QRcodeUtil qRcodeUtil=new QRcodeUtil();
        BufferedImage srcQRcodeImage = qRcodeUtil.gengrateQRCode(contents,QRcodeSize);
        BufferedImage QRcodeImage = qRcodeUtil.insertLogo(srcQRcodeImage,logoPath,logoSize);
        qRcodeUtil.saveImage(QRcodeImage,QRcodeImageFormat,newQRcodeImgPath);
    }
}
