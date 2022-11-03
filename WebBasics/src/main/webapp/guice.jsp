<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="/WEB-INF/headerfragment.jsp" />

<h1>Добавляем к проекту Guice</h1>
<p>
    Guice - модуль для инверсии управления.
    Его установка базируется на свойстве фильтров запускаться первыми,
    а также "слушателях", которые запускаются еще раньше.
    Для работы в веб-проектах необходим как сам Guice,
    https://mvnrepository.com/artifact/com.google.inject/guice/5.1.0
    так его расширение для сервлетов
    https://mvnrepository.com/artifact/com.google.inject.extensions/guice-servlet/5.1.0
</p>
<p>
    Конфигурация модуля состоит из нескольких частей:
    Классы:
     - ConfigListener - точка входа, создание иньектора (вместо main)
     - ConfigServlet - настройка фильтров и сервлетов (можно
        понимать как замену web.xml)
     - ConfigModule - настройка служб (разбирали ранее)
    web.xml:
     - добавляем listener (на наш класс)
     - добавляем фильтр (на GuiceFilter - из servlet-extension)
    по коду:
     - все фильтры, сервлеты и класс ConfigListener аннотируем @Singleton
    по ошибкам:
     - ошибки от Guice попадают в Log-консоль (смотрим в отдельной вкладке)
</p>
<p>
    Проверка - переносим работу с DataService в область зависимостей
    - ConfigModule - устанавливаем связь интерфейса и реализации
    - DataFilter - переносим DataService в зависимости
    - FiltersServlet - переносим DataService из атрибутов в зависимости
</p>
</body>
</html>
