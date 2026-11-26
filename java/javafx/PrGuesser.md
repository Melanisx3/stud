# PrGuesser

[← К оглавлению](README.md)

**Назначение:** точка входа. Запускает JavaFX и показывает главное окно.

## Исходник

Файл: `src/main/java/prguesser/PrGuesser.java`

```java
package prguesser;

import javafx.application.Application;
import javafx.stage.Stage;

/** Точка входа в приложение. */
public class PrGuesser extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        PrGuesserUI ui = new PrGuesserUI(stage);
        ui.makeUI();
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        ui.focusNumberField();
    }
}
```

## Разбор

- `extends Application`: так объявляется приложение JavaFX.
- `main()` вызывает `launch(args)`. JavaFX инициализируется, создаёт объект `PrGuesser` и вызывает `start()` в потоке интерфейса (JavaFX Application Thread).
- `Stage` — это окно, `Scene` — его содержимое. JavaFX передаёт в `start()` главное окно.
- `new PrGuesserUI(stage)` передаёт ссылку на это окно, новое окно не создаётся.
- `makeUI()` строит интерфейс и готовит первую игру.

## Изменения по сравнению с лекцией

| Изменение | Зачем |
|---|---|
| `setResizable(false)` | пользователь не может менять размер окна (пункт 4) |
| `sizeToScene()` | размер окна подбирается под размер сцены |
| `focusNumberField()` после `show()` | поле числа активно сразу после запуска (пункт 6) |
| убрано `throws Exception` | метод не выбрасывает проверяемых исключений |
| `guui` переименовано в `ui` | более понятное имя |

Окно настроек использует тот же `Stage`, поэтому запрет изменения размера действует и на него.
