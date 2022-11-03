package step.learning.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)  // аннотация для методов
public @interface MethodAnnotation {
    // аннотация со значением, value - имя по умолчанию, "" - значение по умолч.
    String value() default "" ;
}
