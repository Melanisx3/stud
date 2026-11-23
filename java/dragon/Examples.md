# Примеры использования Java-классов и Stream API

Этот файл содержит самостоятельные примеры, которые можно изучать и запускать отдельно от основной программы с драконами.

Каждый пример показывает конкретную конструкцию Java: enum, класс, конструктор, коллекции, EnumMap, groupingBy(), Stream API и вывод результатов.

---

# Пример 1. Студенты и распределение по курсам с помощью EnumMap

## Задача

Есть несколько студентов. Каждый студент записан на один из трёх курсов: Java, Python или C++.

Нужно распределить студентов по курсам и вывести результат на экран.

## Полный код StudentExample.java

~~~java
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Перечисление доступных курсов.
enum Course {
    JAVA, PYTHON, CPP
}

// Класс одного студента.
class Student {
    String name;
    Course course;

    // Конструктор принимает имя и курс студента.
    Student(String name, Course course) {
        this.name = name;
        this.course = course;
    }

    // Преобразование объекта в удобную для печати строку.
    @Override
    public String toString() {
        return name + " (" + course + ")";
    }
}

public class StudentExample {
    public static void main(String[] args) {

        // Создаём список студентов.
        List<Student> students = List.of(
                new Student("Alex", Course.JAVA),
                new Student("Maria", Course.PYTHON),
                new Student("Ivan", Course.JAVA),
                new Student("Anna", Course.CPP),
                new Student("Olga", Course.PYTHON)
        );

        // Распределяем студентов по курсам.
        Map<Course, List<Student>> groups = students.stream()
                .collect(Collectors.groupingBy(
                        s -> s.course,
                        () -> new EnumMap<>(Course.class),
                        Collectors.toList()
                ));

        // Выводим результат.
        System.out.println("Распределение студентов по курсам:");

        groups.forEach((course, group) -> {
            System.out.println(course + ":");

            group.forEach(student ->
                    System.out.println("  " + student)
            );
        });
    }
}
~~~

## Разбор кода

### 1. Перечисление Course

~~~java
enum Course {
    JAVA, PYTHON, CPP
}
~~~

Создаёт отдельный тип Course с тремя возможными значениями.

Переменная типа Course не может хранить произвольную строку. Она может ссылаться только на одно из объявленных значений.

Примеры:

~~~java
Course first = Course.JAVA;
Course second = Course.PYTHON;
~~~

### 2. Класс Student

~~~java
class Student {
    String name;
    Course course;
}
~~~

Описывает одного студента.

- name имеет тип String;
- course имеет тип Course.

### 3. Конструктор Student

~~~java
Student(String name, Course course) {
    this.name = name;
    this.course = course;
}
~~~

При создании объекта передаются два значения.

this.name означает поле текущего объекта. name справа от знака = — параметр конструктора.

Пример:

~~~java
Student student = new Student("Alex", Course.JAVA);
~~~

### 4. Создание списка

~~~java
List<Student> students = List.of(...);
~~~

List<Student> — список объектов Student.

List.of() создаёт список из переданных элементов. Полученный список неизменяемый: нельзя добавлять или удалять элементы через этот список.

### 5. Создание групп

~~~java
Map<Course, List<Student>> groups = students.stream()
        .collect(Collectors.groupingBy(
                s -> s.course,
                () -> new EnumMap<>(Course.class),
                Collectors.toList()
        ));
~~~

Тип результата:

- Map — общая структура пар ключ-значение;
- Course — тип ключа;
- List<Student> — значение, то есть список студентов каждой группы.

Разбор аргументов groupingBy():

**Первый аргумент — функция определения ключа**

~~~java
s -> s.course
~~~

Для каждого студента берётся его курс. Именно по этому значению определяется группа.

**Второй аргумент — поставщик карты**

~~~java
() -> new EnumMap<>(Course.class)
~~~

Это лямбда без параметров. Она создаёт новую EnumMap.

Course.class — объект Class, который представляет перечисление Course. Он сообщает EnumMap, что ключами будут значения Course.

**Третий аргумент — способ сбора элементов группы**

~~~java
Collectors.toList()
~~~

Все студенты с одинаковым курсом собираются в список.

### 6. Вывод результата

~~~java
groups.forEach((course, group) -> {
    System.out.println(course + ":");
    group.forEach(student -> System.out.println("  " + student));
});
~~~

Внешний forEach() перебирает пары карты:

- course — ключ;
- group — список студентов.

Внутренний forEach() перебирает студентов конкретной группы.

## Пример результата

~~~text
Распределение студентов по курсам:
JAVA:
  Alex (JAVA)
  Ivan (JAVA)
PYTHON:
  Maria (PYTHON)
  Olga (PYTHON)
CPP:
  Anna (CPP)
~~~

Порядок групп определяется порядком значений в enum: JAVA, PYTHON, CPP.

