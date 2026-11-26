# PrGuesserUI

[← К оглавлению](README.md)

**Назначение:** строит главное окно и связывает действия пользователя с игрой, настройками, статистикой и логом.

## Исходник

Файл: `src/main/java/prguesser/PrGuesserUI.java`

```java
package prguesser;

import java.io.IOException;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/** Главное окно и управление ходом игры. */
public class PrGuesserUI {

    private static final String MAIN_TITLE = "Угадай число";
    private static final String NUMBER_PROMPT = "Введите целое число";

    private final Stage stage;
    private final Random random = new Random();
    private final GameLogger logger = new GameLogger();
    private final GameStats stats = new GameStats();
    private final PrGuesserOptions options;

    private final Label lblGame = new Label();
    private final Label lblRange = new Label();
    private final Label lblHint = new Label();
    private final Label lblAttempts = new Label();
    private final TextField tfNumber = new TextField();
    private final Button btnTry = new Button("Попробовать!");
    private final Label lblResult = new Label();
    private final Button btnNewGame = new Button("Новая игра");
    private final Button btnOptions = new Button("Настройки");
    private final CheckBox cbLog = new CheckBox("Записывать лог");
    private final Button btnClearLog = new Button("Очистить лог");
    private final Label lblStats = new Label();

    private GameSettings settings = new GameSettings(1, 100, 7);
    private Game game;
    private int gameCounter;
    private Scene scene;

    public PrGuesserUI(Stage stage) {
        this.stage = stage;
        options = new PrGuesserOptions(this);
    }

    public void makeUI() {
        options.makeUI();

        tfNumber.setMaxWidth(180);
        tfNumber.setAlignment(Pos.CENTER);
        btnTry.setDefaultButton(true); // нажатие Enter = "Попробовать!"
        cbLog.setTooltip(new Tooltip("Файл: " + logger.file()));
        lblGame.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        for (Label label : new Label[]{lblResult, lblStats, lblHint}) {
            label.setWrapText(true);
            label.setTextAlignment(TextAlignment.CENTER);
        }
        lblResult.setMinHeight(40);

        btnTry.setOnAction(this::handleTry);
        btnNewGame.setOnAction(this::handleNewGame);
        btnOptions.setOnAction(this::handleOpenOptions);
        cbLog.setOnAction(this::handleLogToggle);
        btnClearLog.setOnAction(this::handleClearLog);
        stage.setOnCloseRequest(this::handleClose);

        HBox gameButtons = new HBox(15, btnNewGame, btnOptions);
        gameButtons.setAlignment(Pos.CENTER);
        HBox logBox = new HBox(15, cbLog, btnClearLog);
        logBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(18, lblGame, lblRange, lblHint, lblAttempts,
                tfNumber, btnTry, lblResult, gameButtons, logBox, lblStats);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 30, 20, 30));

        scene = new Scene(root, 400, 600);
        stage.setScene(scene);
        stage.setTitle(MAIN_TITLE);
        startNewGame();
    }

    /** Единственное место, где создаётся новая игра. */
    private void startNewGame() {
        game = new Game(settings, ++gameCounter, random);
        logger.newGame(game);
        writeLogHeader();

        lblGame.setText("Игра №" + game.number());
        lblRange.setText("Загадано число от " + settings.leftBound()
                + " до " + settings.rightBound());
        lblHint.setText("При удачной стратегии хватит попыток: " + settings.optimalAttempts());
        lblResult.setText("Сделайте первую попытку");
        lblResult.setStyle("");
        lblStats.setText(stats.summary());
        updateAttemptsLabel();
        btnTry.setDisable(false);
        tfNumber.setText(NUMBER_PROMPT);
        focusNumberField();
    }

    /** Прерывает активную игру (если есть), начинает новую и сообщает об этом. */
    private void restartGame(String reason) {
        String report = "";
        if (game.isActive()) {
            game.interrupt();
            stats.register(game);
            log(game.resultText());
            report = game.resultText() + "\n\n";
        }
        startNewGame();
        showInfo("Новая игра", report + reason + "\nНачалась игра №" + game.number()
                + ": " + settings.description() + ".");
        focusNumberField();
    }

    private void handleTry(ActionEvent event) {
        if (!game.isActive()) {
            return;
        }
        String input = tfNumber.getText();
        int value;
        try {
            value = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            rejectInput(input, "Ошибка: введите целое число");
            return;
        }
        if (!game.isInRange(value)) {
            rejectInput(input, "Ошибка: число должно быть от "
                    + settings.leftBound() + " до " + settings.rightBound());
            return;
        }

        Game.Verdict verdict = game.makeAttempt(value);
        lblResult.setStyle("");
        lblResult.setText(verdict.text());
        updateAttemptsLabel();
        log("Ввод: \"" + input + "\"; вердикт: " + verdict.text()
                + "; осталось попыток: " + game.remainingText());

        if (!game.isActive()) {
            finishGame();
        }
        focusNumberField();
    }

    /** Некорректный ввод: попытка не расходуется. */
    private void rejectInput(String input, String error) {
        lblResult.setStyle("-fx-text-fill: red;");
        lblResult.setText(error);
        log("Ввод: \"" + input + "\"; вердикт: " + error
                + "; осталось попыток: " + game.remainingText());
        focusNumberField();
    }

    private void finishGame() {
        btnTry.setDisable(true);
        stats.register(game);
        lblStats.setText(stats.summary());
        lblResult.setText(game.resultText());
        log(game.resultText());
        showInfo("Результат игры", game.resultText());
    }

    private void handleNewGame(ActionEvent event) {
        log("Пользователь нажал кнопку \"Новая игра\"");
        restartGame("Вы выбрали новую игру.");
    }

    private void handleOpenOptions(ActionEvent event) {
        log("Пользователь открыл настройки");
        btnTry.setDefaultButton(false);
        options.show(stage, settings);
    }

    /** Вызывается окном настроек после успешной проверки данных. */
    void applySettings(GameSettings newSettings) {
        log("Пользователь сохранил настройки: " + newSettings.description());
        settings = newSettings;
        showMainScene();
        restartGame("Настройки сохранены.");
    }

    /** Вызывается окном настроек по кнопке "Отмена". */
    void cancelSettings() {
        log("Пользователь закрыл настройки без изменений");
        showMainScene();
        focusNumberField();
    }

    private void showMainScene() {
        stage.setScene(scene);
        stage.setTitle(MAIN_TITLE);
        stage.sizeToScene();
        btnTry.setDefaultButton(true);
    }

    private void handleLogToggle(ActionEvent event) {
        if (cbLog.isSelected()) {
            logger.setEnabled(true);
            log("Пользователь включил запись лога; осталось попыток: " + game.remainingText());
        } else {
            log("Пользователь отключил запись лога");
            logger.setEnabled(false);
        }
        focusNumberField();
    }

    private void handleClearLog(ActionEvent event) {
        try {
            logger.clear();
            showInfo("Лог-файл", "Лог-файл очищен.");
        } catch (IOException e) {
            showError("Не удалось очистить лог-файл:\n" + e.getMessage());
        }
        focusNumberField();
    }

    private void handleClose(WindowEvent event) {
        log("Пользователь закрыл приложение");
    }

    private void writeLogHeader() {
        try {
            logger.writeHeader();
        } catch (IOException e) {
            disableLogAfterError(e);
        }
    }

    private void log(String message) {
        try {
            logger.write(message);
        } catch (IOException e) {
            disableLogAfterError(e);
        }
    }

    private void disableLogAfterError(IOException e) {
        logger.setEnabled(false);
        cbLog.setSelected(false);
        showError("Ошибка записи в лог-файл, запись отключена:\n" + e.getMessage());
    }

    private void updateAttemptsLabel() {
        lblAttempts.setText(settings.isUnlimited()
                ? "Количество попыток не ограничено"
                : "Осталось попыток: " + game.remainingText());
    }

    void focusNumberField() {
        tfNumber.requestFocus();
        tfNumber.selectAll();
    }

    private void showInfo(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    private void showError(String message) {
        showAlert(Alert.AlertType.ERROR, "Ошибка", message);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        if (stage.isShowing()) {
            alert.initOwner(stage);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
```

