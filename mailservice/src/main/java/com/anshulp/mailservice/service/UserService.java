package com.anshulp.mailservice.service;

import com.anshulp.mailservice.entity.User;
import com.anshulp.mailservice.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;


    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public User createUser(String name, String email) {

        // Check if user with the same email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with email " + email + " already exists.");
        }
        if (!isValidEmail(email)) {
            throw new RuntimeException("Invalid email format: " + email);
        }

        // Save user to database
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        userRepository.save(user);


        // Send welcome email
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("pandeyanshul.27j4@gmial.com");
            message.setTo(email);
            message.setSubject("Welcome to Our Service");
            message.setText("Hello " + name + ",\n\nWelcome to our service! We're glad to have you on board.\n\nBest regards,\nAlienX Team");
            javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send welcome email to " + email + ": " + e.getMessage());
        }


        return user;
    }

    public String deleteUser(String email) {
        if (!isValidEmail(email)) {
            throw new RuntimeException("Invalid email format: " + email);
        }
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return "User with email " + email + " deleted successfully.";
        } else {
            return "User with email " + email + " not found.";
        }
    }

    public User getUserByEmail(String email) {
        if (!isValidEmail(email)) {
            throw new RuntimeException("Invalid email format: " + email);
        }
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User with email " + email + " not found."));
    }

    public String swa(String name, String email) {
        if (!isValidEmail(email)) {
            throw new RuntimeException("Invalid email format: " + email);
        }

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = getMimeMessageHelper(name, email, message);

            helper.addAttachment(
                    "AnshulPandey_Resume.pdf",
                    new ClassPathResource("files/AnshulPandey_Resume.pdf")
            );

            javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + email + ": " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }

        return "Hello " + name + ", welcome to our service!";
    }

    private static MimeMessageHelper getMimeMessageHelper(String name, String email, MimeMessage message) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("pandeyanshul.27j4@gmail.com");
        helper.setTo(email);
        helper.setSubject("Please find the attached resume");
        String htmlContent = "<html><body>" +
                "<h1>Hello " + name + ",</h1>" +
                "<p>Welcome to our service! We're excited to have you on board.</p>" +
                "<p>Please find the attached resume for your reference.</p>" +
                "</body></html>";
        helper.setText(htmlContent, true);
        return helper;
    }
}
