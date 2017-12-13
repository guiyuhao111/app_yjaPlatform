package QRcode;

import cn.net.gxht.app.yjdPlatform.pictures.entity.QRcodeUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.Test;

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
public class MakeQRcode {
    @Test
    public void testQRUtil() throws Exception {
        String content="http://www.baidu.com";
        String logoPath="D://logo.png";
        String newQRcodeImgPath="D://QRcodeImgPath.png";
        QRcodeUtil.makeQRcode(content,QRcodeUtil.QRCODE_SIZE,logoPath,QRcodeUtil.LOGO_SIZE,QRcodeUtil.QRCODE_IMG_FORMAT,newQRcodeImgPath);
    }
}
