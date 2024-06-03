package org.example.service.email;

import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final QRCodeService qrCodeService;
    @Autowired
    public EmailService(JavaMailSender mailSender, QRCodeService qrCodeService) {
        this.mailSender = mailSender;
        this.qrCodeService = qrCodeService;
    }


    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(text, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("hitohitotadano7@gmail.com");

        mailSender.send(mimeMessage);
    }

    public void sendEmailWithQRCode(String to, String subject, String text, String identifier)
            throws MessagingException, IOException, WriterException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setFrom("hitohitotadano7@gmail.com");

        BufferedImage qrImage = qrCodeService.generateQRCodeImage(identifier);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);
        ByteArrayDataSource dataSource = new ByteArrayDataSource(baos.toByteArray(), "image/png");

        helper.addAttachment("qrcode.png", dataSource);

        mailSender.send(mimeMessage);
    }

    public void sendEmailWithPdf(String to,
                                 String subject,
                                 String text,
                                 String identifier,
                                 byte[] pdfBytes) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setFrom("hitohitotadano7@gmail.com");

        ByteArrayDataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");
        helper.addAttachment(identifier+"entrada.pdf", dataSource);

        mailSender.send(mimeMessage);
    }
}
