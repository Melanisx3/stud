# GameLogger

[← К оглавлению](README.md)

**Назначение:** дозапись и очистка `LogGuess.txt`. Новый класс для пунктов 13 и 14.

## Исходник

Файл: `src/main/java/prguesser/GameLogger.java`

```java
package prguesser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/** Работа с лог-файлом: дозапись и очистка. */
public class GameLogger {

    // Относительный путь: файл создаётся в рабочем каталоге (корне проекта).
    private final Path file = Path.of("LogGuess.txt");
    private boolean enabled;     // по умолчанию запись не ведётся
    private boolean headerNeeded = true;
    private String header = "";

    /** Запоминает заголовок новой игры. */
    public void newGame(Game game) {
        header = "=== Игра №" + game.number() + ": " + game.settings().description() + " ===";
        headerNeeded = true;
    }

    /** Если заголовок этой игры ещё не записан, записывает его. */
    public void writeHeader() throws IOException {
        if (enabled && headerNeeded) {
            append(header);
            headerNeeded = false;
        }
    }

    public void write(String message) throws IOException {
        if (enabled) {
            writeHeader();
            append(message);
        }
    }

    public void clear() throws IOException {
        Files.writeString(file, "", StandardCharsets.UTF_8);
        headerNeeded = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Path file() {
        return file.toAbsolutePath();
    }

    private void append(String line) throws IOException {
        Files.writeString(file, line + System.lineSeparator(), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
```

## Поля

| Поле | Смысл |
|---|---|
| `file` | путь к `LogGuess.txt` |
| `enabled` | включена ли запись; у `boolean` значение по умолчанию `false` |
| `headerNeeded` | нужно ли записать заголовок перед следующим сообщением |
| `header` | заголовок текущей игры |

`Path.of("LogGuess.txt")` задаёт путь относительно **рабочего каталога**, а не расположения класса. При `javafx:run` с настройками проекта это должна быть папка проекта. Если запускаете другим способом, проверьте Working directory.

## Методы

- `newGame()` запоминает заголовок, но сам в файл не пишет.
- `writeHeader()` пишет заголовок, если лог включён и это ещё не сделано.
- `write()` при включённом логе сначала обеспечивает наличие заголовка, затем пишет сообщение.
- `clear()` заменяет содержимое пустой строкой и снова требует заголовок. Если файла не было, он создаётся.
- `isEnabled()` и `setEnabled()` читают и меняют состояние. `isEnabled()` сейчас в проекте не вызывается.
- `file()` возвращает абсолютный путь для подсказки у флажка.
- `append()` — единственный метод дозаписи.

## Отложенный заголовок

Если лог включили в середине игры, сначала появится заголовок с исходным лимитом, затем сообщение о включении с текущим остатком, потом следующие действия. Ходы, сделанные при выключенном логе, задним числом не записываются. При повторном включении в той же игре заголовок не дублируется. После очистки он добавляется перед следующей записью.

`headerNeeded = false` выполняется только после успешной записи.

## Работа с файлом

- `CREATE` создаёт файл, если его нет.
- `APPEND` добавляет в конец и не стирает прошлые записи.
- `UTF_8` сохраняет русский текст в известной кодировке.
- `System.lineSeparator()` даёт перевод строки текущей системы.
- `Files.writeString()` сам открывает и закрывает файл.

`IOException` передаётся наружу через `throws`. [PrGuesserUI](PrGuesserUI.md) показывает сообщение, поэтому логгер не зависит от JavaFX.

## Пример формата

```text
=== Игра №1: диапазон от 1 до 100, доступно попыток: 7 ===
Пользователь включил запись лога; осталось попыток: 7
Ввод: "50"; вердикт: Загаданное число больше; осталось попыток: 6
Пользователь отключил запись лога
```
