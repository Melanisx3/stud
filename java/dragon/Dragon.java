import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Дракон.
 * Умения хранятся битовой маской (LAND | FLY | SWIM).
 * Скорость в среде, где дракон двигаться не умеет, равна 0.
 * Огневая мощь хранится массивом длиной "количество голов";
 * у не огнедышащего дракона все элементы равны 0 (это упрощает потоковую обработку голов).
 */
public class Dragon {
    public static final int LAND = 1;
    public static final int FLY = 2;
    public static final int SWIM = 4;

    private final String name;
    private final int abilities;
    private final int landSpeed;
    private final int flySpeed;
    private final int swimSpeed;
    private final boolean fireBreathing;
    private final int[] headPower;

    public Dragon(String name, int abilities, int landSpeed, int flySpeed, int swimSpeed,
                  boolean fireBreathing, int[] headPower) {
        if (abilities < 1 || abilities > 7) {
            throw new IllegalArgumentException("Дракон должен уметь двигаться хотя бы в одной среде");
        }
        if (headPower.length < 1) {
            throw new IllegalArgumentException("У дракона должна быть хотя бы одна голова");
        }
        this.name = name;
        this.abilities = abilities;
        this.landSpeed = (abilities & LAND) != 0 ? landSpeed : 0;
        this.flySpeed = (abilities & FLY) != 0 ? flySpeed : 0;
        this.swimSpeed = (abilities & SWIM) != 0 ? swimSpeed : 0;
        this.fireBreathing = fireBreathing;
        this.headPower = fireBreathing ? headPower.clone() : new int[headPower.length];
    }

    public String name() { return name; }
    public int abilities() { return abilities; }
    public boolean canLand() { return (abilities & LAND) != 0; }
    public boolean canFly() { return (abilities & FLY) != 0; }
    public boolean canSwim() { return (abilities & SWIM) != 0; }
    public int landSpeed() { return landSpeed; }
    public int flySpeed() { return flySpeed; }
    public int swimSpeed() { return swimSpeed; }
    public boolean isFireBreathing() { return fireBreathing; }
    public int heads() { return headPower.length; }

    /** Поток значений огневой мощи голов. */
    public IntStream headPowers() { return Arrays.stream(headPower); }

    /** Максимальная скорость среди сред, где дракон способен двигаться. */
    public int maxSpeed() {
        return IntStream.of(landSpeed, flySpeed, swimSpeed).max().getAsInt();
    }

    public int totalPower() { return headPowers().sum(); }

    public double avgPower() { return headPowers().average().orElse(0); }

    public int maxHeadPower() { return headPowers().max().orElse(0); }

    public int minHeadPower() { return headPowers().min().orElse(0); }

    /** Разность между максимальной и минимальной мощностью голов. */
    public int powerSpread() { return maxHeadPower() - minHeadPower(); }

    /** Текстовое описание набора умений, например "LAND+FLY". */
    public static String describe(int mask) {
        return Stream.of(
                        (mask & LAND) != 0 ? "LAND" : null,
                        (mask & FLY) != 0 ? "FLY" : null,
                        (mask & SWIM) != 0 ? "SWIM" : null)
                .filter(s -> s != null)
                .reduce((a, b) -> a + "+" + b)
                .orElse("NONE");
    }

    @Override
    public String toString() {
        return String.format("%s [%s] speed(land/fly/swim)=%d/%d/%d heads=%d fire=%s power=%s",
                name, describe(abilities), landSpeed, flySpeed, swimSpeed, heads(),
                fireBreathing ? "yes" : "no", Arrays.toString(headPower));
    }
}
