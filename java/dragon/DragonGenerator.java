import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Генератор драконов.
 * Видов драконов 14: 7 непустых комбинаций умений (суша/воздух/вода) x {огнедышащий, нет}.
 * Драконы создаются по кругу по видам, поэтому каждого вида примерно поровну,
 * затем список перемешивается.
 */
public class DragonGenerator {

    public record Params(int count, int minHeads, int maxHeads,
                         int minSpeed, int maxSpeed,
                         int minPower, int maxPower) {
        public Params {
            if (count < 0) throw new IllegalArgumentException("count < 0");
            if (minHeads < 1 || maxHeads < minHeads) throw new IllegalArgumentException("неверный диапазон голов");
            if (minSpeed < 0 || maxSpeed < minSpeed) throw new IllegalArgumentException("неверный диапазон скоростей");
            if (minPower < 1 || maxPower < minPower) throw new IllegalArgumentException("неверный диапазон мощности (min >= 1)");
        }
    }

    private static final int KINDS = 14;

    private final Random random;

    public DragonGenerator(long seed) {
        this.random = new Random(seed);
    }

    public List<Dragon> generate(Params p) {
        List<Dragon> dragons = IntStream.range(0, p.count())
                .mapToObj(i -> create(i, p))
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(dragons, random);
        return dragons;
    }

    private Dragon create(int index, Params p) {
        int kind = index % KINDS;
        int abilities = (kind % 7) + 1;        // 1..7
        boolean fire = kind >= 7;
        int heads = between(p.minHeads(), p.maxHeads());
        int[] power = fire
                ? random.ints(heads, p.minPower(), p.maxPower() + 1).toArray()
                : new int[heads];
        return new Dragon(String.format("Dragon-%03d", index + 1), abilities,
                between(p.minSpeed(), p.maxSpeed()),
                between(p.minSpeed(), p.maxSpeed()),
                between(p.minSpeed(), p.maxSpeed()),
                fire, power);
    }

    private int between(int lo, int hi) {
        return lo + random.nextInt(hi - lo + 1);
    }
}