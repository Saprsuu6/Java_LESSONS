package step.learning;

import javax.inject.Inject;

import com.google.inject.name.Named;
import step.learning.anno.DemoClass;
import step.learning.anno.EntryPoint;
import step.learning.services.RandomProvider;
import step.learning.services.StringService;
import step.learning.services.hash.HashService;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    @Inject  // ссылка на объект будет инициализирована контейнером (иньектором)
    private StringService stringService ;


    // @Inject @Named("ten")
    private final RandomProvider randomProvider ;
    @Inject
    public App(@Named("ten") RandomProvider randomProvider) {
        // иньекция через конструктор лучше тем, что позволяет использовать final поля
        this.randomProvider = randomProvider;
    }

    @Inject @Named("128")
    private HashService hash128 ;
    @Inject @Named("160")
    private HashService hash160 ;

    @Inject @Named("MsConnectionString")
    private String msConnectionString ;
    @Inject @Named("OracleConnectionString")
    private String oracleConnectionString ;

    public void run() {
        System.out.println( "IoC Demo" ) ;
        System.out.println( "StringService: " + stringService.getString() ) ;
        System.out.println( "RandomProvider: " + randomProvider.getN() ) ;
        System.out.println( "HashService (128bit): " + hash128.hash( "Hello" ) ) ;
        System.out.println( "HashService (160bit): " + hash160.hash( "Hello" ) ) ;
        System.out.println( "MsConnectionString: " + msConnectionString ) ;
        System.out.println( "OracleConnectionString: " + oracleConnectionString ) ;
    }

    public void runMenu() {
        // Определяем текущий класс
        Class<?> currentClass = Main.class ;
        // Извлечь из имени класса имя пакета
        String packageName = currentClass.getPackage().getName() ;
        // Получаем перечень имен классов из пакета и его под-пакетов (1 уровень вложенности)
        List<String> classNames = getClassNames( packageName ) ;
        if( classNames == null ) {
            System.out.println( "Error scanning package" ) ;
            return ;
        }

        // region выбрать только те классы, которые аннотированы как "DemoClass"
        List<Class<?>> demoClasses = new ArrayList<>() ;
        for( String className : classNames ) {
            Class<?> theClass ;
            try {
                theClass = Class.forName( className ) ;
            }
            catch( Exception ignored ) {  // ignored - спец. имя для неиспользуемой переменной
                continue ;
            }
            if( theClass.isAnnotationPresent( DemoClass.class ) ) {
                demoClasses.add( theClass ) ;
            }
            // System.out.println( className ) ;
        }
        // endregion

        // region Вывести меню и предложить выбор
        System.out.println( "Demo classes:" ) ;
        int i = 1 ;
        for( Class<?> theClass : demoClasses ) {
            System.out.printf( "%d. %s %n", i++, theClass.getName() ) ;
        }
        System.out.println( "0. Exit" ) ;
        System.out.print( "Make your choice: " ) ;
        // endregion

        // region Получить выбор, обработать (на ошибки) и запустить (EntryPoint)
        Scanner kbScanner = new Scanner( System.in ) ;
        int userChoice = -1 ;
        try {
            userChoice = kbScanner.nextInt() ;
        }
        catch( InputMismatchException ignored ) {
            System.out.println( "Incorrect choice" ) ;
        }

        if( userChoice == 0 ) {
            System.out.println( "Bye" ) ;
            return ;
        }
        int index = userChoice - 1 ;
        if( index >= demoClasses.size() || index < 0 ) {
            System.out.println( "Incorrect choice" ) ;
        }
        else {
            Class<?> exeClass = demoClasses.get( index ) ;
            // сканнируем методы на аннотацию EntryPoint и запускаем первый найденный
            Method[] methods = exeClass.getDeclaredMethods() ;
            for( Method method : methods ) {
                if( method.isAnnotationPresent( EntryPoint.class ) ) {
                    try {
                        method.invoke(
                                exeClass.getDeclaredConstructor().newInstance()
                        ) ;
                    }
                    catch( Exception ex ) {
                        System.out.println( "Execution error: " + ex.getMessage() ) ;
                    }

                    break ;
                }
            }
        }

        System.out.println( "Choice: " + userChoice ) ;
        // endregion
/*
Д.З. Обеспечить повтор диалога (меню-выбор) до выбора выхода
При формировании меню учесть приоритет, указанный в DemoClass аннотации
Добавить вывод уведомления в случае если в классе не будет найден метод EntryPoint
* Поскольку диалог цикличный, учитыватать тот факт, что объект класса мог
   быть ранее создан, в таком случае использовать "старый" объект, новый не создавать.

 */
    }
    private static List<String> getClassNames( String packageName ) {
        // class loader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader() ;
        URL packageResource =
                classLoader.getResource(         // classLoader - "системный" элемент,
                        packageName.replace(     // способный определить файловый
                                ".",             // ресурс по имени (соотношение пакетов
                                "/" )            // и реальных файлов). В имени ресурса
                ) ;                              // вместо "." должны быть "/"
        if( packageResource == null ) {
            System.out.println( "Resource identification error" ) ;
            return null ;
        }
        String packagePath = packageResource.getPath() ;  // Реальный файловый путь
        // System.out.println( packagePath ) ;
        // Сканнируем как папку, определяем содержимое
        File packageBase = new File( packagePath ) ;
        File[] list = packageBase.listFiles() ;
        if( list == null ) {
            System.out.println( "Error scanning directory " + packageBase ) ;
            return null ;
        }
        List<String> classNames = new ArrayList<>() ;
        for( File file : list ) {
            // задание: если имеем директорию, вывести ее состав (один уровень вложенности)
            // задание: нас интересуют только файлы типа ".class", собираем информацию о них
            //  в коллекцию; для работы с классом нужно только имя, но с учетом пакета
            // System.out.println( file.getName() + " " + ( file.isFile() ? "file" : "dir" ) ) ;
            if( file.isFile() ) {
                String filename = file.getName() ;
                if( filename.endsWith( ".class" ) ) {
                    classNames.add(
                            packageName + "." +
                                    filename.substring( 0, filename.lastIndexOf( '.' ) )
                    ) ;
                }
            }
            else if( file.isDirectory() ) {
                File[] subList = file.listFiles() ;
                if( subList != null ) {
                    for (File sub : subList) {
                        // System.out.println( "  " + sub.getName() + " " + ( sub.isFile() ? "file" : "dir" ) ) ;
                        if( sub.isFile() ) {
                            String filename = sub.getName() ;
                            if( filename.endsWith( ".class" ) ) {
                                classNames.add(
                                        packageName + "." + file.getName() + "." +
                                                filename.substring( 0, filename.lastIndexOf( '.' ) )
                                ) ;
                            }
                        }
                    }
                }
            }
        }
        return classNames ;
    }
}
