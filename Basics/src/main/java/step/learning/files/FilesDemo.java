package step.learning.files;

import step.learning.anno.DemoClass;
import step.learning.anno.EntryPoint;

import java.io.*;
import java.nio.charset.StandardCharsets;

@DemoClass
public class FilesDemo {
    @EntryPoint
    public void run() {
        // fsDemo() ;
        ioDemo() ;
    }

    // region Работа с файловой системой
    /**
     * Работа с файловой системой
     */
    private void fsDemo() {
        File file = new File( "newItem" ) ;   // "." - текущая (рабочая) директория

        if( file.isDirectory() ) {
            System.out.println( file.getName() + " - is existing directory" ) ;
            String[] list = file.list() ;
            if( list != null ) {
                for( String itemName : list ) {
                    System.out.println( itemName ) ;
                }
            } else System.out.println( "List request error" ) ;

            // Предложим удалить директорию
            System.out.print( "Delete directory? (y/...) " ) ;
            int sym ;
            try {
                sym = System.in.read() ;
            }
            catch( IOException ex ) {
                System.out.println( "System error: " + ex.getMessage() ) ;
                return ;
            }
            if( sym == 'y' ) {
                boolean res = file.delete() ;
                if( res ) {
                    System.out.println( "Directory deleted successfully" ) ;
                }
                else {
                    System.out.println( "Deletion error" ) ;
                }
            }
            else {
                System.out.println( "Delete cancelled" ) ;
            }
        }
        else if( file.isFile() ) {
            System.out.println( file.getName() + " - is existing file" ) ;

            if( file.canRead() ) System.out.println( "Readable" ) ;
            else System.out.println( "Non-Readable" ) ;

            if( file.canWrite() ) System.out.println( "Writable" ) ;
            else System.out.println( "Non-Writable" ) ;

            if( file.canExecute() ) System.out.println( "Executable" ) ;
            else System.out.println( "Non-Executable" ) ;

            System.out.println( "File size: " + file.length() ) ;
        }
        else {
            System.out.println( file.getName() + " - does not exist" ) ;
            boolean res = file.mkdir() ;   // создать директорию
            if( res ) {
                System.out.printf( "Directory '%s' created%n", file.getName() );
            }
            else {
                System.out.println( "Directory creation error" ) ;
            }
        }
    }
    // endregion

    /**
     * Работа с файлами для хранения данных
     */
    private void ioDemo() {
        // чтение из файла. низкоуровневый вариант
        String fileContent ;
        StringBuilder sb = new StringBuilder() ;
        try( InputStream reader = new FileInputStream( "hello.txt" ) ) {
            int symbol ;
            while( ( symbol = reader.read() ) != -1 ) {  // "12345" - 5 символов  (10+45-  100+5000-  1к+500к- )
                // fileContent += (char) symbol ;   // "" - "1" - "12" - "123" - "1234" - мусор: 5 объектов, 10 символов
                sb.append( (char) symbol ) ;
            }
            // fileContent = sb.toString() ; - переносим за блок try, это позволит
            // System.out.println( fileContent ) ;  быстрее закрыть поток (файл)
        }
        catch( IOException ex ) {
            System.out.println( ex.getMessage() ) ;
            return ;
        }
        // fileContent = sb.toString() ;   // Кодировка по умолчанию - ISO 8859-1 (ASCII)
        fileContent = new String(          // Если нужно менять кодировку
                sb.toString().getBytes(    // используем конструктор строки
                        StandardCharsets.ISO_8859_1 ),
                StandardCharsets.UTF_8 ) ;

        System.out.println( fileContent ) ;

        // Запись в файл на примере более высокого уровня
        try( FileWriter writer = new FileWriter( "result.txt" ) ) {
            writer.write( "Здоровенькі були!" ) ;
            writer.flush() ;
        }
        catch( IOException ex ) {
            System.out.println( ex.getMessage() ) ;
            return ;
        }
        System.out.println( "Write finished" ) ;
    }
}
/* Работа с файлами
Традиционно, работа с файлами содержит в себе два аспекта:
 - работа с файловой системой - навигация по папкам, просмотр
    их содержимого, создание файлов и папок, копирование, удаление, ...
 - использование файлов как источников данных - сохранение,
    чтение файлов.

Файл (в информатике) - совокупность данных, объединенных под именем.
В развитии - добавилась идея потоков (stream), что усложняет
понимание границ файлов, вводя "бесконечные" файлы, по типу
Интернет-радио


1. Работа с файловой системой
Основной объект - File (java.io). Объект отвечает как за работу с
файлами, так и за работу с папками.
!! Создание объекта не приводит к изменениям в файловой системе
   (файлы/папки не создаются). Объекты являются их "отражением"
   в программе.

2. Использование файлов для работы с данными
Существует большое множество способов и инструментов для работы
 с файлами.
Чтение из файла: основная проблема - нам может быть не известен
 полный объем данных, содержащихся в файле. Это очень вероятно
 в задачах сетевого обмена.
 Решения: а) считываем по одному символу и накапливаем их в строку
 б) считываем массивами byte[], состыковываем их при переполнении

reader.available() > 0   vs   read() != -1
 reader.available() может "отставать" - возвращать 0, но за время
   этого возврата данные могут появиться
 read() != -1  -- более гарантированный вариант  того, что в потоке
   больше нет данных
 */
