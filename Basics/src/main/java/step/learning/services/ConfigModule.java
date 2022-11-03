package step.learning.services;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import step.learning.services.hash.HashService;
import step.learning.services.hash.MD5HashService;
import step.learning.services.hash.Sha1HashService;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind( SymbolService.class )          // интерфейс
                .to( CharService.class ) ;   // класс-реализация

        bind( HashService.class )  // автоматический анализ Singleton
                .annotatedWith( Names.named("128") )
                .to( MD5HashService.class ) ;
        bind( HashService.class )
                .annotatedWith( Names.named("160") )
                .to( Sha1HashService.class ) ;

        bind( String.class )
                .annotatedWith( Names.named("MsConnectionString") )
                .toInstance( "Data Source=MSSQLLocalDb;..." ) ;
    }

    @Provides @Named("OracleConnectionString")
    String getOracleCS() {
        return "oracle:host=localhost..." ;
    }

    @Provides   // альтернатива bind - методы-провайдеры
    @Named("max")
    RandomProvider getRandomProviderMax() {  // + можно вызвать конструкторы с пар-ми
        return new RandomProviderMax() ;     // - Singleton нужно реализовывать самим
    }  // связывание - по типу возврата (RandomProvider) - где понадобится
    // внедрение RandomProvider, там будет запущен данный метод

    private RandomProvider r10 ;   // схема реализации Singleton
    @Provides @Named("ten")
    RandomProvider getRandomProviderTen() {
        if( r10 == null
            || RandomProviderTen.class.isAnnotationPresent( com.google.inject.Singleton.class )
            || RandomProviderTen.class.isAnnotationPresent( javax.inject.Singleton.class )
        ) r10 = new RandomProviderTen() ;
        return r10 ;
    }

}
/*
Один из примеров именованных иньекций - строки
MsConnectionString
OracleConnectionString
Задание: реализовать
 В App
    @Inject @Named("MsConnectionString")
    private String MsConnectionString ;
 В ConfigModule

 */
