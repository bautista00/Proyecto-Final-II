package com.proyectointegrador.msticket;


import com.proyectointegrador.msticket.dto.EmailDTO;
import com.proyectointegrador.msticket.service.implement.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

	@Mock
	private JavaMailSender javaMailSender;

	@Mock
	private TemplateEngine templateEngine;

	@InjectMocks
	private EmailService emailService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void sendMail_Successful() throws MessagingException {
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setReceiver("test@example.com");
		emailDTO.setSubject("Test Subject");
		emailDTO.setMessage("Test Message");


		when(templateEngine.process(eq("email"), any(Context.class))).thenReturn("<html><body>This is a test email</body></html>");

		emailService.sendMail(emailDTO);

		verify(javaMailSender).send(any(MimeMessage.class));
	}






	@Test
	void sendMail_Failure() {
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setReceiver("test@example.com");
		emailDTO.setSubject("Test Subject");
		emailDTO.setMessage("Test Message");

		when(javaMailSender.createMimeMessage()).thenThrow(new RuntimeException("Mail server error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			emailService.sendMail(emailDTO);
		});

		assertEquals("Error al enviar el correo: Mail server error", exception.getMessage());
	}
}
