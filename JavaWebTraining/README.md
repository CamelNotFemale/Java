# Тренировочные проекты Java Web
* __JavaEETest__ - ознакомление с сервлетами, JSP, основы HTTP, работа через Apache Tomcat
* __SpringAppTest__ - ознакомление со Spring Core
  - Инверсия управления
  - Внедрение зависимостей (через конструктор и сеттеры, внедрение ссылок и простых значений, внедрение из внешнего файла - properties)
  - Бины и их конфигурации (scope, init-method, destroy-method, factory-method)
  - Внедрение зависимостей через XML/XML+Аннотации/Java Code конфигурацию
  - @Autowiring, @Qualifier, @Scope, @Value, @PostConstruct, @PreDestroy
* __SpringMVCApp__ - ознакомление со Spring MVC
  - Знакомство с MVC моделью
  - Конфигурация через Java код
  - Контроллеры. @Controller
  - Параметры GET запроса. Аннотация @RequestParam
  - Модель. Передача данных от Контроллера к Представлению.
* __CRUD__ - веб-приложение CRUD, REST с использованием паттерна DAO
  - Аннотация @ModelAttribute. HTML Формы (Thymeleaf)
  - Валидация форм. Аннотация @Valid (hibernate-validator)
  - Базы данных (PostgreSQL)
  - Варианты реализаций связи с БД через *JDBC API*, *JDBC Template(Spring)* и *Hibernate+SpringORM*
  - Стилизация HTML кода через ResourceHandlerRegistry(Spring) на CSS  
* __ClickCounter__ - веб-приложение "Счетчик кликов"