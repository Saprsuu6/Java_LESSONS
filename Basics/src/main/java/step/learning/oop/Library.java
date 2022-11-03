package step.learning.oop;

import step.learning.anno.DemoClass;
import step.learning.anno.EntryPoint;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@DemoClass
public class Library {
    private final List<Literature> funds ;

    public Library() {
        funds = new ArrayList<>() ;
    }

    public void add( Literature literature ) {
        funds.add( literature ) ;
    }

    public void printFunds() {
        for( Literature literature : funds ) {
            if( literature instanceof Printable ) {
                ((Printable)literature).print() ;
            }
            else {
                System.out.println( "Unprintable: " + literature.getTitle() ) ;
            }
        }
    }
    @EntryPoint
    public void run() {
        add(new Book()
                .setAuthor( "Knuth" )
                .setTitle( "Art of programming" ) ) ;
        add(new Hologram()
                .setTitle( "Pectoral" ) ) ;
        try {
            add(new Newspaper()
                    .setTitle( "Daily Planet" )
                    .setDate( "2022-09-22" ) ) ;
            add(new Newspaper()
                    .setTitle( "The Washington Post" )
                    .setDate( "2022-09-21" ) ) ;
            add(new Newspaper()
                    .setTitle( "The Daily Mail" )
                    .setDate( "2021-09-21" ) ) ;
        } catch( ParseException ex ) {
            System.out.println( "Funds creation failed: " + ex.getMessage() ) ;
            return ;
        }

        printFunds();
    }
}
// Задача: создать интерфейс Periodic (маркер - без методов)
//  Реализовать его в Newspaper, Journal, сделать метод showPeriodic()

/*
    ООП
    Сущности: Классы, Интерфейсы, Структуры, Абстрактные классы,
      Делегаты, Атрибуты, Enum, Event, Generics
    Инкапсуляция: Поля, модификаторы, конструкторы, деструкторы,
      свойства, методы расширения, статика, список инициализации
    Наследование: множественное наследование/реализация,
      модификаторы наследования, where (Generics),
      виртуальность (и замещение методов), делегирование
    Полиморфизм: преобразование типов UpCast / DownCast, в
      т.ч. проверка принадлежности к типу, перегрузки
-----------------------------------------------------------------
В Java свойств нет, принято создавать аксессоры для полей

Интерфейс (в ООП) - это класс, который содержит
 - только методы
 - только абстрактные
 - только public
-----------------------------------------------------------------
Проверка на принадлежность к классу или интерфейсу производится
оператором instanceof
 if( x instanceof SomeClass )
 if( x instanceof SomeInterface )

 */
