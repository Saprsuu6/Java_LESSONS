package step.learning.threading;

import step.learning.ConsoleColors;
import step.learning.anno.DemoClass;
import step.learning.anno.EntryPoint;

@DemoClass
public class ThreadingDemo {
    private String str ;

    @EntryPoint
    public void demo() {
        new PrinterThread().start() ;  // run - вызов метода (синхронный), start - в потоке

        new ArgThread( "arg1" ).start() ;

        ArgThread argThread = new ArgThread() ;
        argThread.setArg( "arg2" ) ;
        argThread.start() ;

        new Thread() {             // Анонимный объект анонимного класса:
            @Override              // переопределяем класс и тут же создаем объект
            public void run() {
                System.out.println(
                        ConsoleColors.YELLOW_BOLD + "Anon thread" + ConsoleColors.RESET ) ;
            }
        }.start() ;

        new Thread( new PrintRunnable() ).start() ;  // использование Runnable

        str = "param1" ;
        new Thread( new Runnable() {   // анонимная реализация Runnable
            @Override
            public void run() {
                System.out.println(
                    ConsoleColors.BLUE_BOLD_BRIGHT + "Anon Runnable " + str + ConsoleColors.RESET ) ;
            }
        } ).start() ;

        str = "param2" ;
        Runnable runnable = () -> {
            System.out.println( "Arrow runnable " + str ) ;
        } ;
        new Thread( runnable ).start() ;

        str = "param3" ;
        new Thread( () -> System.out.println( "Arrow in Thread " + str ) ).start() ;

        new Thread( this::printDemo ) ;  // :: - Runnable from this.printDemo

        System.out.println( ConsoleColors.RED_BOLD_BRIGHT + "Threading demo" + ConsoleColors.RESET ) ;
    }

    private void printDemo() {
        System.out.println(
                ConsoleColors.GREEN_BOLD_BRIGHT + "printDemo method" + ConsoleColors.RESET ) ;
    }

    static class PrintRunnable       //
            implements Runnable {    // интерфейс для задач запуска (~функтор, ~лямбда)
        @Override
        public void run() {
            System.out.println( "PrintRunnable" ) ;
        }
    }

    static class PrinterThread   // Основу работу с потоками составляет
            extends Thread {     // класс Thread
        @Override
        public void run() {      // Перегруженный метод run определяет то, что выполняется
            System.out.println(
                    "PrinterThread works" ) ;
        }
    }

    static class ArgThread       // Метод run не имеет перегрузок, поэтому не принимает
            extends Thread {     // параметров.
        private String arg ;     // Для передачи параметров в поток они инкапсулируются
        @Override
        public void run() {
            System.out.println( "Arg thread: " + this.arg ) ;
        }
        public void setArg(String arg) {
            this.arg = arg;
        }
        public ArgThread() {
            this.arg = "";
        }
        public ArgThread(String arg) {
            this.arg = arg;
        }
    }
}
/*
    Многопоточность.
    Поток - системный ресурс, запускающий код (активность) параллельно с другими потоками
    Процесс - запуск приложения (программы), потоки находятся в процессе,
      окончание последнего из потоков заканчивает и процесс
    Многопоточность - эволюционный путь увеличения быстродействия. Однопоточная
      программа ограничена в ресурсах процессора.
    Многопоточные приложения отличаются тем, что завершение потоков не имеет
      гарантированной последовательности (отличается от запуска к запуску).
      Это косвенный признак хорошего использования ресурсов ПК.
      Традиционно, стараются создать порядок, используя синхронизацию,
      НО это не очень эффективная стратегия, лучше модифицировать задачу (алгоритм)
      для работы с произвольным порядком.

    Синхронность - последовательное выполнение блоков кода
    Асинхронность - произвольное  (10 блоков -> 3 параллельно)
    Параллельность - одновременное
     Многопоточность - использование Thread с normal приоритетом (остаются
        после завершения основного потока)
     Многозадачность - использвание "задач"- "оболочек" над потоками, чаще
       всего фонового приоритета (разрушаются после завершения основного потока)
       Также задача - возвращает значение, поток - нет
     Запуск новых процессов

Задание: Пользователь вводит число N, программа запускает N потоков, каждый
из которых выводит свой номер "Thread X works"
 */
