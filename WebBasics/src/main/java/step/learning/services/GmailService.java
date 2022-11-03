package step.learning.services;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class GmailService implements EmailService {
    @Override
    public boolean send( String to, String subject, String text ) {
        Properties gmailProperties = new Properties() ;  // java.util
        gmailProperties.put( "mail.smtp.auth", "true" ) ;
        gmailProperties.put( "mail.smtp.starttls.enable", "true" ) ;
        gmailProperties.put( "mail.smtp.port", "587" ) ;
        gmailProperties.put( "mail.smtp.ssl.protocols", "TLSv1.2" ) ;
        gmailProperties.put( "mail.smtp.ssl.trust", "smtp.gmail.com" ) ;

        Session mailSession = Session.getInstance( gmailProperties ) ;  // javax.mail.Session
        // mailSession.setDebug( true ) ;   // вывод в консоль данных обмена

        try {
            Transport mailTransport = mailSession.getTransport( "smtp" ) ;
            mailTransport.connect(               // Подключаемся к почтовому серверу
                    "smtp.gmail.com",            // адрес (хост) сервера
                    "proviryalovich@gmail.com",  // ящик
                    "umfqogmkoabhzpiu" ) ;       // пароль приложения
            // Создаем сообщение (javax.mail.internet.MimeMessage;)
            MimeMessage message = new MimeMessage( mailSession ) ;
            // от кого
            message.setFrom( new InternetAddress( "proviryalovich@gmail.com" ) ) ;
            // тема
            message.setSubject( subject ) ;
            // содержание (тело)
            message.setContent( text, "text/html; charset=utf-8" ) ;
            // отправляем
            mailTransport.sendMessage( message, InternetAddress.parse( to ) ) ;
            mailTransport.close() ;
        }
        catch( MessagingException ex ) {
            System.out.println( ex.getMessage() ) ;
            return false ;
        }
        return true ;
    }
}
/*
if( confirm != null ) {     // есть сообщение на подтверджение
    try {
        User user = userId == null
                ? (User) req.getAttribute( "AuthUser" )
                : userDAO.getUserById( userId ) ;

        if ( user == null )
            throw new Exception("Invalid user id");

        if ( user.getEmailCode() == null ) // почта подтверждена, вероятно обновление страницы с параметрами
            throw new Exception("Email already confirmed");

        if ( !confirm.equals( user.getEmailCode() ) ) // код не подтвержден
            throw new Exception("Invalid code");

        if ( !userDAO.confirmEmail(user) )
            throw new Exception("DB error");

        req.setAttribute("confirm", "OK");  // код подтвержден
    }
    catch (Exception ex) {
        req.setAttribute("confirmError", ex.getMessage());
    }

}
 */
/*
javax.mail - указать зависимость в pom.xml
https://mvnrepository.com/artifact/javax.mail/mail
smtp.gmail.com:587   proviryalovich@gmail.com   umfqogmkoabhzpiu
"denniksam@gmail.com"
Д.З. Модифицировать метод add (UserDAO) для приема почты и отправки
кода подтверждения.

Подтверждение кода
- возможность ввода кода
- переход по ссылке из почты
= в кабинете - возле почты уведомление о необходимости подтвердить
= в верхней панели (авторизация) также присутствует сообщение (на любых страницах)

Отдельная страница подтверждения почты. Поведение:
- простой заход на страницу: вывести поле для кода
- заход с параметрами: проверить и выдать рез-т
? при неправильном вводе - ограничивать кол-во попыток
 */