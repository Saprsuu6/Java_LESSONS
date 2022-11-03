package step.learning.anno;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@DemoClass
public class AnnotationsDemo {
    @FieldAnnotation( value = "For all versions", priority = -1 )
    private String separator = "------------------------------------------------";
    @MethodAnnotation( "Entry Point" )
    @EntryPoint
    public void run() {
        // Извлечь аннотацию - получить информацию о типе
        Class<?> type = ClassWithAnnotation.class ;  // по классу
        Class<?> thisType = this.getClass() ;        // по объекту
        Class<?> nameType ;
        try {
            nameType = Class.forName(                // по названию класса
                    "step.learning.anno.ClassWithoutAnnotation" ) ;
        } catch( ClassNotFoundException ex ) {
            System.out.println( "Class not found: " + ex.getMessage() ) ;
            return ;
        }
        // region Check class annotations
        // Запрос аннотации из типа
        MarkerAnnotation marker = type.getAnnotation( MarkerAnnotation.class ) ;
        if( marker != null )
            System.out.printf( "Class '%s' has MarkerAnnotation%n", type.getName() ) ;
        else
            System.out.printf( "Class '%s' has no MarkerAnnotation%n", type.getName() ) ;

        marker = thisType.getAnnotation( MarkerAnnotation.class ) ;
        if( marker != null )
            System.out.printf( "Class '%s' has MarkerAnnotation%n", thisType.getName() ) ;
        else
            System.out.printf( "Class '%s' has no MarkerAnnotation%n", thisType.getName() ) ;

        marker = nameType.getAnnotation( MarkerAnnotation.class ) ;
        if( marker != null )
            System.out.printf( "Class '%s' has MarkerAnnotation%n", nameType.getName() ) ;
        else
            System.out.printf( "Class '%s' has no MarkerAnnotation%n", nameType.getName() ) ;
        // endregion
        System.out.println( separator ) ;

        // region Check method annotations
        Method[] methods = type.getDeclaredMethods() ;   // массив методов (кроме наследованных)
        Object obj ;
        try { obj = type.getDeclaredConstructor().newInstance() ; }
        catch( Exception ex ) {
            System.out.println( "Instantiate error: " + ex.getMessage() ) ;
            return;
        }
        for( Method method : methods ) {
            if( method.isAnnotationPresent( MethodAnnotation.class ) ) {
                MethodAnnotation methodAnnotation = method.getAnnotation( MethodAnnotation.class ) ;
                System.out.printf( "Method '%s' of class '%s' does have '%s' method annotation%n",
                        method.getName(), type.getName(), methodAnnotation.value() ) ;
                // запуск метода - для Recommended запускаем, для Deprecated выдаем отказ
                try {
                    method.setAccessible( true ) ;  // для запуска недоступных методов (private)
                    method.invoke( obj ) ;
                } catch( IllegalAccessException | InvocationTargetException ex ) {
                    System.out.println( "Invoke error: " + ex.getMessage() ) ;
                }
            }
            else {
                System.out.printf( "Method '%s' of class '%s' has not method annotation%n",
                        method.getName(), type.getName() ) ;
            }
        }
        // Задание - просканнировать методы thisType, nameType (не запускать, только вывести данные)
        // endregion
        System.out.println( separator ) ;

        // region Field annotations
        Field[] fields = type.getDeclaredFields() ;
        for( Field field : fields ) {
            if( field.isAnnotationPresent( FieldAnnotation.class ) ) {
                FieldAnnotation fieldAnnotation = field.getAnnotation( FieldAnnotation.class ) ;
                System.out.printf( "Field '%s' of  class '%s' if %s with priority %d%n",
                        field.getName(), type.getName(),
                        fieldAnnotation.value(), fieldAnnotation.priority());
            }
            else {
                System.out.printf( "Field '%s' of  class '%s' has no annotation%n",
                        field.getName(), type.getName() ) ;
            }
        }
        // Задание: вывести данные о полях nameType, thisType; для thisType - вывести
        //  также значение поля
        // endregion

        /*
        Домашнее задание: в классах ClassWithAnnotation, ClassWithoutAnnotation
        реализовать конструкторы, которые задают полям случайные значения.
        В сканнирующем цикле проверить: если у поля есть аннотация, то нужно вывести
        его (поля) значение
          - если объекта нет - создать новый (если у полей вообще нет аннотаций, то
              объект и не будет создан)
          - если есть (ранее создан) - использовать его
         */
    }
}
/* Аннотации
Аннотации (аналог атрибутов в С#) - разновидность интерфейсов, реализуемых
в классах.
Аннотации делят на маркеры (без данных) и аннотации со значениями.
Аннотации играют роль метаданных для класса

Создание аннотаций:
public @interface MarkerAnnotation
 + указание доступности ( @Retention(RetentionPolicy.RUNTIME) - доступна после сборки приложения)
     время разработки (SOURCE) - .java,  время выполнения (RUNTIME) - .class
 + указание цели ( @Target(ElementType.TYPE) ) может ограничить для классов, методов, полей,...

Указание (применение) аннотаций:
 перед нужным элементом добавляем
   @MarkerAnnotation
   public class ClassWithAnnotation ...

Определение аннотаций:

 */
