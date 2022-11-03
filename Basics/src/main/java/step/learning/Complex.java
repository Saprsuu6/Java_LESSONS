package step.learning;

import step.learning.anno.DemoClass;
import step.learning.anno.EntryPoint;

import java.util.*;
@DemoClass
public class Complex {
    private final Random random ;

    public Complex() {
        random = new Random();
    }

    private void arraysDemo() {
        int[] arr1 = new int[4];  // default init - 0
        int[] arr2 = new int[] { 5,4,3,2,1 };
        int[] arr3 = { 5,4,3,2,1 };
        for( int i = 0; i < 4; ++i ) {
            System.out.println(
                String.format( "arr1[ %d ] = %d",
                    i, arr1[i] ) ) ;
        }
        System.out.println("---------------------------");

        int j = 0;
        for( ; j < arr2.length; j++ ) {
            System.out.println( arr2[j] ) ;
        }
        System.out.println("---------------------------");
        for( int a : arr3 ) {
            System.out.println( a ) ;
        }
        // Jagged array
        System.out.println("===========================");
        int[][] arr4 = {
                { 1,2,3 },
                { 4,5,6,7 },
                { 8,9 }
        };
        printArr( arr4 ) ;
        System.out.println("---------------------------");

        // Rect array
        int[][] arr5 = new int[3][4];
        randArr( arr5 ) ;
        printArr( arr5 ) ;
        System.out.println("---------------------------");
    }

    private void printArr(int[][] arr) {
        for( int[] a : arr ) {
            for( int x : a ) {
                System.out.print( x + " " ) ;
            }
            System.out.println();
        }
    }

    private void randArr(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = random.nextInt(42);
            }
        }
    }

    private void collectionsDemo() {
        List<Integer> arr1 = new ArrayList<>();
        arr1.add( 10 ) ;
        arr1.add( 20 ) ;
        arr1.add( 30 ) ;
        arr1.add( 40 ) ;
        for( Integer x : arr1 ) {
            System.out.print( x + " " ) ;
        }
        System.out.println();
        System.out.println("---------------------------");
        arr1.set(1, 21);
        arr1.remove(3);
        for (int i = 0; i < arr1.size(); i++) {
            System.out.printf( "i=%d, x=%d%n", i, arr1.get(i) ) ;
        }
        System.out.println("=============================");

        Map<String, String> map = new HashMap<>();
        map.put( "Hello", "Привет" ) ;
        map.put( "Bye", "Пока" ) ;
        map.put( "Hi", "Здравствуйте" ) ;
        for( String key : map.keySet() ) {
            System.out.printf( "%s -- %s\n", key, map.get(key) ) ;
        }
        System.out.println("---------------------------");

    }

    private void dictionaryUI() {
        Map<String, String> map = new HashMap<>();
        map.put( "Hello", "Привет" ) ;
        map.put( "Bye", "Пока" ) ;
        map.put( "Hi", "Здравствуйте" ) ;
        System.out.print(
                "Англо-Украинский словарь\n" +
                "    1. Показать все\n" +
                "    2. Перевод англ. слова\n" +
                "    3. Перевод укр. слова\n" +
                "    *4. Добавить слово\n" +
                "    0. Выход\n" +
                "Введите выбор: " ) ;
        Scanner kbScanner = new Scanner( System.in ) ;
        int userSelection = kbScanner.nextInt() ;
        System.out.println( userSelection ) ;
        // String str = kbScanner.nextLine() ;
        // while (System.in.available()){ int c = System.in.read(); ...
        // String translate = map.get( str ) ;
        // System.out.println(str + " - " + translate ) ;
    }

    @EntryPoint
    public void run() {
        arraysDemo();
        collectionsDemo();
        dictionaryUI();
    }
}
/*
    Комплексные типы данных - массивы и коллекции
    Массивы - ссылочные типы данных
    Коллекции - "элластичные" массивы
     - Линейные (списки)
        ArrayList, LinkedList, Vector, стек, очередь
     - Множество (set) - не допускают повторов
     - "Словарь" (Map)
 */
/*
    Задание: UI работы со словарем
    Англо-Украинский словарь
    1. Показать все
    2. Перевод англ. слова
    3. Перевод укр. слова
    *4. Добавить слово
    0. Выход
    Введите выбор:
 */