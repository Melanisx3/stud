# Game

[← К оглавлению](README.md)

**Назначение:** правила и состояние **одной** игры. Новый класс без зависимости от JavaFX.

## Исходник

Файл: `src/main/java/prguesser/Game.java`

```java
package prguesser;

import java.util.Random;

/** Логика одной игры. Не зависит от JavaFX. */
public class Game {

    public enum Verdict {
        LESS("Загаданное число меньше"),
        GREATER("Загаданное число больше"),
        GUESSED("Вы угадали число!");

        private final String text;

        Verdict(String text) {
            this.text = text;
        }

        public String text() {
            return text;
        }
    }

    public enum Status { ACTIVE, WON, LOST, INTERRUPTED }

    private final GameSettings settings;
    private final int number;
    private final int secretNumber;
    private int attemptsMade;
    private Status status = Status.ACTIVE;

    public Game(GameSettings settings, int number, Random random) {
        this.settings = settings;
        this.number = number;
        // Верхняя граница nextLong не включается. long защищает от переполнения.
        secretNumber = (int) random.nextLong(settings.leftBound(), settings.rightBound() + 1L);
    }

    /** Выполняет одну корректную попытку. */
    public Verdict makeAttempt(int value) {
        if (!isActive()) {
            throw new IllegalStateException("Игра уже завершена");
        }
        if (!isInRange(value)) {
            throw new IllegalArgumentException("Число вне диапазона");
        }
        attemptsMade++;
        if (value == secretNumber) {
            status = Status.WON;
            return Verdict.GUESSED;
        }
        if (!settings.isUnlimited() && attemptsMade >= settings.maxAttempts()) {
            status = Status.LOST;
        }
        return value > secretNumber ? Verdict.LESS : Verdict.GREATER;
    }

    public void interrupt() {
        if (isActive()) {
            status = Status.INTERRUPTED;
        }
    }

    public boolean isInRange(int value) {
        return value >= settings.leftBound() && value <= settings.rightBound();
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public String remainingText() {
        return settings.isUnlimited()
                ? "не ограничено"
                : String.valueOf(settings.maxAttempts() - attemptsMade);
    }

    public String resultText() {
        String title = switch (status) {
            case ACTIVE -> "продолжается";
            case WON -> "победа, число угадано";
            case LOST -> "поражение, число не угадано, попытки закончились";
            case INTERRUPTED -> "прервана, число не угадано";
        };
        String text = "Игра №" + number + ": " + title
                + ". Сделано попыток: " + attemptsMade + ".";
        return isActive() ? text : text + " Было загадано: " + secretNumber + ".";
    }

    public GameSettings settings() {
        return settings;
    }

    public int number() {
        return number;
    }

    public int attemptsMade() {
        return attemptsMade;
    }

    public Status status() {
        return status;
    }
}
```

## Зачем отдельный класс

В лекционном коде `PrGuesserUI` одновременно рисовал интерфейс, хранил секретное число и сравнивал его с вводом. Теперь новая игра — это новый объект `Game`. Счётчик, статус и секретное число создаются заново, поэтому старое состояние случайно не остаётся.

## Перечисления

`enum` задаёт фиксированный набор значений.

- `Verdict`: результат попытки и его текст. `LESS` означает, что **загаданное** число меньше введённого.
- `Status`: `ACTIVE` (идёт), `WON` (победа), `LOST` (попытки кончились), `INTERRUPTED` (прервана).

Один `Status` заменяет `boolean statusGame` и различает причины окончания игры (пункты 9 и 11).

## Поля

`settings`, `number` и `secretNumber` объявлены `final`: во время игры они не меняются. Изменяются только `attemptsMade` и `status`.

## Генерация числа

`nextLong(origin, bound)` включает `origin`, но не включает `bound`, поэтому передаётся `rightBound + 1L`. Суффикс `L` не даёт сумме переполниться при `Integer.MAX_VALUE`. Результат лежит в диапазоне `int`, так что обратное приведение безопасно. Число больше не печатается в консоль.

## `makeAttempt()`

1. Запрещает ход в завершённой игре.
2. Отклоняет число вне диапазона.
3. Только после проверок увеличивает счётчик (пункт 12).
4. При совпадении ставит `WON`.
5. Иначе проверяет лимит и при его исчерпании ставит `LOST`.
6. Возвращает подсказку.

Победа проверяется раньше лимита, поэтому угаданное на последней попытке число даёт победу.

После проигрышной последней попытки метод всё равно возвращает `LESS` или `GREATER`. О поражении интерфейс узнаёт через `isActive()` и `resultText()`.

## Остальные методы

| Метод | Назначение |
|---|---|
| `interrupt()` | прерывает только активную игру и не портит уже полученный результат |
| `isInRange()` | проверяет диапазон, включая границы |
| `isActive()` | сообщает, можно ли делать ход |
| `remainingText()` | остаток попыток или «не ограничено» (пункт 3) |
| `resultText()` | номер игры, результат и число попыток (пункт 11); после окончания раскрывает секретное число |
| `settings()`, `number()`, `attemptsMade()`, `status()` | дают прочитать состояние, не изменяя поля напрямую |

`switch` в `resultText()` выбирает текст по статусу и сразу возвращает значение.
