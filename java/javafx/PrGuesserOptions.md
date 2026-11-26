# PrGuesserOptions

[← К оглавлению](README.md)

**Назначение:** окно настроек. Показывает значения, проверяет ввод и передаёт результат в главное окно.

## Исходник

Файл: `src/main/java/prguesser/PrGuesserOptions.java`

```java
package prguesser;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Окно настроек: ввод и проверка данных. */
public class PrGuesserOptions {

    private static final String NOT_INTEGER = "Введите целое число";

    private final PrGuesserUI ui;
    private final TextField tfLeft = new TextField();
    private final TextField tfRight = new TextField();
    private final TextField tfAttempts = new TextField();
    private final Button btnSave = new Button("Сохранить настройки");
    private final Button btnCancel = new Button("Отмена");
    private Scene scene;

    public PrGuesserOptions(PrGuesserUI ui) {
        this.ui = ui;
    }

    public void makeUI() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        grid.addRow(0, new Label("Левая граница"), tfLeft);
        grid.addRow(1, new Label("Правая граница"), tfRight);
        grid.addRow(2, new Label("Количество попыток"), tfAttempts);

        for (TextField field : new TextField[]{tfLeft, tfRight, tfAttempts}) {
            field.setPrefWidth(220);
            field.setMaxWidth(220);
            // Как только текст меняется, красный цвет убирается.
            field.textProperty().addListener((obs, oldText, newText) -> field.setStyle(""));
        }

        btnSave.setDefaultButton(true); // Enter
        btnCancel.setCancelButton(true); // Esc
        btnSave.setOnAction(this::handleSave);
        btnCancel.setOnAction(eh -> ui.cancelSettings());

        HBox buttons = new HBox(15, btnSave, btnCancel);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(25, grid,
                new Label("0 или отрицательное число попыток: без ограничения"), buttons);
        root.setAlignment(Pos.CENTER);

        scene = new Scene(root, 500, 400);
    }

    /** Показывает окно, заполняя поля действующими настройками. */
    void show(Stage stage, GameSettings current) {
        tfLeft.setText(String.valueOf(current.leftBound()));
        tfRight.setText(String.valueOf(current.rightBound()));
        tfAttempts.setText(String.valueOf(current.maxAttempts()));
        stage.setScene(scene);
        stage.setTitle("Настройки игры");
        stage.sizeToScene();
        focus(tfLeft);
    }

    private void handleSave(ActionEvent event) {
        // Сначала читаем все поля, чтобы отметить все ошибки сразу.
        Integer left = parse(tfLeft);
        Integer right = parse(tfRight);
        Integer attempts = parse(tfAttempts);

        if (left != null && right != null && right <= left) {
            // long: left + 1 не переполнится при Integer.MAX_VALUE
            showFieldError(tfRight, "Число, не меньшее " + (left + 1L));
            right = null;
        }

        if (left == null) {
            focus(tfLeft);
        } else if (right == null) {
            focus(tfRight);
        } else if (attempts == null) {
            focus(tfAttempts);
        } else {
            ui.applySettings(new GameSettings(left, right, attempts));
        }
    }

    /** Возвращает число или null, показав ошибку в поле. */
    private Integer parse(TextField field) {
        try {
            return Integer.valueOf(field.getText().trim());
        } catch (NumberFormatException e) {
            showFieldError(field, NOT_INTEGER);
            return null;
        }
    }

    private void showFieldError(TextField field, String message) {
        field.setText(message);
        // highlight-свойства сохраняют красный цвет и у выделенного текста
        field.setStyle("-fx-text-fill: red; -fx-highlight-fill: #ffe0e0; -fx-highlight-text-fill: red;");
    }

    private void focus(TextField field) {
        field.requestFocus();
        field.selectAll();
    }
}
```

## Что изменено после лекции

| Изменение | Пункт |
|---|---|
| заголовок «Настройки игры» | −1 |
| кнопка «Отмена» | 0 |
| поле и метка количества попыток | 1 |
| проверка и сообщения об ошибках | 2 |
| сцена 500×400 | 4 |
| ширина полей 220 | 5 |
| фокус на левой границе | 7 |
| передача `GameSettings` | 8 |
| обработчик сохранения в отдельном методе | 10 |

## `makeUI()`

- `GridPane` заменяет два отдельных `HBox`: каждая метка стоит в одной строке со своим полем.
- `addRow()` добавляет такую строку.
- Цикл одинаково настраивает три поля. Ширина 220 нужна, чтобы помещались сообщения об ошибках.
- Слушатель `textProperty()` убирает красный стиль при изменении текста.
- `setDefaultButton(true)` назначает Enter сохранению, `setCancelButton(true)` назначает Esc отмене.
- У «Отмены» обработчик из одного оператора, поэтому по пункту 10 достаточно лямбды.

## `show()`

Заполняет поля **действующими** настройками, меняет сцену и заголовок, фокусирует левую границу. Благодаря этому после «Отмены» черновые значения и сообщения об ошибках не остаются при следующем открытии.

## `handleSave()`

1. Разбирает все три поля, чтобы сразу отметить все нечисловые значения.
2. Если обе границы — числа и `right <= left`, пишет в правое поле «Число, не меньшее L», где L = `left + 1`.
3. Ставит фокус на первое ошибочное поле: левое, правое, попытки.
4. Если ошибок нет, создаёт `GameSettings` и вызывает `ui.applySettings()`.

`right = null` помечает правую границу как ошибочную. Если левая граница не разобрана, сравнить с ней правую нельзя.

**Крайний случай:** при `left = Integer.MAX_VALUE` большей правой границы типа `int` не существует. Программа покажет «Число, не меньшее 2147483648», но такое значение в `int` не помещается, поэтому нужно уменьшить левую границу. `left + 1L` не даёт сообщению переполниться.

## `parse()`

Возвращает `Integer`, а не `int`, чтобы `null` мог означать ошибку. `Integer.valueOf()` не принимает дроби, буквы, пустую строку и значения вне диапазона `int`. Пробелы по краям убирает `trim()`. Ноль и отрицательное число попыток допустимы.

## `showFieldError()` и `focus()`

Сначала вызывается `setText()`, потом `setStyle()`. **Порядок важен:** изменение текста запускает слушатель, который очищает стиль. При обратном порядке красный цвет сразу пропал бы.

Свойства `highlight` сохраняют красный цвет, когда сообщение выделено. `focus()` передаёт фокус и выделяет текст, чтобы его можно было сразу заменить.

## Почему настройки передаются через метод

`ui.applySettings(new GameSettings(...))` отдаёт только проверенные значения, все вместе. Главному окну не нужно читать поля окна настроек. Решение о перезапуске игры остаётся в [PrGuesserUI](PrGuesserUI.md).
