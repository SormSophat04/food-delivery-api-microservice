package com.food.eat.authservice.service.impl;

import com.food.eat.authservice.enums.OtpPurpose;
import com.food.eat.authservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpEmailService implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:noreply@food-delivery.local}")
    private String fromAddress;

    @Value("${app.mail.brand-name:Food Delivery}")
    private String brandName;

    @Override
    public void sendOtpCode(String email, String code, OtpPurpose purpose, long ttlMinutes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAddress);
            helper.setTo(email);
            helper.setSubject(subjectFor(purpose));
            helper.setText(plainBodyFor(code, ttlMinutes, purpose), modernHtmlBodyFor(code, ttlMinutes, purpose));
            mailSender.send(message);
        } catch (MessagingException exception) {
            throw new IllegalStateException("Failed to send OTP email", exception);
        }
    }

    private String subjectFor(OtpPurpose purpose) {
        return switch (purpose) {
            case REGISTER -> brandName + " - Verify your registration";
            case LOGIN -> brandName + " - Verify your login";
        };
    }

    private String plainBodyFor(String code, long ttlMinutes, OtpPurpose purpose) {
        return switch (purpose) {
            case REGISTER -> "Use this code to verify your account: " + code +
                    "\nThis code expires in " + ttlMinutes + " minutes.";
            case LOGIN -> "Use this code to complete your login: " + code +
                    "\nThis code expires in " + ttlMinutes + " minutes.";
        };
    }

    private String modernHtmlBodyFor(String code, long ttlMinutes, OtpPurpose purpose) {
        String actionLabel = switch (purpose) {
            case REGISTER -> "verify your account";
            case LOGIN -> "complete your login";
        };

        String safeBrand = escapeHtml(brandName);
        String safeCode = escapeHtml(code);

        return "<!doctype html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0\">" +
                "<title>" + safeBrand + " verification code</title>" +
                "</head>" +
                "<body style=\"margin:0;padding:0;background:#f3f6fb;font-family:Inter,Segoe UI,Roboto,Helvetica,Arial,sans-serif;color:#0f172a;\">" +
                "<table role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"padding:32px 12px;\">" +
                "<tr>" +
                "<td align=\"center\">" +
                "<table role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"max-width:560px;background:#ffffff;border-radius:16px;border:1px solid #e2e8f0;overflow:hidden;\">" +
                "<tr>" +
                "<td style=\"padding:24px 28px;background:linear-gradient(135deg,#0ea5e9,#2563eb);\">" +
                "<p style=\"margin:0;font-size:14px;font-weight:600;letter-spacing:0.4px;color:#e0f2fe;\">SECURITY CODE</p>" +
                "<h1 style=\"margin:8px 0 0 0;font-size:24px;line-height:1.25;color:#ffffff;\">" + safeBrand + "</h1>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style=\"padding:28px;\">" +
                "<p style=\"margin:0 0 10px 0;font-size:16px;line-height:1.55;color:#334155;\">Use this code to " + actionLabel + ":</p>" +
                "<div style=\"margin:0 0 18px 0;padding:16px 20px;text-align:center;background:#f8fafc;border:1px solid #dbeafe;border-radius:12px;\">" +
                "<span style=\"font-size:34px;font-weight:700;letter-spacing:8px;color:#1d4ed8;font-family:ui-monospace,SFMono-Regular,Menlo,Consolas,monospace;\">" + safeCode + "</span>" +
                "</div>" +
                "<p style=\"margin:0 0 12px 0;font-size:14px;line-height:1.6;color:#475569;\">This code expires in <strong>" + ttlMinutes + " minutes</strong>.</p>" +
                "<p style=\"margin:0;font-size:13px;line-height:1.6;color:#64748b;\">If you did not request this code, you can safely ignore this email.</p>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style=\"padding:18px 28px;border-top:1px solid #e2e8f0;background:#f8fafc;\">" +
                "<p style=\"margin:0;font-size:12px;line-height:1.5;color:#94a3b8;\">" + safeBrand + " security notification</p>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";
    }

    private String escapeHtml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
