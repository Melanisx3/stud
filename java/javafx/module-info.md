# module-info.java

[← К оглавлению](README.md)

**Назначение:** описывает Java-модуль `prguesser`.

## Исходник

Файл: `src/main/java/module-info.java`

```java
module prguesser {
    requires javafx.controls;
    exports prguesser;
}
```

## Разбор

- `module prguesser`: имя модуля.
- `requires javafx.controls`: нужны элементы интерфейса. Этот модуль транзитивно подключает `javafx.graphics` и `javafx.base`.
- `exports prguesser`: открывает публичные типы пакета другим модулям. В частности, JavaFX получает доступ для создания класса приложения `PrGuesser`.

`javafx.fxml` не подключён, потому что FXML запрещён заданием. Для `java.util` и `java.nio.file` отдельная строка не нужна: они входят в `java.base`, который доступен всегда.

Файл лежит в `src/main/java`, рядом с папкой `prguesser`, а не внутри неё.
