# DragonTasks — выполнение заданий над драконами

[Исходный файл DragonTasks.java](../DragonTasks.java)

## 1. Назначение класса

DragonTasks содержит статические методы для выполнения одиннадцати заданий над коллекцией драконов.

Класс не хранит состояние конкретного дракона и не создаёт генератор. Он получает готовый список List<Dragon>, обрабатывает его с помощью Stream API и возвращает результаты.

Большинство методов используют потоковую обработку:

- stream() — получение потока элементов коллекции;
- filter() — фильтрация по условию;
- map() / mapToLong() — преобразование элементов;
- collect() — сбор результата;
- groupingBy() — группировка;
- partitioningBy() — разделение на true и false;
- sum(), max(), min() — числовые операции.

## 2. Конструктор класса

~~~java
private DragonTasks() {}
~~~

Конструктор объявлен private, поэтому создать объект DragonTasks из другого класса нельзя.

Все методы класса static, следовательно, вызываются непосредственно через имя класса:

~~~java
DragonTasks.task1(dragons);
~~~

Такой класс используется как набор утилитных методов.

## 3. Вложенная запись DragonPower

~~~java
public record DragonPower(Dragon dragon, int total, double avg)
~~~

Хранит результаты вычисления мощности одного дракона.

| Компонент | Тип | Назначение |
|---|---|---|
| dragon | Dragon | Исходный объект дракона |
| total | int | Суммарная мощность всех голов |
| avg | double | Средняя мощность одной головы |

Record автоматически создаёт конструктор, методы доступа dragon(), total(), avg(), а также стандартные методы записи.

### Переопределённый toString()

~~~java
@Override
public String toString() {
    return String.format("%s -> total=%d avg=%.2f", dragon.name(), total, avg);
}
~~~

Формирует строку с именем дракона, суммарной и средней мощностью.

%.2f выводит вещественное число с двумя знаками после запятой.

## 4. Задание 1 — вычисление мощности каждого дракона

### Метод

~~~java
public static List<DragonPower> task1(List<Dragon> dragons)
~~~

**Входные параметры:**

- dragons — List<Dragon>, список исходных драконов.

**Возвращаемый тип:**

- List<DragonPower> — список объектов с вычисленными характеристиками.

### Алгоритм

1. dragons.stream() создаёт поток объектов Dragon.
2. map(d -> new DragonPower(...)) преобразует каждого дракона в новый объект DragonPower.
3. Для текущего дракона вызываются totalPower() и avgPower().
4. collect(toList()) собирает все созданные объекты в список.

~~~java
return dragons.stream()
        .map(d -> new DragonPower(d, d.totalPower(), d.avgPower()))
        .collect(toList());
~~~

В результате исходный список не заменяется и сами объекты Dragon не изменяются. Создаются отдельные объекты DragonPower.

## 5. Задание 2 — драконы с максимальной скоростью выше порога

### Метод

~~~java
public static List<Dragon> task2(List<Dragon> dragons, int speedThreshold)
~~~

**Входные параметры:**

- dragons — List<Dragon>, исходный список;
- speedThreshold — int, минимальный порог максимальной скорости.

**Возвращаемый тип:** List<Dragon>.

### Алгоритм

1. Создаётся поток драконов.
2. filter() проверяет условие d.maxSpeed() > speedThreshold.
3. maxSpeed() возвращает максимальную скорость дракона среди трёх сред.
4. collect(toList()) собирает только подходящих драконов.

~~~java
return dragons.stream()
        .filter(d -> d.maxSpeed() > speedThreshold)
        .collect(toList());
~~~

Важно: используется строгое сравнение >. Если максимальная скорость равна порогу, дракон не попадёт в результат.

## 6. Задание 3 — драконы со средней мощностью в заданном диапазоне

Выполняется двумя методами: task3List() и task3Sum().

### Метод task3List()

~~~java
public static List<Dragon> task3List(List<Dragon> dragons, double lo, double hi)
~~~

**Параметры:**

- dragons — List<Dragon>, исходный список;
- lo — double, нижняя граница средней мощности;
- hi — double, верхняя граница средней мощности.

**Возвращает:** List<Dragon>.

Алгоритм:

1. Получаем поток драконов.
2. Для каждого дракона вычисляем avgPower().
3. Проверяем два условия: средняя мощность должна быть не меньше lo и не больше hi.
4. Оператор && требует одновременного выполнения обоих условий.
5. Подходящие драконы собираются в список.

