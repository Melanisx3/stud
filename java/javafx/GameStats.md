# GameStats

[← К оглавлению](README.md)

**Назначение:** статистика текущего запуска приложения. Новый класс для пункта 15.

## Исходник

Файл: `src/main/java/prguesser/GameStats.java`

```java
package prguesser;

/** Статистика текущего сеанса. */
public class GameStats {

    private int played;
    private int won;
    private long attemptsInWonGames;

    /** Прерванная без единой попытки игра в статистику не входит. */
    public void register(Game game) {
        if (game.isActive() || game.attemptsMade() == 0) {
            return;
        }
        played++;
        if (game.status() == Game.Status.WON) {
            won++;
            attemptsInWonGames += game.attemptsMade();
        }
    }

    public String summary() {
        if (played == 0) {
            return "Статистика: сыгранных игр пока нет";
        }
        String text = String.format("Сыграно: %d, побед: %d (%.1f%%)",
                played, won, 100.0 * won / played);
        if (won > 0) {
            text += String.format("%nВ среднем попыток на победу: %.1f",
                    (double) attemptsInWonGames / won);
        }
        return text;
    }
}
```

## Поля

- `played`: учтённые игры.
- `won`: победы.
- `attemptsInWonGames`: сумма попыток **только в выигранных играх**.

## `register()`

Не учитывает активную игру и игру без корректных попыток: случайный перезапуск не должен портить процент побед. Проигранные игры и прерванные после хотя бы одной попытки входят в `played`. Некорректный ввод попыткой не считается.

Метод не запоминает, какие игры уже учтены. `PrGuesserUI` вызывает его один раз: либо из `finishGame()`, либо при прерывании активной игры.

## `summary()`

- Если учтённых игр нет, сообщает об этом и тем самым избегает деления на ноль.
- Процент побед равен `100.0 * won / played`. `100.0` включает дробное вычисление.
- При наличии побед показывает среднее число попыток на победу.
- В `String.format` код `%.1f` означает один знак после запятой, `%%` — знак процента, `%n` — перевод строки. Десятичный разделитель зависит от языковых настроек системы.

Статистика хранится только в памяти и после перезапуска обнуляется.