## Что исправлено после лекционной версии

| Было | Стало и зачем |
|---|---|
| два метода `returnScene()` — ошибка компиляции | возврат выполняет `showMainScene()` |
| часть скобок обработчика настроек после `//` | отдельный метод `handleOpenOptions()` |
| `parseInt` без обработки ошибки | `try/catch` и сообщение пользователю |
| новая игра не обновляла надписи и поле | `startNewGame()` обновляет всё |
| надпись всегда «от 1 до 100» | берутся действующие границы |
| логика игры внутри UI | вынесена в [Game](Game.md) |

## Поля и конструктор

- Элементы интерфейса стали полями, потому что с ними работают разные методы.
- `final` запрещает заменить ссылку на другой объект. Свойства самого элемента, например текст, менять можно.
- `random`, `logger`, `stats` существуют всё время работы окна. `game` заменяется при каждой новой игре.
- `settings` изначально равен 1–100 с 7 попытками. В лекционной версии лимита не было; для этого укажите `0`.
- `gameCounter` нумерует игры в сеансе (пункт 15).

## `makeUI()`

- `setMaxWidth(180)` уменьшает поле ввода (пункт 5).
- `setDefaultButton(true)` связывает Enter с «Попробовать!».
- `Tooltip` показывает полный путь к лог-файлу.
- `setWrapText(true)` переносит длинные строки.
- `setMinHeight(40)` оставляет место под результат.
- `VBox` располагает элементы столбцом, `HBox` — кнопки и флажок в ряд.
- Размер сцены 400×600 (пункт 4), заголовок «Угадай число» (пункт −1).
- В конце вызывается `startNewGame()`.