~~~java
return dragons.stream()
        .filter(d -> d.avgPower() >= lo && d.avgPower() <= hi)
        .collect(toList());
~~~

Обе границы включаются в диапазон.

### Метод task3Sum()

~~~java
public static long task3Sum(List<Dragon> selected)
~~~

**Параметр:**

- selected — List<Dragon>, список драконов, отобранных первым методом.

**Возвращает:** long — общую сумму огневой мощности всех выбранных драконов.

Алгоритм:

1. selected.stream() создаёт поток.
2. mapToLong(Dragon::totalPower) преобразует каждого дракона в числовое значение типа long.
3. sum() складывает все значения.

~~~java
return selected.stream().mapToLong(Dragon::totalPower).sum();
~~~

Для суммирования используется long, чтобы иметь больший диапазон значений, чем int.

## 7. Задание 4 — разделение драконов на летающих и нелетающих с группировкой по количеству голов

### Метод

~~~java
public static Map<Boolean, Map<Integer, List<Dragon>>> task4(List<Dragon> dragons)
~~~

**Параметр:** dragons — List<Dragon>.

**Возвращаемый тип:**

~~~java
Map<Boolean, Map<Integer, List<Dragon>>>
~~~

Структура результата:

- внешний Map: ключ Boolean;
- внутренний Map: ключ Integer;
- значение: List<Dragon>.

Внешний ключ показывает, умеет ли дракон летать:

- true — летает;
- false — не летает.

Внутренний ключ — количество голов.

Пример структуры:

~~~text
true
    1 -> [Dragon-001, Dragon-004]
    3 -> [Dragon-007]

false
    2 -> [Dragon-002]
    4 -> [Dragon-005, Dragon-006]
~~~

### Алгоритм

~~~java
return dragons.stream().collect(
        partitioningBy(Dragon::canFly,
                groupingBy(Dragon::heads, TreeMap::new, toList())));
~~~

1. partitioningBy(Dragon::canFly) разделяет всех драконов на две группы по результату canFly().
2. Для каждой из двух групп применяется groupingBy(Dragon::heads, ...).
3. TreeMap::new указывает, что внутренние карты должны быть TreeMap.
4. toList() собирает драконов с одинаковым количеством голов в списки.

### Используемые типы

- Boolean — результат логического условия;
- Integer — количество голов;
- List<Dragon> — список драконов;
- TreeMap — карта, которая хранит ключи в отсортированном порядке.

partitioningBy всегда формирует два логических раздела: true и false.

## 8. Задание 5 — группировка по количеству голов и разделение на плавающих и неплавающих

### Метод

~~~java
public static Map<Integer, Map<Boolean, List<Dragon>>> task5(List<Dragon> dragons)
~~~

**Параметр:** dragons — List<Dragon>.

**Возвращаемый тип:**

~~~java
Map<Integer, Map<Boolean, List<Dragon>>>
~~~

Структура отличается от задания 4 порядком вложенности:

- внешний ключ Integer — количество голов;
- внутренний ключ Boolean — способность плавать;
- значение List<Dragon> — соответствующие драконы.

Пример:

~~~text
1 голова
    true  -> [Dragon-001]
    false -> [Dragon-002, Dragon-003]

3 головы
    true  -> [Dragon-004, Dragon-005]
    false -> [Dragon-006]
~~~

### Алгоритм

~~~java
return dragons.stream().collect(
        groupingBy(Dragon::heads, TreeMap::new,
                partitioningBy(Dragon::canSwim)));
~~~

1. groupingBy(Dragon::heads) группирует драконов по количеству голов.
2. TreeMap::new создаёт внешнюю карту с сортировкой количества голов.
3. Для каждой группы partitioningBy(Dragon::canSwim) разделяет драконов на плавающих и неплавающих.

Главное отличие от задания 4: сначала группировка по количеству голов, затем логическое разделение.

## 9. Задание 6 — драконы, которые либо летают, либо плавают, и у которых каждая голова достаточно сильная

### Метод

~~~java
public static List<Dragon> task6(List<Dragon> dragons, int minHeadPower)
~~~

**Параметры:**

- dragons — List<Dragon>;
- minHeadPower — int, минимально допустимая мощность каждой головы.

