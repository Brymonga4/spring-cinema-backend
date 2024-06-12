package org.example.service.email;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    public BufferedImage generateQRCodeImage(String identifier) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(identifier, BarcodeFormat.QR_CODE, 400, 300);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public String decodeQRCodeInImage(MultipartFile imageFile){
        String decodedQR = "";

        try {
            BufferedImage image = ImageIO.read(imageFile.getInputStream());
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(image)));

            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

            MultiFormatReader reader = new MultiFormatReader();

            // Decodifica el QR
            Result result = reader.decode(bitmap, hints);

            decodedQR = result.getText();
            System.out.println("Contenido del QR: " + result.getText());

        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }

        return decodedQR;

    }


}