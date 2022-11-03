package step.learning.serial;

import step.learning.anno.DemoClass;
import step.learning.anno.EntryPoint;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@DemoClass
public class SerializationDemo {

    @EntryPoint
    public void run() {
        // serializeList() ;
        deserializeList() ;
    }

    public void deserialize() {
        this.serialize() ;
        // Десериализация
        try( FileInputStream file = new FileInputStream( "save.ser" ) ) {
            ObjectInputStream ois = new ObjectInputStream( file ) ;
            while( file.available() > 0 ) {
                DataObject data = (DataObject) ois.readObject();
                System.out.println(data);
            }
        }
        catch( Exception ex ) {
            System.out.println( "Error deserialization: " + ex.getMessage() ) ;
            return ;
        }
        System.out.println( "Done" ) ;
    }

    public void serialize() {
        DataObject data1 = new DataObject();
        DataObject data2 = new DataObject(10);
        DataObject data3 = new DataObject(10, 20.5f);
        DataObject data4 = new DataObject(10, 20.5f, "Hello", "Trans");
        System.out.println("------------------ Initial ----------------");
        System.out.println(data1);
        System.out.println(data2);
        System.out.println(data3);
        System.out.println(data4);

        // try-with-resource
        try( FileOutputStream file = new FileOutputStream( "save.ser" ) ) {
            ObjectOutputStream oos = new ObjectOutputStream( file ) ;
            oos.writeObject( data1 ) ;  // добавить 2, 3
            oos.writeObject( data4 ) ;
            oos.flush() ;
        }
        catch( IOException ex ) {
            System.out.println( "Error serialization: " + ex.getMessage() ) ;
            return ;
        }
        System.out.println( "Serialized" ) ;

    }

    public void serializeList() {
        List<DataObject> list = new ArrayList<>() ;
        list.add( new DataObject(110, 10f, "Hello 1", "Trans") ) ;
        list.add( new DataObject(120, 20f, "Hello 2", "Trans") ) ;
        list.add( new DataObject(130, 30f, "Hello 3", "Trans") ) ;
        list.add( new DataObject(140, 40f, "Hello 4", "Trans") ) ;
        list.add( new DataObject(150, 50f, "Hello 5", "Trans") ) ;
        try( FileOutputStream file =
                     new FileOutputStream( "list.ser" ) ) {
            ObjectOutputStream oos = new ObjectOutputStream( file ) ;
            oos.writeObject( list ) ;
            oos.flush() ;
        }
        catch( Exception ex ) {
            System.out.println( "Error serialization: " + ex.getMessage() ) ;
            return ;
        }
        System.out.println( "Done" ) ;
    }

    public void deserializeList() {
        try( FileInputStream file = new FileInputStream( "list.ser" ) ) {
            ObjectInputStream ois = new ObjectInputStream( file ) ;
            /* // Warning: unchecked cast
            @SuppressWarnings("unchecked")
            List<DataObject> list = (List<DataObject>) ois.readObject() ;
            System.out.println( "Deserialized list:" ) ;
            for( DataObject data : list ) {
                System.out.println( data ) ;
            } */
            List<?> list = (List<?>) ois.readObject() ;
            for( Object data : list ) {
                if( data instanceof DataObject ) {
                    System.out.println( data ) ;
                }
            }
        }
        catch( Exception ex ) {
            System.out.println( "Error deserialization: " + ex.getMessage() ) ;
            return ;
        }
        System.out.println( "-- Done --" ) ;
    }
}
/*
Сериализация (от англ. - последовательный) - представление
данных (обычно объектов) в виде некоторой последовательности.
Традиционно разделяют
 текстовую сериализацию (JSON, XML, CSV, неформат)
 бинарную сериализацию (смешанную)
           "10" - (byte)49-(byte)48
 int(10) <
           00..(32bit)..01010

Сериализация потенциально опасна - предоставляет возможность
 "рассекретить" объект. Поэтому по умолчанию объекты не могут
 быть сериализованы, а для разрешения сериализации они должны
 реализовать интерфейс-маркер Serializable. Все наследники
 класса также будут Serializable

ObjectOutputStream - "инструмент" для сериализации объектов.
 создается над потоком записи, то есть предварительно нужно,
 например, открыть файл в потоковом режиме на запись.
 Поток смешанный (бинарный), сохраняет данные о типе объекта и
 названии полей, сериализуются все поля, независимо от видимости.
Результат - в один файл можно сериализовать несколько объектов,
 десериализуются в том же порядке, в котором записываются.
 Защитить от сериализации поле можно ключевым словом transient.

Сериализация коллекций: при десериализации
  List<DataObject> list = (List<DataObject>) ois.readObject()
 выдается предупреждение о непроверяемом преобразовании.
 Это особенность Generics: List<T> может быть проверен только
 на то, что он List, но не на конкретные объекты.
 При необходимости, проверка на тип должна проводиться для
 каждого объекта коллекции.

 Д.З. Впровадити серіалізацію фондів бібліотеки.
 На початку роботи "бібліотека" намагається десеріалізувати фонди
 та видає загальну кількість зчитаних даних.
 Далі пропонує меню щодо відображення за видами (Printable, Periodic...)
 */
