package com.chandlertu.sendlater;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

import com.chandlertu.mail.MailSender;

public class SendLater {

	public static void main(String[] args) {
		Instant start = Instant.now();

		Properties props = new Properties();
		Path path = Paths.get(System.getProperty("user.home"), ".sendlater", "mail.properties");
		try (InputStreamReader inStream = new InputStreamReader(new FileInputStream(path.toFile()), "utf-8")) {
			props.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String account = props.getProperty("account");
		String password = props.getProperty("password");
		String smtp = props.getProperty("smtp");
		String to = props.getProperty("to");
		String subject = props.getProperty("subject");
		String text = props.getProperty("text");
		String ssl = props.getProperty("ssl", "false");

		while (true) {
			Instant end = Instant.now();
			Duration time = Duration.between(start, end);
			long mintues = time.toMillis() / 1000 / 60;

			subject = mintues + " ·ÖÖÓ";
			System.out.println(subject);

			if (mintues >= 25 && mintues % 5 == 0) {
				MailSender sender = new MailSender(account, password, smtp);
				sender.send(to, subject, text, ssl);
				System.out.println("·¢ËÍÓÊ¼ş");
			}

			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
