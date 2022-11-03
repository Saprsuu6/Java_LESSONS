package step.learning.oop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Newspaper
        extends Literature
        implements Printable {
    private Date date ;
    private Calendar calendar ;

    static private final SimpleDateFormat   // форматировщик даты: как на ввод, так и на вывод
            dateParser = new SimpleDateFormat( "yyyy-MM-dd" ) ;
    static private final SimpleDateFormat
            datePrinter = new SimpleDateFormat( "dd.MM.yyyy" ) ;
    static private final SimpleDateFormat
            datePrinterShort = new SimpleDateFormat( "dd.MM" ) ;
    public Date getDate() {
        return date;
    }
    public Newspaper setDate( String dateString ) throws ParseException {
        this.date = dateParser.parse( dateString ) ;  // методы Date deprecated
        this.calendar = Calendar.getInstance() ;      // рекомендованый объект для
        calendar.setTime( this.date ) ;               //  работы с датами
        return this ;
    }
    @Override
    public Newspaper setTitle( String title ) {
        return (Newspaper) super.setTitle( title ) ;
    }
    @Override
    public void print() {
        Calendar now = Calendar.getInstance() ;
        String dateString =
                now.get( Calendar.YEAR ) == calendar.get( Calendar.YEAR )
                ? datePrinterShort.format( this.getDate() )  // тот же год - выводим сокращенную дату
                : datePrinter.format( this.getDate() ) ;
        System.out.printf( "Newspaper '%s' @%s%n",
                    super.getTitle(), dateString ) ;

    }
}
/*
Задача: при выводе даты использовать "интеллектуальное" форматирование:
если дата сегодня, то выводить "сегодня"
аналогично - "вчера"
если дата за текущий год, то выводить только день.месяц,
 иначе - полную дату (день.месяц.год)
Не использовать date.getYear(); (deprecated)
 ! calendar.get( Calendar.YEAR )
 */