**Возвращает:** List<Dragon>.

### Алгоритм

~~~java
return dragons.stream()
        .filter(d -> d.canFly() ^ d.canSwim())
        .filter(d -> d.headPowers().allMatch(p -> p >= minHeadPower))
        .collect(toList());
~~~

Первый filter():

- canFly() проверяет способность летать;
- canSwim() проверяет способность плавать;
- оператор ^ — логическое исключающее ИЛИ (XOR).

XOR возвращает true, когда операнды различаются:

| Летает | Плавает | Результат XOR |
|---|---|---|
| true | true | false |
| true | false | true |
| false | true | true |
| false | false | false |

Таким образом, подходит дракон, который умеет летать или плавать, но не одновременно и то и другое.

Второй filter():

1. headPowers() возвращает IntStream мощностей голов.
2. allMatch() проверяет каждый элемент потока.
3. Условие p >= minHeadPower должно выполняться для каждой головы.
4. Если хотя бы одна голова имеет мощность ниже порога, дракон исключается.

Важно: allMatch() проверяет все элементы, а не только максимальную мощность.

Если поток пустой, allMatch() вернёт true. Однако корректно созданный Dragon всегда имеет хотя бы одну голову.

## 10. Задание 7 — группировка драконов по максимальной мощности одной головы

### Метод

~~~java
public static Map<Integer, List<Dragon>> task7(List<Dragon> dragons)
~~~

**Параметр:** dragons — List<Dragon>.

**Возвращает:** Map<Integer, List<Dragon>>.

Ключ Integer — максимальная мощность одной головы. Значение List<Dragon> — драконы с такой максимальной мощностью.

### Алгоритм

~~~java
return dragons.stream()
        .filter(d -> d.canFly() || d.canSwim())
        .collect(groupingBy(Dragon::maxHeadPower, TreeMap::new, toList()));
~~~

1. filter() оставляет драконов, которые умеют летать или плавать. Здесь используется логическое ИЛИ ||, поэтому достаточно одного умения.
2. groupingBy(Dragon::maxHeadPower) объединяет драконов с одинаковой максимальной мощностью головы.
3. TreeMap::new создаёт отсортированную карту.
4. toList() собирает драконов в списки.

В отличие от задания 6, здесь не требуется, чтобы каждая голова достигала определённого порога.

## 11. Задание 8 — группировка не огнедышащих драконов по преимущественной среде передвижения

### Перечисление Env

~~~java
public enum Env { LAND, FLY, SWIM }
~~~

Перечисление задаёт три возможных ключа группировки:

- LAND — суша;
- FLY — воздух;
- SWIM — вода.

Enum — специальный тип данных Java, который содержит фиксированный набор именованных значений.

### Метод predominantEnv(Dragon d)

~~~java
private static Env predominantEnv(Dragon d)
~~~

**Параметр:** d — объект Dragon.

**Возвращает:** Env — преимущественную среду передвижения.

Алгоритм:

1. Получает максимальную скорость дракона методом maxSpeed().
2. Сравнивает landSpeed() с максимальной скоростью.
3. Если скорости равны, возвращает Env.LAND.
4. Иначе сравнивает flySpeed().
5. Если скорости равны, возвращает Env.FLY.
6. Если ни одно из предыдущих условий не выполнилось, возвращает Env.SWIM.

При равенстве скоростей приоритет установлен в порядке LAND, затем FLY, затем SWIM.

### Метод task8()

~~~java
public static Map<Env, List<Dragon>> task8(List<Dragon> dragons)
~~~

**Параметр:** dragons — List<Dragon>.

**Возвращает:** Map<Env, List<Dragon>>.

Ключ — элемент перечисления Env. Значение — список драконов.

### Алгоритм

~~~java
return dragons.stream()
        .filter(d -> d.maxHeadPower() == 0)
        .collect(groupingBy(DragonTasks::predominantEnv,
                () -> new EnumMap<>(Env.class), toList()));
~~~

1. Создаётся поток драконов.
2. filter() оставляет драконов, у которых максимальная мощность головы равна нулю. В текущей модели это не огнедышащие драконы.
3. groupingBy() определяет группу через метод predominantEnv().
4. Supplier карты создаёт новую EnumMap с ключами типа Env.
5. toList() собирает драконов каждой группы в список.

