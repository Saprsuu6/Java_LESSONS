package step.learning.services;

import javax.inject.Singleton;
import java.util.Random;

// альтернативное название служб - провайдеры
@Singleton
public class RandomProviderMax implements RandomProvider {
    private final int n = new Random().nextInt() ;

    public int getN() {
        return n;
    }
}
