package step.learning.threading;

import step.learning.ConsoleColors;
import step.learning.anno.DemoClass;
import step.learning.anno.EntryPoint;

import java.util.concurrent.*;
import java.util.function.Function;

@DemoClass
public class SyncDemo {
    @EntryPoint
    public void demo() {
        System.out.println( "Synchronization demo" ) ;
        sum = 100 ;
        int months = 12 ;
        threads = months ;
        for( int i = 0; i < months; ++i ) {
            new Thread( plus10percentSyncFin ).start() ;
        }
        // Thread pool
        ExecutorService pool = Executors.newFixedThreadPool(3) ;
        for( int i = 1; i <= 10; ++i ) {
            pool.submit(    // IIFE - Immediately Invoked Fun Expression (fun(x){})(x)
                ( (Function<Integer,Runnable>)  x ->
                    () -> System.out.printf( "pool %d works %n", x )
                ).apply(i)
            ) ;
        }

        Future<String>                 // ~Task<String> - объект, запустивший
                res =                  // фоновую задачу и ожидающий от нее строки
                pool.submit(           // добавление задачи в пул
                        () -> {        // активность, возвращающую строку (Callable<String>)
                            return
                            "Hello," + " world!" ;
                        } ) ;
        try {
            String str = res.get();  // str = await res
            System.out.println( str ) ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        res = pool.submit(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return "Hello, call" ;
                    }
                }
        );
        pool.shutdown() ;
    }
    private int threads ;
    private double sum ;
    private Runnable plus10percent = () -> {
        double tmp = sum ;                   // проблема -
        tmp *= 1.10 ;   // + 10 %            // отсутствие
        System.out.println(                  // транзакции
                "current sum = " + tmp ) ;   // от чтения sum
        sum = tmp ;                          // до ее записи
    } ;

    private final Object sumLocker = new Object() ;
    private Runnable plus10percentSync = () -> {
        synchronized( sumLocker ) {   // аналог lock() в C#
            double tmp = sum;                 // проблема -
            tmp *= 1.10;   // + 10 %          // все тело метода
            System.out.println(               // в синхроблоке -
                    "current sum = " + tmp);  // никакой код не будет
            sum = tmp;                        // асинхронным
        }
    } ;

    private Runnable plus10percentSync2 = () -> {
        synchronized( sumLocker ) {        // транзакция
            sum *= 1.10;                   // организована
        }                                  // -- другой может менять sum --
        System.out.println(                // но за ней снова
                "current sum = " + sum);   // обращение к sum
    } ;
    private Runnable plus10percentSync3 = () -> {
        double tmp ;
        synchronized( sumLocker ) {        // транзакция
            tmp = sum = sum * 1.10;        // + сохранение рез-та
        }                                  // -- другой может менять sum --
        System.out.println(                // обращение не к sum
                "current sum = " + tmp);   // а к локальной копии tmp
    } ;
    private Runnable plus10percentSyncFin = () -> {
        double tmp ;
        boolean isLast ;
        synchronized( sumLocker ) {  // транзакция по sum
            tmp = sum = sum * 1.10;
        }
        synchronized( sumLocker ) {  // Транзакция по threads
            threads--;
            isLast = threads == 0 ;  // условие последнего потока
        }
        System.out.println(
                ( isLast ? ConsoleColors.GREEN_BOLD : "" )
                + "current sum = " + tmp
                + ( isLast ? ConsoleColors.RESET : "" )
        ) ;
        if( isLast ) done() ;
    } ;

    private void done() {
        System.out.println( "Result: " + sum ) ;
    }
}
/*
    Синхронизация. Фоновые потоки - задачи.
  Одно из преимуществ асинхронных приложений - в возможности
ускорения вычислений при наличие возможности проводить части
вычислений параллельно (одновременно)
  Пример: расчет годовой инфляции. За каждый месяц известен
процент инфляции. Необходимо расчитать годовую инфляцию.
? можно ли "параллелить"? (x + 10%) + 20%  =?= (x + 20%) + 10%
 (x * 1.10) * 1.20  =?= (x * 1.20) * 1.10
  x * 1.1 * 1.2 =!= x * 1.2 * 1.1
  Вывод: операция коммутативна, значит можно выполнять в любом
  порядке - Январь + Февраль == Февраль + Январь

Синхронизация ресурса - создание транзакции для операций
 (чтение - изменение - запись)  для общих ресурсов
   (чтение - изменение - запись)
     (чтение - изменение - запись)
        | эта операция происходит раньше, чем запись из 1 потока
 */
/*
Д.З. Реализовать задачу расчета годовой инфляции, используя
пул задач
 */