## Обработчики (пункт 10)

`btnTry.setOnAction(this::handleTry)` — ссылка на метод. Она не вызывает его сразу: JavaFX вызовет метод при нажатии и передаст `ActionEvent`. `handleClose` получает `WindowEvent`.

## `startNewGame()`

Это единственное место, где создаётся `Game`. Метод:

1. увеличивает номер и создаёт игру;
2. сообщает логгеру о ней и пишет заголовок, если лог включён;
3. обновляет метки, статистику и остаток попыток;
4. включает кнопку;
5. возвращает подсказку в поле, выделяет её и передаёт фокус.

## `restartGame()`

Общий код для кнопки «Новая игра» и сохранения настроек. Если старая игра активна, метод прерывает её, учитывает в статистике и пишет результат в лог. Затем начинает новую и показывает **одно** окно с итогом прерванной игры и информацией о новой (пункты 8 и 11). Для уже завершённой игры итог повторно не показывается.

Порядок важен: результат старой игры записывается до заголовка новой.

## `handleTry()` и `rejectInput()`

1. Если игра закончена, ничего не происходит.
2. `trim()` убирает пробелы по краям, `Integer.parseInt()` разбирает число.
3. Буквы, дроби и значения вне `int` дают `NumberFormatException`.
4. Затем проверяется игровой диапазон.
5. Корректное число передаётся в `makeAttempt()`.
6. Обновляются метки и лог; при окончании вызывается `finishGame()`.

`rejectInput()` показывает красную ошибку и пишет её в лог. `makeAttempt()` не вызывается, поэтому счётчик не меняется (пункт 12). Следующий корректный ввод возвращает обычный цвет.

## `finishGame()`

Отключает кнопку (пункт 9), обновляет статистику, показывает результат в метке и диалоге, пишет его в лог (пункт 11). Отключённая кнопка не срабатывает и по Enter.

## Переходы между окнами (пункт 8)

| Метод | Что делает |
|---|---|
| `handleOpenOptions()` | логирует действие и открывает настройки; игра пока не прерывается |
| `applySettings()` | принимает `GameSettings`, возвращает главную сцену и начинает новую игру |
| `cancelSettings()` | возвращается без изменений (пункт 0) |
| `showMainScene()` | восстанавливает сцену, заголовок, размер и кнопку по умолчанию |

Пока открыты настройки, у `btnTry` отключается свойство кнопки по умолчанию. При возврате оно включается снова.

## Логирование (пункты 13 и 14)

- `handleLogToggle()` при включении сначала активирует логгер, потом пишет сообщение. При выключении порядок обратный, иначе сообщение об отключении не записалось бы.
- `handleClearLog()` очищает файл и сообщает об этом. Само нажатие не записывается, чтобы после очистки файл был пустым.
- `handleClose()` отмечает закрытие приложения крестиком окна.
- `writeLogHeader()` и `log()` перехватывают `IOException`.
- `disableLogAfterError()` отключает запись, снимает флажок и показывает ошибку, чтобы она не повторялась на каждом действии.

## Вспомогательные методы

- `updateAttemptsLabel()` выводит остаток или сообщение об отсутствии лимита (пункт 3).
- `focusNumberField()` вызывает `requestFocus()` и `selectAll()`: новый ввод заменяет старый текст (пункт 6).
- `showInfo()` и `showError()` используют общий `showAlert()`.
- `initOwner(stage)` привязывает диалог к главному окну.
- `showAndWait()` приостанавливает выполнение текущего метода до закрытия диалога, при этом интерфейс диалога работает. Поэтому после него можно вернуть фокус.
- `setMinHeight(USE_PREF_SIZE)` помогает показать длинный текст целиком.
