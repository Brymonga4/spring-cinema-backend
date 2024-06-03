package org.example.service.email;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;

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
        Document document = new Document(pdfDoc, PageSize.A4);
        document.setMargins(50, 50, 50, 50);

        Color blue = new DeviceRgb(15, 72, 122);
        Color white = new DeviceRgb(255, 255, 255);

        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont normal = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont dataFont = PdfFontFactory.createFont(StandardFonts.COURIER);



        //Cabecera del PDF
        float[] columnWidth = {25,75};
        Table cabecera = new Table(UnitValue.createPercentArray(columnWidth));
        cabecera.setWidth(UnitValue.createPercentValue(100));


        //Creamos dos celdas
        Cell cine = new Cell().add(new Paragraph("CINE Filmmes")
                .setFont(bold).setFontSize(20).setFontColor(white));
        Cell titulo = new Cell().add(new Paragraph("ENTRADA VÁLIDA PARA EL ACCESO DIRECTO A LA SALA")
                .setFont(bold).setFontSize(11));

        cine.setBackgroundColor(blue).setBorder(null)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        titulo.setBorder(null).setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER)
                            .setPadding(15);

        // Añadimos las celdas a la tabla de la cabecera
        cabecera.addCell(cine);
        cabecera.addCell(titulo);

        //Generamos la imagen del QR del identificador
        BufferedImage qrImage = qrCodeService.generateQRCodeImage(identifier);

        ByteArrayOutputStream qrBaos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", qrBaos);
        Image qrCode = new Image(ImageDataFactory.create(qrBaos.toByteArray()))
                .scaleToFit(300,300);

        // Imagen de la película
        Image movieImage = new Image(ImageDataFactory.create("src/main/resources/static/uploads/jaikiyuuu.jpg"))
                .scaleToFit(300,300);

        // Tabla de QR e imagen de peli
        float[] columnWidthImages = {40,60};
        Table imagesTable = new Table(UnitValue.createPercentArray(columnWidthImages));
        imagesTable.setWidth(UnitValue.createPercentValue(100));
        imagesTable.setHeight(UnitValue.createPercentValue(100));

        Cell qrCell = new Cell().add(qrCode);
        Cell movieImageCell = new Cell().add(movieImage);

        qrCell.setBorder(null).setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER);


        movieImageCell.setBorder(null).setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER)
                            .setPadding(5);

        // Añadimos las celdas a la tabla de imágenes
        imagesTable.addCell(qrCell);
        imagesTable.addCell(movieImageCell);


        Paragraph frasePeli = new Paragraph().add("Película: ").setFont(dataFont).setFontColor(blue)
                        .add(new Paragraph().add("Titulo de la peli").setFont(bold));

        Paragraph fraseSesion = new Paragraph().add("Sesion: ").setFont(dataFont).setFontColor(blue)
                .add("66:66"+" h").setFont(bold);

        Paragraph fraseSala = new Paragraph().add("Sala: ").setFont(dataFont).setFontColor(blue)
                .add("1").setFont(bold);


        Paragraph fraseDia = new Paragraph().add("Día: ").setFont(dataFont).setFontColor(blue)
                .add("11-06-2024").setFont(bold);

        Paragraph fraseCine = new Paragraph().add("Cine: ").setFont(dataFont).setFontColor(blue)
                .add("CINE FilMM").setFont(bold);

        Paragraph fraseButacas = new Paragraph().add("Butacas: ").setFont(dataFont).setFontColor(blue)
                .add("F 6 B 12").setFont(bold);
        Paragraph frasePrecio = new Paragraph().add("Precio: ").setFont(dataFont).setFontColor(blue)
                .add("16.08€").setFont(bold);

        float[] columnWidthData1 = {50,50};
        Table dataTable1 = new Table(UnitValue.createPercentArray(columnWidthData1));
        dataTable1.setWidth(UnitValue.createPercentValue(90));

        float[] columnWidthData2 = {100};
        Table dataTable2 = new Table(UnitValue.createPercentArray(columnWidthData2));
        dataTable2.setWidth(UnitValue.createPercentValue(90));

        Cell dataOne = new Cell().add(frasePeli).add(fraseSesion).add(fraseSala)
                .setBorder(Border.NO_BORDER);
        Cell dataTwo = new Cell().add(fraseDia).add(fraseCine)
                .setBorder(Border.NO_BORDER);
        Cell dataThree = new Cell().add(fraseButacas).add(frasePrecio)
                .setBorder(Border.NO_BORDER);

        dataTable1.addCell(dataOne).addCell(dataTwo);

        dataTable2.addCell(dataThree);


        document.add(cabecera);
        document.add(imagesTable);
        // Añadir identificador
        document.add(new Paragraph(identifier).setTextAlignment(TextAlignment.CENTER));

        document.add(dataTable1);
        document.add(dataTable2);


        document.close();
        return baos.toByteArray();
    }
}