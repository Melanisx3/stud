import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Запуск: java Main [ключ=значение ...]
 * Ключи (в скобках значения по умолчанию):
 *   count(70) minHeads(1) maxHeads(5) minSpeed(10) maxSpeed(100) minPower(10) maxPower(100)
 *   seed(42) speedThreshold(80) avgLo(30) avgHi(60) headPower(40) n(5) delimiter(", ") out(out)
 */
public class Main {

    private static Map<String, String> opts = new HashMap<>();

    private static int num(String key, int def) {
        return Integer.parseInt(opts.getOrDefault(key, String.valueOf(def)));
    }

    public static void main(String[] args) throws IOException {
        for (String a : args) {
            String[] kv = a.split("=", 2);
            if (kv.length == 2) opts.put(kv[0], kv[1]);
        }
        if (args.length == 0) {
            askInteractively();   // без аргументов - спрашиваем параметры у пользователя
        }
        Path dir = Path.of(opts.getOrDefault("out", "out"));
        Files.createDirectories(dir);

        // Задание 0
        var params = new DragonGenerator.Params(num("count", 70), num("minHeads", 1), num("maxHeads", 5),
                num("minSpeed", 10), num("maxSpeed", 100), num("minPower", 10), num("maxPower", 100));
        List<Dragon> dragons = new DragonGenerator(num("seed", 42)).generate(params);
        write(dir, 0, dragons.stream().map(Dragon::toString).toList());

        // Задание 1
        write(dir, 1, DragonTasks.task1(dragons).stream().map(Object::toString).toList());

        // Задание 2
        int speedThreshold = num("speedThreshold", 80);
        List<String> l2 = new ArrayList<>();
        l2.add("Порог скорости: " + speedThreshold);
        DragonTasks.task2(dragons, speedThreshold).forEach(d -> l2.add(d.toString()));
        write(dir, 2, l2);

        // Задание 3
        int lo = num("avgLo", 30), hi = num("avgHi", 60);
        List<Dragon> t3 = DragonTasks.task3List(dragons, lo, hi);
        List<String> l3 = new ArrayList<>();
        l3.add("Диапазон средней мощности: [" + lo + "; " + hi + "]");
        t3.forEach(d -> l3.add(String.format("%s avg=%.2f", d, d.avgPower())));
        l3.add("Суммарная огневая мощь: " + DragonTasks.task3Sum(t3));
        write(dir, 3, l3);

        //  Задание 4
        List<String> l4 = new ArrayList<>();
        print(DragonTasks.task4(dragons), l4, 0, "летает", "голов");
        write(dir, 4, l4);

        // Задание 5
        List<String> l5 = new ArrayList<>();
        print(DragonTasks.task5(dragons), l5, 0, "голов", "плавает");
        write(dir, 5, l5);

        //  Задание 6
        int headPower = num("headPower", 40);
        List<String> l6 = new ArrayList<>();
        l6.add("Минимальная мощность каждой головы: " + headPower);
        DragonTasks.task6(dragons, headPower).forEach(d -> l6.add(d.toString()));
        write(dir, 6, l6);

        // Задание 7
        List<String> l7 = new ArrayList<>();
        print(DragonTasks.task7(dragons), l7, 0, "макс. мощность головы");
        write(dir, 7, l7);

        // Задание 8
        List<String> l8 = new ArrayList<>();
        print(DragonTasks.task8(dragons), l8, 0, "преимущественно");
        write(dir, 8, l8);

        // Задание 9
        int n = num("n", 5);
        var r9 = DragonTasks.task9(dragons, n);
        List<String> l9 = new ArrayList<>();
        l9.add("Максимальная суммарная мощность: " + r9.maxTotal() + ", число диапазонов: " + n);
        print(r9.byRange(), l9, 0, "диапазон");
        l9.add("Диапазоны с максимальным числом драконов (" + r9.busiestCount() + "): " + r9.busiestRanges());
        write(dir, 9, l9);

        //Задание 10
        List<String> l10 = new ArrayList<>();
        print(DragonTasks.task10(dragons), l10, 0, "макс. мощность головы", "умения");
        write(dir, 10, l10);

        // Задание 11
        String delimiter = opts.getOrDefault("delimiter", ", ");
        List<Dragon> t11 = DragonTasks.task11(dragons);
        write(dir, 11, List.of(
                "Максимальная разность мощности голов: " + DragonTasks.maxSpread(dragons),
                DragonTasks.joinNames(t11, delimiter)));

        System.out.println("Готово. Файлы task0.txt ... task11.txt записаны в " + dir.toAbsolutePath());
    }

