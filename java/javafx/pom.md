# pom.xml

[← К оглавлению](README.md)

**Назначение:** сообщает Maven, как собирать и запускать проект. Это не Java-класс.

## Исходник

Файл: `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>prguesser</groupId>
    <artifactId>PrGuesser</artifactId>
    <version>1.0</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.release>25</maven.compiler.release>
        <javafx.version>25.0.1</javafx.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
            </plugin>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>prguesser/prguesser.PrGuesser</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## Разбор

| Элемент | Что означает |
|---|---|
| `modelVersion` | версия формата POM |
| `groupId`, `artifactId`, `version` | идентификатор и версия проекта |
| `project.build.sourceEncoding` | UTF-8, чтобы русские строки читались правильно |
| `maven.compiler.release` | сборка под Java 25 |
| `javafx.version` | версия JavaFX в одном месте |
| `javafx-controls` | единственная прямая зависимость; остальные нужные модули Maven подтянет сам |
| `maven-compiler-plugin` | компилирует исходники |
| `javafx-maven-plugin` | даёт команду `javafx:run` |
| `mainClass` | `имя_модуля/пакет.Класс`, здесь `prguesser/prguesser.PrGuesser` |

`${javafx.version}` подставляет значение из `<properties>`.

Плагины Maven скачиваются Maven. Это не плагины IntelliJ IDEA из Marketplace. При первой загрузке нужен доступ к репозиторию Maven.

Эта конфигурация в моей среде полной сборкой не проверялась (см. [оглавление](README.md#что-проверено)).
