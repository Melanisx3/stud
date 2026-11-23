/** Исполнитель на бесконечном поле. */
class Robot {
    private long x;
    private long y;

    Robot(long x, long y) {
        this.x = x;
        this.y = y;
    }

    void move(char direction, int distance) {
        try {
            switch (direction) {
                case 'N': y = Math.addExact(y, distance); break;
                case 'S': y = Math.subtractExact(y, distance); break;
                case 'E': x = Math.addExact(x, distance); break;
                case 'W': x = Math.subtractExact(x, distance); break;
                default: throw new IllegalArgumentException("неизвестное направление " + direction);
            }
        } catch (ArithmeticException e) {
            throw new CoordinateOverflowException("переполнение координат при команде " + direction + " " + distance);
        }
    }

    String position() {
        return "(" + x + ", " + y + ")";
    }
}
