Стартер логирования с использованием Spring-interceptor.

При подключении стартера автоматически включается логирование всех запросов приходящих в контроллеры
и выходящих из Rest Template (чтобы удостовериться, можно внедрять его с @Qualifier("loggingRestTemplate")).

Для настройки можно воспользоваться application.properties файлом.

Возможные(и дефолтные) значения переметров:

1) http-logging.configuration.enabled: true.

Отвечает за то, появятся ли нужные классы для логирования в classpath. Default = true.

2) http-logging.configuration.log-level: info. 

Уровень логирования, куда будет отравляться информация. Маленькими буквами. Default = info.

3) http-logging.configuration.time-request-parameter: startTime

При использовании интерцепторов в запрос перед началом выполнения будет ставиться url-encoded значение 
с названием соответствующем данному параметру. Что бы он точно не мешал можно его настроить. Default – startTime.

4)  http-logging.configuration.log-format: %M %R %r

Формат вывода логов. (Для того, чтобы использовать % в yml конфигурации необходимо обернуть строку в одинарные кавычки).
Использует локальные сокращения. Default: '%n%M %u %nHeaders:%n%h%n%H%n%R %r %tms.'

- %M – Method
- %u – URL
- %h – Request Headers. If empty EMPTY_HEADERS is printed
- %H – Response Headers. If empty EMPTY_HEADERS is printed
- %r – Код ответа(строка). Например: Not Found, Forbidden.
- %R – Код ответа(цифра).
- %t – Время обработки запроса.
- %n – Новая строка.

Пример лога с дефолтной конфигурацией

2024-09-25T00:13:04.174+03:00  INFO 20937 --- [logging-starter] [           main] c.s.s.logging_starter.logger.HttpLogger  : <br/>
GET http://localhost/test <br/>
Headers: <br/>
EMPTY_HEADERS <br/>
EMPTY_HEADERS <br/>
200 OK 11ms.

