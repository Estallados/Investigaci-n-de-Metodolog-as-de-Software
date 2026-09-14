package co.edu.unbosque.metodologiaespiral.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;

@Service
public class CorreoUtil {
	private final Resend resend;

	public CorreoUtil(@Value("${RESEND_API_KEY}") String apiKey) {
		this.resend = new Resend(apiKey);
	}

	public void enviarCorreo(String destinatario, String asunto, String mensaje) {
		CreateEmailOptions params = CreateEmailOptions.builder().from("onboarding@resend.dev").to(destinatario)
				.subject(asunto).html(mensaje).build();
		try {
			resend.emails().send(params);
		} catch (ResendException e) {
			throw new RuntimeException("Error enviando correo a " + destinatario + ": " + e.getMessage(), e);
		}
	}
}