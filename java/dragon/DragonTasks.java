import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

/**
 * Выбор структур данных:
 *List когда нужен просто набор результатов: порядок сохраняется, дубликатов нет
 *    (каждый дракон - отдельный объект), доступ последовательный;
 *TreeMap для группировок по числовым ключам (число голов, мощность, номер диапазона):
 *        ключи в файле выводятся в возрастающем порядке;
 *Map<Boolean,...> для деления на два подмножества;
 *EnumMap - для группировки по enum (среда обитания): компактно и упорядоченно.
 */
public final class DragonTasks {
    private DragonTasks() {}

    // ---------- Задание 1 ----------
    public record DragonPower(Dragon dragon, int total, double avg) {
        @Override
        public String toString() {
            return String.format("%s -> total=%d avg=%.2f", dragon.name(), total, avg);
        }
    }

    public static List<DragonPower> task1(List<Dragon> dragons) {
        return dragons.stream()
                .map(d -> new DragonPower(d, d.totalPower(), d.avgPower()))
                .collect(toList());
    }

    // ---------- Задание 2 ----------
    public static List<Dragon> task2(List<Dragon> dragons, int speedThreshold) {
        return dragons.stream()
                .filter(d -> d.maxSpeed() > speedThreshold)
                .collect(toList());
    }

    // ---------- Задание 3 ----------
    public static List<Dragon> task3List(List<Dragon> dragons, double lo, double hi) {
        return dragons.stream()
                .filter(d -> d.avgPower() >= lo && d.avgPower() <= hi)
                .collect(toList());
    }

    public static long task3Sum(List<Dragon> selected) {
        return selected.stream().mapToLong(Dragon::totalPower).sum();
    }

    // ---------- Задание 4 ----------
    public static Map<Boolean, Map<Integer, List<Dragon>>> task4(List<Dragon> dragons) {
        return dragons.stream().collect(
                partitioningBy(Dragon::canFly,
                        groupingBy(Dragon::heads, TreeMap::new, toList())));
    }

    // ---------- Задание 5 ----------
    public static Map<Integer, Map<Boolean, List<Dragon>>> task5(List<Dragon> dragons) {
        return dragons.stream().collect(
                groupingBy(Dragon::heads, TreeMap::new,
                        partitioningBy(Dragon::canSwim)));
    }

    // ---------- Задание 6 ----------
    /** Либо летает, либо плавает (XOR), и КАЖДАЯ голова имеет мощность >= порога. */
    public static List<Dragon> task6(List<Dragon> dragons, int minHeadPower) {
        return dragons.stream()
                .filter(d -> d.canFly() ^ d.canSwim())
                .filter(d -> d.headPowers().allMatch(p -> p >= minHeadPower))
                .collect(toList());
    }

    // ---------- Задание 7 ----------
    public static Map<Integer, List<Dragon>> task7(List<Dragon> dragons) {
        return dragons.stream()
                .filter(d -> d.canFly() || d.canSwim())
                .collect(groupingBy(Dragon::maxHeadPower, TreeMap::new, toList()));
    }

    // ---------- Задание 8 ----------
    public enum Env { LAND, FLY, SWIM }

    /** При равенстве скоростей приоритет: LAND, затем FLY, затем SWIM. */
    private static Env predominantEnv(Dragon d) {
        int best = d.maxSpeed();
        if (d.landSpeed() == best) return Env.LAND;
        if (d.flySpeed() == best) return Env.FLY;
        return Env.SWIM;
    }

    public static Map<Env, List<Dragon>> task8(List<Dragon> dragons) {
        return dragons.stream()
                .filter(d -> d.maxHeadPower() == 0)
                .collect(groupingBy(DragonTasks::predominantEnv,
                        () -> new EnumMap<>(Env.class), toList()));
    }

    // Задание 9
    public record Task9Result(int maxTotal, Map<String, List<Dragon>> byRange, List<String> busiestRanges,
                              int busiestCount) {}

    private static int rangeIndex(int power, int max, int n) {
        return max == 0 ? 0 : (int) Math.min(n - 1, (long) power * n / max);
    }

    private static String rangeLabel(int i, int max, int n) {
        return String.format("[%.2f; %.2f%s", i * (double) max / n, (i + 1) * (double) max / n,
                i == n - 1 ? "]" : ")");
    }

    public static Task9Result task9(List<Dragon> dragons, int n) {
        int max = dragons.stream().mapToInt(Dragon::totalPower).max().orElse(0);
        Map<Integer, List<Dragon>> grouped = dragons.stream()
                .collect(groupingBy(d -> rangeIndex(d.totalPower(), max, n), TreeMap::new, toList()));

        // Все n диапазонов (в том числе пустые) в порядке возрастания
        Map<String, List<Dragon>> byRange = IntStream.range(0, n).boxed()
                .collect(Collectors.toMap(
                        i -> rangeLabel(i, max, n),
                        i -> grouped.getOrDefault(i, List.of()),
                        (a, b) -> a, LinkedHashMap::new));

        int maxCount = byRange.values().stream().mapToInt(List::size).max().orElse(0);
        List<String> busiest = byRange.entrySet().stream()
                .filter(e -> e.getValue().size() == maxCount)
                .map(Map.Entry::getKey)
                .collect(toList());
        return new Task9Result(max, byRange, busiest, maxCount);
    }

    // Задание 10
    public static Map<Integer, Map<String, Long>> task10(List<Dragon> dragons) {
        return dragons.stream().collect(
                groupingBy(Dragon::maxHeadPower, TreeMap::new,
                        groupingBy(d -> Dragon.describe(d.abilities()), TreeMap::new, counting())));
    }

    //  Задание 11
    public static int maxSpread(List<Dragon> dragons) {
        return dragons.stream().mapToInt(Dragon::powerSpread).max().orElse(0);
    }

    public static List<Dragon> task11(List<Dragon> dragons) {
        int max = maxSpread(dragons);
        return dragons.stream()
                .filter(d -> d.powerSpread() == max)
                .collect(toList());
    }

    public static String joinNames(List<Dragon> dragons, String delimiter) {
        return dragons.stream().map(Dragon::name).collect(Collectors.joining(delimiter));
    }
}