### Почему EnumMap?

EnumMap — реализация интерфейса Map, специально предназначенная для ключей-перечислений.

Конструктор:

~~~java
new EnumMap<>(Env.class)
~~~

Env.class — объект Class, представляющий тип перечисления Env. Он сообщает EnumMap, какие значения enum могут использоваться как ключи.

Лямбда:

~~~java
() -> new EnumMap<>(Env.class)
~~~

является Supplier — функцией без входных параметров, которая создаёт и возвращает новую карту по запросу.

В этом варианте groupingBy получает не готовую карту, а способ создания карты.

EnumMap хранит ключи в порядке объявления элементов перечисления. Внутри используется массивоподобное представление, поэтому такая карта компактна и эффективна для enum-ключей.

Важно: groupingBy создаёт группы только для реально встретившихся ключей. Если драконов для какой-либо среды нет, соответствующий ключ может отсутствовать в результате.

Также predominantEnv() сравнивает все три значения скоростей, не проверяя отдельно canLand(), canFly() и canSwim(). Если все три скорости равны нулю, метод вернёт LAND из-за установленного приоритета. Это следует учитывать при интерпретации результата.

## 12. Задание 9 — распределение драконов по диапазонам суммарной мощности

Задание использует три вспомогательных метода и одну запись результата.

### Запись Task9Result

~~~java
public record Task9Result(
        int maxTotal,
        Map<String, List<Dragon>> byRange,
        List<String> busiestRanges,
        int busiestCount
) {}
~~~

Хранит итоговую информацию:

| Поле | Тип | Назначение |
|---|---|---|
| maxTotal | int | Максимальная суммарная мощность одного дракона |
| byRange | Map<String, List<Dragon>> | Все диапазоны и списки драконов в них |
| busiestRanges | List<String> | Названия диапазонов с максимальным количеством драконов |
| busiestCount | int | Максимальное количество драконов в одном диапазоне |

### Метод rangeIndex(int power, int max, int n)

~~~java
private static int rangeIndex(int power, int max, int n)
~~~

Параметры:

- power — int, суммарная мощность конкретного дракона;
- max — int, максимальная суммарная мощность среди всех драконов;
- n — int, количество диапазонов.

Возвращает номер диапазона типа int.

Алгоритм:

~~~java
return max == 0 ? 0 : (int) Math.min(n - 1, (long) power * n / max);
~~~

1. Если max равен нулю, возвращается индекс 0. Это предотвращает деление на ноль.
2. Иначе вычисляется power * n / max.
3. Перед умножением power преобразуется в long, чтобы снизить риск переполнения int при умножении.
4. Math.min(n - 1, ...) ограничивает индекс последним допустимым диапазоном.
5. Результат преобразуется к int.

Индексы диапазонов: от 0 до n - 1.

### Метод rangeLabel(int i, int max, int n)

~~~java
private static String rangeLabel(int i, int max, int n)
~~~

Параметры:

- i — int, индекс диапазона;
- max — int, максимальная суммарная мощность;
- n — int, число диапазонов.

Возвращает строковую подпись диапазона.

Границы рассчитываются так:

- нижняя: i * max / n;
- верхняя: (i + 1) * max / n.

Вычисления выполняются с double, чтобы сохранить дробные значения.

String.format() формирует подпись с двумя знаками после запятой.

Все диапазоны, кроме последнего, имеют вид [нижняя; верхняя). Последний диапазон закрывается квадратной скобкой и включает верхнюю границу.

### Метод task9(List<Dragon> dragons, int n)

~~~java
public static Task9Result task9(List<Dragon> dragons, int n)
~~~

Параметры:

- dragons — List<Dragon>, исходные драконы;
- n — int, количество диапазонов.

Возвращает Task9Result.

#### Шаг 1. Поиск максимальной суммарной мощности

~~~java
int max = dragons.stream()
        .mapToInt(Dragon::totalPower)
        .max()
        .orElse(0);
~~~

- mapToInt() преобразует каждого дракона в его суммарную мощность.
- max() ищет максимальное значение.
- orElse(0) возвращает ноль, если список пуст.

#### Шаг 2. Первичная группировка

~~~java
Map<Integer, List<Dragon>> grouped = dragons.stream()
        .collect(groupingBy(
                d -> rangeIndex(d.totalPower(), max, n),
                TreeMap::new,
                toList()
        ));