    /** Интерактивный ввод параметров. Пустая строка (Enter) = значение по умолчанию. */
    private static void askInteractively() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите параметры. Enter - оставить значение по умолчанию (в скобках).");

        System.out.println("\nГенератор драконов (задание 0) ---");
        while (true) {
            ask(sc, "count", "Количество драконов", 70, 0);
            ask(sc, "minHeads", "Минимальное количество голов", 1, 1);
            ask(sc, "maxHeads", "Максимальное количество голов", 5, 1);
            ask(sc, "minSpeed", "Минимальная скорость в каждой среде", 10, 0);
            ask(sc, "maxSpeed", "Максимальная скорость в каждой среде", 100, 0);
            ask(sc, "minPower", "Минимальная огневая мощность головы (>= 1)", 10, 1);
            ask(sc, "maxPower", "Максимальная огневая мощность головы", 100, 1);
            try {
                new DragonGenerator.Params(num("count", 70), num("minHeads", 1), num("maxHeads", 5),
                        num("minSpeed", 10), num("maxSpeed", 100), num("minPower", 10), num("maxPower", 100));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Введите значения заново.\n");
            }
        }
        ask(sc, "seed", "Seed генератора (одинаковый seed - одинаковые драконы)", 42, Integer.MIN_VALUE);

        System.out.println("\n--- Параметры заданий ---");
        ask(sc, "speedThreshold", "Задание 2: порог максимальной скорости", 80, 0);
        while (true) {
            ask(sc, "avgLo", "Задание 3: нижняя граница средней мощности", 30, 0);
            ask(sc, "avgHi", "Задание 3: верхняя граница средней мощности", 60, 0);
            if (num("avgLo", 30) <= num("avgHi", 60)) break;
            System.out.println("Ошибка: нижняя граница больше верхней. Введите заново.\n");
        }
        ask(sc, "headPower", "Задание 6: минимальная мощность каждой головы", 40, 0);
        ask(sc, "n", "Задание 9: количество диапазонов n", 5, 1);

        System.out.print("Задание 11: разделитель имён [\", \"]: ");
        String line = sc.hasNextLine() ? sc.nextLine() : "";
        if (!line.isEmpty()) opts.put("delimiter", line);
        System.out.println();
    }

    /** Запрашивает целое число >= min; при ошибке ввода спрашивает повторно. */
    private static void ask(Scanner sc, String key, String label, int def, int min) {
        while (true) {
            System.out.print(label + " [" + def + "]: ");
            if (!sc.hasNextLine()) {            // конец ввода - берём значение по умолчанию
                opts.put(key, String.valueOf(def));
                return;
            }
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                opts.put(key, String.valueOf(def));
                return;
            }
            try {
                int value = Integer.parseInt(line);
                if (value < min) {
                    System.out.println("  Значение должно быть не меньше " + min + ".");
                    continue;
                }
                opts.put(key, String.valueOf(value));
                return;
            } catch (NumberFormatException e) {
                System.out.println("  Нужно целое число.");
            }
        }
    }

    private static void write(Path dir, int task, List<String> lines) throws IOException {
        Files.write(dir.resolve("task" + task + ".txt"), lines);
    }

    /** Универсальный вывод вложенных Map/Collection с подписями уровней ключей. */
    private static void print(Object value, List<String> out, int level, String... keyNames) {
        String indent = "  ".repeat(level);
        if (value instanceof Map<?, ?> m) {
            String label = level < keyNames.length ? keyNames[level] : "key";
            m.forEach((k, v) -> {
                String key = k instanceof Boolean b ? (b ? "да" : "нет") : String.valueOf(k);
                if (v instanceof Map<?, ?> || v instanceof Collection<?>) {
                    int size = v instanceof Collection<?> c ? c.size() : ((Map<?, ?>) v).size();
                    out.add(indent + label + " = " + key + " (" + size + "):");
                    print(v, out, level + 1, keyNames);
                } else {
                    out.add(indent + label + " = " + key + ": " + v);
                }
            });
        } else if (value instanceof Collection<?> c) {
            c.forEach(e -> out.add(indent + e));
        }
    }
}