Порядок студентов внутри каждой группы соответствует порядку их появления в исходном потоке.

---

# Пример 2. Подсчёт количества объектов каждой категории

## Задача

Есть список покупок. Нужно посчитать, сколько товаров относится к каждой категории.

## Полный код CategoryExample.java

~~~java
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

enum Category {
    FOOD, CLOTHES, ELECTRONICS
}

public class CategoryExample {
    public static void main(String[] args) {

        List<Category> products = List.of(
                Category.FOOD,
                Category.CLOTHES,
                Category.FOOD,
                Category.ELECTRONICS,
                Category.FOOD,
                Category.CLOTHES
        );

        Map<Category, Long> counts = products.stream()
                .collect(Collectors.groupingBy(
                        category -> category,
                        () -> new EnumMap<>(Category.class),
                        Collectors.counting()
                ));

        System.out.println("Количество товаров:");

        counts.forEach((category, count) ->
                System.out.println(category + ": " + count)
        );
    }
}
~~~

## Разбор

В этом примере groupingBy() работает почти так же, как в примере со студентами.

Отличие — третий аргумент:

~~~java
Collectors.counting()
~~~

Вместо сбора объектов в список он подсчитывает количество элементов в каждой группе.

Поэтому тип результата:

~~~java
Map<Category, Long>
~~~

Здесь Long используется потому, что Collectors.counting() возвращает значение типа Long.

## Результат

~~~text
Количество товаров:
FOOD: 3
CLOTHES: 2
ELECTRONICS: 1
~~~

Обрати внимание: в результат попадают только категории, которые встретились в исходном списке.

Если нужна карта со всеми категориями, включая категории с нулевым количеством, их можно добавить отдельно.

---

# Пример 3. Создание карты через лямбду и ссылку на конструктор

## Задача

Посмотреть, чем отличаются лямбда, Supplier и ссылка на конструктор.

## Полный код SupplierExample.java

~~~java
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

enum Environment {
    LAND, FLY, SWIM
}

public class SupplierExample {
    public static void main(String[] args) {

        // Вариант 1: лямбда с созданием ArrayList.
        Supplier<List<String>> first =
                () -> new ArrayList<>();

        // Вариант 2: ссылка на конструктор ArrayList.
        Supplier<List<String>> second =
                ArrayList::new;

        // Каждый вызов get() создаёт новый список.
        List<String> list1 = first.get();
        List<String> list2 = second.get();

        list1.add("Hello");
        list2.add("World");

        System.out.println(list1);
        System.out.println(list2);

        // EnumMap требует Class перечисления.
        Supplier<EnumMap<Environment, Integer>> third =
                () -> new EnumMap<>(Environment.class);

        EnumMap<Environment, Integer> map1 = third.get();

        // Ссылка на конструктор с входным параметром Class.
        Function<Class<Environment>,
                EnumMap<Environment, Integer>> fourth =
                EnumMap::new;

        EnumMap<Environment, Integer> map2 =
                fourth.apply(Environment.class);

        map1.put(Environment.LAND, 10);
        map2.put(Environment.FLY, 20);

        System.out.println(map1);
        System.out.println(map2);
    }
}
~~~

## Разбор

### Supplier<T>

Supplier — функциональный интерфейс из java.util.function.

Его основная операция:

~~~java
T get();
~~~

Supplier не принимает аргументы и возвращает объект типа T.

Поэтому подходит для:

~~~java
() -> new ArrayList<>()
~~~

и:

~~~java
() -> new EnumMap<>(Environment.class)
~~~

Вызов get() выполняет создание нового объекта.

### Function<T, R>

Function — функциональный интерфейс, который принимает объект типа T и возвращает объект типа R.

Его основная операция:

~~~java
R apply(T value);
~~~

В примере:

~~~java
Function<Class<Environment>,
        EnumMap<Environment, Integer>> fourth = EnumMap::new;
~~~

Функция принимает Class<Environment>, а возвращает EnumMap<Environment, Integer>.

EnumMap::new — ссылка на конструктор EnumMap, которому требуется передать класс перечисления.

### Почему нельзя написать Supplier с EnumMap::new?

У EnumMap нет конструктора без параметров.

Ей необходимо сообщить тип перечисления:

~~~java
new EnumMap<>(Environment.class)
~~~

Поэтому ссылка EnumMap::new не подходит для Supplier без входных параметров. Она подходит для Function, которая передаёт Class конструктору.

## Результат

~~~text
[Hello]
[World]
{LAND=10}
{FLY=20}
~~~

---

# Пример 4. Создание драконов и печать результатов

## Задача

Использовать реальные классы Dragon и DragonGenerator из проекта, создать несколько драконов, выполнить одно из заданий и вывести результат в консоль.

Этот пример предполагает, что файлы Dragon.java, DragonGenerator.java и DragonTasks.java находятся в одном пакете (как в текущей структуре проекта — без объявления package).