~~~

Создаётся карта, где ключ Integer — индекс диапазона, а значение List<Dragon> — драконы в этом диапазоне.

TreeMap обеспечивает сортировку числовых индексов.

#### Шаг 3. Формирование всех диапазонов

~~~java
Map<String, List<Dragon>> byRange = IntStream.range(0, n).boxed()
        .collect(Collectors.toMap(
                i -> rangeLabel(i, max, n),
                i -> grouped.getOrDefault(i, List.of()),
                (a, b) -> a,
                LinkedHashMap::new
        ));
~~~

- IntStream.range(0, n) создаёт индексы диапазонов.
- boxed() преобразует значения int в объекты Integer, чтобы использовать обычный Stream<Integer>.
- Collectors.toMap() создаёт карту.
- Первый аргумент определяет строковый ключ — название диапазона.
- Второй аргумент возвращает список драконов по индексу.
- getOrDefault(i, List.of()) возвращает пустой неизменяемый список, если в диапазоне нет драконов.
- (a, b) -> a — функция объединения значений при совпадении ключей. В нормальной работе подписи диапазонов уникальны.
- LinkedHashMap::new создаёт карту, сохраняющую порядок добавления диапазонов.

Таким образом, в byRange присутствуют все n диапазонов, даже пустые.

#### Шаг 4. Поиск максимального количества драконов

~~~java
int maxCount = byRange.values().stream()
        .mapToInt(List::size)
        .max()
        .orElse(0);
~~~

- values() получает коллекцию списков.
- stream() создаёт поток списков.
- mapToInt(List::size) преобразует каждый список в его размер.
- max() находит наибольший размер.
- orElse(0) задаёт значение по умолчанию.

#### Шаг 5. Поиск самых заполненных диапазонов

~~~java
List<String> busiest = byRange.entrySet().stream()
        .filter(e -> e.getValue().size() == maxCount)
        .map(Map.Entry::getKey)
        .collect(toList());
~~~

- entrySet() получает набор пар ключ-значение.
- filter() оставляет диапазоны, размер списка которых равен maxCount.
- getValue() возвращает список драконов.
- getKey() возвращает название диапазона.
- collect(toList()) собирает названия в List<String>.

Если несколько диапазонов имеют одинаковое максимальное количество драконов, они все попадут в busiest.

#### Шаг 6. Возврат результата

~~~java
return new Task9Result(max, byRange, busiest, maxCount);
~~~

Создаётся запись Task9Result со всеми вычисленными данными.

**Ограничение текущей реализации:** метод предполагает n >= 1. Сам task9() не проверяет это условие. В интерактивном вводе Main для n установлена минимальная граница 1.

## 13. Задание 10 — группировка по максимальной мощности головы и набору умений с подсчётом драконов

### Метод

~~~java
public static Map<Integer, Map<String, Long>> task10(List<Dragon> dragons)
~~~

**Параметр:** dragons — List<Dragon>.

**Возвращает:** Map<Integer, Map<String, Long>>.

Структура:

- внешний ключ Integer — максимальная мощность одной головы;
- внутренний ключ String — текстовое описание умений;
- значение Long — количество драконов с такими характеристиками.

### Алгоритм

~~~java
return dragons.stream().collect(
        groupingBy(Dragon::maxHeadPower, TreeMap::new,
                groupingBy(
                        d -> Dragon.describe(d.abilities()),
                        TreeMap::new,
                        counting()
                )
        )
);
~~~

1. Внешний groupingBy() группирует драконов по maxHeadPower().
2. TreeMap::new обеспечивает сортировку внешних ключей.
3. Для каждой внешней группы выполняется ещё один groupingBy().
4. Dragon.describe(d.abilities()) преобразует битовую маску в строку, например LAND+FLY.
5. Внутренняя TreeMap сортирует строковые ключи.
6. counting() считает количество элементов в каждой внутренней группе.

В отличие от предыдущих заданий, результатом группы является не список драконов, а число типа Long.

## 14. Задание 11 — поиск максимальной разницы мощностей голов и имён соответствующих драконов

Задание использует три метода: maxSpread(), task11() и joinNames().

### Метод maxSpread()

~~~java
public static int maxSpread(List<Dragon> dragons)
~~~

Параметр: dragons — List<Dragon>.

