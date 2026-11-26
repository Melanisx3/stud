# PrGuesser: игра «Угадай число»

Учебное приложение на чистом JavaFX (без FXML). Это доработка лекционного проекта по пунктам от −1 до 15.

## Страницы по файлам

| Страница | Происхождение | Роль |
|---|---|---|
| [PrGuesser](PrGuesser.md) | из лекции, изменён | запуск приложения |
| [PrGuesserUI](PrGuesserUI.md) | из лекции, переработан | главное окно и управление игрой |
| [PrGuesserOptions](PrGuesserOptions.md) | из лекции, переработан | окно настроек и проверка ввода |
| [GameSettings](GameSettings.md) | новый | настройки одной игры |
| [Game](Game.md) | новый | правила и состояние игры |
| [GameStats](GameStats.md) | новый | статистика сеанса |
| [GameLogger](GameLogger.md) | новый | лог-файл |
| [module-info](module-info.md) | новый файл проекта | описание Java-модуля |
| [pom.xml](pom.md) | новый файл проекта | сборка и зависимости |

Строго говоря, Java-модуль в проекте один: он описан в `module-info.java`. Остальные `.java`-файлы содержат классы и `record`.

Удобный порядок чтения: `PrGuesser` → `GameSettings` → `Game` → `PrGuesserUI` → `PrGuesserOptions` → `GameStats` → `GameLogger`.

## Как связаны классы

```text
PrGuesser ── создаёт ──> PrGuesserUI
                            ├── PrGuesserOptions ── передаёт GameSettings обратно
                            ├── Game ── использует GameSettings
                            ├── GameStats ── получает завершённые Game
                            └── GameLogger ── пишет LogGuess.txt
```

`Game` и `GameSettings` не используют JavaFX: правила игры отделены от интерфейса.

## Путь одной попытки

1. Пользователь нажимает «Попробовать!» или Enter.
2. `PrGuesserUI.handleTry()` разбирает строку и проверяет диапазон.
3. При ошибке вызывается `rejectInput()`, попытка не расходуется.
4. Иначе `Game.makeAttempt()` возвращает вердикт и при необходимости меняет статус.
5. Интерфейс обновляет метки и передаёт строку в `GameLogger`.
6. Если игра закончена, `finishGame()` обновляет статистику и показывает результат.

## Путь настроек

1. `PrGuesserOptions.show()` заполняет поля действующими значениями.
2. `handleSave()` проверяет ввод.
3. Создаётся объект `GameSettings`.
4. `PrGuesserUI.applySettings()` прерывает активную игру и начинает новую.

## Где выполнены пункты задания

| № | Требование | Где смотреть |
|---|---|---|
| −1 | заголовки окон | [UI](PrGuesserUI.md), [Options](PrGuesserOptions.md) |
| 0 | кнопка «Отмена» | [Options](PrGuesserOptions.md) |
| 1 | поле количества попыток | [Options](PrGuesserOptions.md) |
| 2 | проверка настроек | [Options](PrGuesserOptions.md) |
| 3 | остаток попыток | [UI](PrGuesserUI.md), [Game](Game.md) |
| 4 | фиксированные размеры | [PrGuesser](PrGuesser.md), [UI](PrGuesserUI.md), [Options](PrGuesserOptions.md) |
| 5 | уменьшенные поля | [UI](PrGuesserUI.md), [Options](PrGuesserOptions.md) |
| 6 | фокус на поле числа | [UI](PrGuesserUI.md) |
| 7 | фокус на левой границе | [Options](PrGuesserOptions.md) |
| 8 | передача настроек | [GameSettings](GameSettings.md), [Options](PrGuesserOptions.md), [UI](PrGuesserUI.md) |
| 9 | конец игры по попыткам | [Game](Game.md), [UI](PrGuesserUI.md) |
| 10 | обработчики в методах | [UI](PrGuesserUI.md), [Options](PrGuesserOptions.md) |
| 11 | сообщение результата | [Game](Game.md), [UI](PrGuesserUI.md) |
| 12 | некорректный ввод | [UI](PrGuesserUI.md), [Game](Game.md) |
| 13 | лог и очистка | [GameLogger](GameLogger.md), [UI](PrGuesserUI.md) |
| 14 | переключение лога во время игры | [GameLogger](GameLogger.md), [UI](PrGuesserUI.md) |
| 15 | дополнительные возможности | [GameStats](GameStats.md), [GameSettings](GameSettings.md), [Game](Game.md) |

## Запуск

Откройте корень проекта в IntelliJ IDEA как Maven-проект, выберите JDK 25 и запустите `Plugins → javafx → javafx:run`.