## Полный код ExampleMain.java

~~~java
import java.util.List;
import java.util.Map;

public class ExampleMain {
    public static void main(String[] args) {

        // 1. Задаём параметры генерации.
        DragonGenerator.Params params =
                new DragonGenerator.Params(
                        20,  // количество драконов
                        1, 5, // минимум и максимум голов
                        10, 100, // диапазон скоростей
                        10, 50 // диапазон мощности голов
                );

        // 2. Создаём генератор с фиксированным seed.
        DragonGenerator generator = new DragonGenerator(42);

        // 3. Генерируем список драконов.
        List<Dragon> dragons = generator.generate(params);

        // 4. Выводим исходный список.
        System.out.println("=== ВСЕ ДРАКОНЫ ===");

        dragons.forEach(System.out::println);

        // 5. Выполняем задание 2.
        int speedThreshold = 80;

        List<Dragon> fastDragons =
                DragonTasks.task2(dragons, speedThreshold);

        System.out.println("\n=== БЫСТРЫЕ ДРАКОНЫ ===");
        System.out.println("Порог скорости: " + speedThreshold);

        fastDragons.forEach(System.out::println);

        // 6. Выполняем задание 8.
        Map<DragonTasks.Env, List<Dragon>> groups =
                DragonTasks.task8(dragons);

        System.out.println("\n=== НЕ ОГНЕДЫШАЩИЕ ДРАКОНЫ ===");

        groups.forEach((env, group) -> {
            System.out.println("\n" + env + ":");

            group.forEach(dragon ->
                    System.out.println("  " + dragon.name()
                            + ", скорость = " + dragon.maxSpeed())
            );
        });

        // 7. Выводим количество созданных объектов.
        System.out.println("\nВсего создано драконов: "
                + dragons.size());

        System.out.println("Быстрых драконов: "
                + fastDragons.size());
    }
}
~~~

## Порядок выполнения

1. Создаётся Params с настройками генерации.
2. Создаётся DragonGenerator с seed = 42.
3. Метод generate() возвращает List<Dragon>.
4. forEach(System.out::println) печатает каждого дракона.
5. task2() возвращает драконов, чья максимальная скорость строго выше 80.
6. task8() группирует не огнедышащих драконов по преимущественной среде.
7. groups.forEach() перебирает группы.
8. Для каждой группы выводится название среды и список драконов.

## Использованные конструкции

| Конструкция | Назначение |
|---|---|
| new DragonGenerator.Params(...) | Создание объекта параметров |
| new DragonGenerator(42) | Создание генератора |
| generate(params) | Генерация списка |
| List<Dragon> | Список драконов |
| DragonTasks.task2(...) | Выполнение задания 2 |
| Map<Env, List<Dragon>> | Группировка по среде |
| forEach() | Перебор элементов |
| System.out.println() | Вывод в консоль |
| System.out::println | Ссылка на метод вывода |

## Важное замечание

Фиксированный seed делает генерацию воспроизводимой при одинаковых параметрах и одинаковом порядке операций. При этом конкретные результаты заданий зависят от сгенерированных характеристик драконов.

---

# Пример 5. Простая группировка без EnumMap

Для сравнения рассмотрим группировку студентов с обычной HashMap.

~~~java
Map<Course, List<Student>> groups = students.stream()
        .collect(Collectors.groupingBy(
                student -> student.course
        ));
~~~

Здесь не передаётся Supplier карты и не указывается третий аргумент.

Collectors.groupingBy() самостоятельно выбирает стандартную реализацию Map — обычно HashMap.

Сравнение:

| Вариант | Карта |
|---|---|
| groupingBy(Student::course) | Стандартная HashMap |
| groupingBy(Student::course, HashMap::new, toList()) | Явно HashMap |
| groupingBy(Student::course, () -> new EnumMap<>(Course.class), toList()) | EnumMap |

Если ключи — значения одного перечисления, EnumMap обычно является более специализированным и подходящим вариантом.

---

# Итог

В примерах использованы следующие важные понятия Java:

- enum — перечисление фиксированного набора значений;
- class — описание типа объектов;
- объект Class и выражение .class — представление типа во время выполнения;
- constructor — создание нового объекта;
- record — компактная форма класса для хранения данных;
- List — список объектов;
- Map — пары ключ-значение;
- EnumMap — карта с enum-ключами;
- Supplier — поставщик нового объекта;
- Function — функция с входным и выходным значением;
- лямбда-выражение — запись реализации функционального интерфейса;
- ссылка на конструктор ::new — использование конструктора как реализации функционального интерфейса;
- Stream API — обработка коллекций;
- groupingBy() — группировка объектов;
- counting() — подсчёт элементов;
- forEach() — перебор элементов;
- System.out.println() — печать данных в консоль.
