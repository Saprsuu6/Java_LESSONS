package step.learning;

import step.learning.anno.DemoClass;
import step.learning.anno.EntryPoint;

@DemoClass(priority = 1)
public class DataTypes {
    @EntryPoint
    public void run() {
        System.out.println(
                ConsoleColors.BLUE + "Hello, " +
                ConsoleColors.PURPLE + "World!" +
                ConsoleColors.RESET
        );
        String s1 = new String("Hello");
        String s2 = new String("Hello");
        if(s1 == s2) System.out.println("==");
        else System.out.println("!=");
        // == - reference equality
        if(s1.equals(s2)) System.out.println("equals");
        else System.out.println("!equals");

        String s3 = "Hello, " + "World!";  // Константные строки склад. сразу
        String s4 = "Hello" + ", World!";  // Pool строк - если строка есть в пуле, то новая не создается
        if(s3 == s4) System.out.println("==");
        else System.out.println("!=");

        // Primitives - Value types
        byte bx = -100;   // В Java все числовые типы - знаковые (беззнаковых нет)
        byte by = -0x3C;
        short sx = -30000;
        int ix = 20;      // -1 =(bin)= 1111 1111
        long lx = 100L;
        float fx = 0.1f;  // 0.1 - double
        float fy = (float)0.1;
        double dx = 1.5e-7;
        char c = 'A';     // UTF-16 (2 байта)
        boolean b = true;

        Object obj = ix;  // Общий базовый тип
        System.out.println(obj.getClass());
        Integer iy = 10;  // Object wrap for int

    }
}
/*
Java: Типы данных
Основной единицей данных является класс.
Важно!! Классы и пакеты строго именуются:
 - пакет (package) называет также как и папка (CaseSensitive)
    рекомендуется lowercase
 - класс называется как и файл (CaseSensitive)
    рекомендуется CapitalCamelCase
    = В одном файле - один класс (public)
       Несколько классов могут быть в одном файле, если
       они а) private, б) внутренние (класс в классе)
    = Класс нельзя разделить на несколько файлов
 */