Возвращает int — максимальную разницу между мощностями голов среди всех драконов.

Алгоритм:

1. Создаётся поток драконов.
2. mapToInt(Dragon::powerSpread) получает разницу мощностей голов каждого дракона.
3. max() находит максимальную разницу.
4. orElse(0) возвращает ноль для пустого потока.

### Метод task11()

~~~java
public static List<Dragon> task11(List<Dragon> dragons)
~~~

Параметр: dragons — List<Dragon>.

Возвращает List<Dragon> — всех драконов, у которых разница мощностей голов равна максимальной.

Алгоритм:

1. Вызывает maxSpread(dragons) и сохраняет результат в переменную max типа int.
2. Создаёт поток драконов.
3. filter() оставляет драконов, у которых powerSpread() == max.
4. collect(toList()) собирает их в список.

Если несколько драконов имеют одинаковую максимальную разницу, возвращаются все они.

### Метод joinNames()

~~~java
public static String joinNames(List<Dragon> dragons, String delimiter)
~~~

Параметры:

- dragons — List<Dragon>, список подходящих драконов;
- delimiter — String, разделитель между именами.

Возвращает String — одну строку с именами драконов.

Алгоритм:

1. dragons.stream() создаёт поток.
2. map(Dragon::name) преобразует каждый объект в его имя типа String.
3. Collectors.joining(delimiter) объединяет строки с указанным разделителем.

Пример:

~~~java
String names = DragonTasks.joinNames(dragons, ", ");
~~~

Если имена равны Dragon-001 и Dragon-004, результат будет:

~~~text
Dragon-001, Dragon-004
~~~

## 15. Сводная таблица заданий

| Задание | Входные параметры | Возвращаемый тип | Основные операции |
|---|---|---|---|
| 1 | List<Dragon> | List<DragonPower> | map, collect |
| 2 | List<Dragon>, int | List<Dragon> | filter |
| 3, отбор | List<Dragon>, double, double | List<Dragon> | filter |
| 3, сумма | List<Dragon> | long | mapToLong, sum |
| 4 | List<Dragon> | Map<Boolean, Map<Integer, List<Dragon>>> | partitioningBy, groupingBy |
| 5 | List<Dragon> | Map<Integer, Map<Boolean, List<Dragon>>> | groupingBy, partitioningBy |
| 6 | List<Dragon>, int | List<Dragon> | filter, XOR, allMatch |
| 7 | List<Dragon> | Map<Integer, List<Dragon>> | filter, groupingBy, TreeMap |
| 8 | List<Dragon> | Map<Env, List<Dragon>> | filter, groupingBy, EnumMap |
| 9 | List<Dragon>, int | Task9Result | groupingBy, TreeMap, LinkedHashMap |
| 10 | List<Dragon> | Map<Integer, Map<String, Long>> | вложенный groupingBy, counting |
| 11, максимум | List<Dragon> | int | mapToInt, max |
| 11, отбор | List<Dragon> | List<Dragon> | filter |
| 11, имена | List<Dragon>, String | String | map, joining |

## 16. Основные структуры данных

### List

Список объектов. В проекте используется для хранения драконов и результатов обработки.

### Map

Коллекция пар ключ-значение. Используется для группировки объектов по одному или нескольким признакам.

### TreeMap

Реализация Map, которая хранит ключи в отсортированном порядке. В проекте используется для числовых и строковых ключей.

### LinkedHashMap

Сохраняет порядок добавления элементов. В задании 9 нужен для последовательного вывода диапазонов от первого к последнему.

### EnumMap

Специализированная карта для ключей-перечислений. В задании 8 ключом является Env.

### Stream и IntStream

Stream<T> обрабатывает объекты, например Dragon. IntStream обрабатывает примитивные целые числа int.

## 17. Общие замечания

- Методы класса статические и не изменяют исходные объекты Dragon.
- Большинство методов возвращает новые списки или карты.
- Потоковая обработка выполняется при вызове терминальной операции, например collect(), sum(), max().
- groupingBy() группирует элементы по ключу, который возвращает переданная функция.
- partitioningBy() делит элементы на две группы по логическому условию.
- TreeMap и LinkedHashMap используются не случайно: они задают нужный порядок ключей при выводе.
- В task8 используется EnumMap, потому что ключи принадлежат одному перечислению Env.
