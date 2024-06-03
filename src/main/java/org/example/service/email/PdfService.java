package org.example.service.email;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class PdfService {

    private final QRCodeService qrCodeService;

    public PdfService(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    public byte[] generatePdfWithQrCode(String identifier, String additionalInfo) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Add a paragraph with additional information
        document.add(new Paragraph(additionalInfo));

        // Generate QR Code image
        BufferedImage qrImage = qrCodeService.generateQRCodeImage(identifier);
        ByteArrayOutputStream qrBaos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", qrBaos);

        // Add QR Code image to PDF
        ImageData qrData = ImageDataFactory.create(qrBaos.toByteArray());
        Image qrCode = new Image(qrData);
        document.add(qrCode);

        document.close();
        return baos.toByteArray();
    }

    public byte[] generatePdfWithQrCodeAndDesign(String identifier, String additionalInfo) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);

        Document document = new Document(pdfDoc);



        // Add a paragraph with additional information
        document.add(new Paragraph(additionalInfo));

        // Generate QR Code image
        BufferedImage qrImage = qrCodeService.generateQRCodeImage(identifier);
        ByteArrayOutputStream qrBaos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", qrBaos);

        // Add QR Code image to PDF
        ImageData qrData = ImageDataFactory.create(qrBaos.toByteArray());
        Image qrCode = new Image(qrData);
        document.add(qrCode);

        document.close();
        return baos.toByteArray();
    }
}