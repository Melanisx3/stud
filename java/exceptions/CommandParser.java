/**
 * Парсер. Ждёт строку вида "направление пробел число".
 * Если строка верная, двигает исполнителя. Иначе бросает исключение
 * и ничего не исправляет: исправлением занимается читатель.
 *
 * Порядок проверок: пробелы, число, направление. Поэтому, например,
 * "Snn-5" даёт ошибки по очереди: нет пробела, отрицательное число,
 * несколько направлений.
 */
class CommandParser {
    private static final String DIRECTIONS = "NSEWnsew";

    private final int k;
    private final Robot robot;

    CommandParser(int k, Robot robot) {
        this.k = k;
        this.robot = robot;
    }

    void parse(String line) throws CommandException {
        int space = line.indexOf(' ');

        if (space < 0) {
            // Пробела нет. Если есть что-то до числа и само число, то это пропущенный пробел.
            int i = 0;
            while (i < line.length() && !isNumberStart(line.charAt(i))) {
                i++;
            }
            if (i == 0 || i == line.length()) {
                throw new UnrecognizedCommandException(line);
            }
            throw new MissingSpaceException(line);
        }
        if (space == 0) {
            throw new UnrecognizedCommandException(line);
        }

        String symbols = line.substring(0, space);
        String number = line.substring(space + 1);

        if (number.startsWith(" ")) {
            throw new ExtraSpacesException(line);
        }
        if (number.contains(" ")) {
            throw new UnrecognizedCommandException(line); // лишние слова
        }

        int distance = checkNumber(line, number);
        char direction = checkSymbols(line, symbols);
        robot.move(direction, distance);
    }

    private int checkNumber(String line, String number) throws CommandException {
        if (!number.matches("-?\\d+")) {
            throw new InvalidNumberException(line, number);
        }
        int value;
        try {
            value = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new NumberOutOfRangeException(line, k); // не помещается в int, значит больше K
        }
        if (Math.abs((long) value) > k) {
            throw new NumberOutOfRangeException(line, k);
        }
        if (value < 0) {
            throw new NegativeNumberException(line);
        }
        if (value == 0) {
            throw new ZeroNumberException(line);
        }
        return value;
    }

    private char checkSymbols(String line, String symbols) throws CommandException {
        for (char c : symbols.toCharArray()) {
            if (DIRECTIONS.indexOf(c) < 0) {
                if (Character.isLetter(c)) {
                    throw new UnsupportedLetterException(line, c);
                }
                throw new NotLetterSymbolException(line, c);
            }
        }
        if (symbols.length() > 1) {
            throw new MultipleSymbolsException(line);
        }
        char c = symbols.charAt(0);
        if (Character.isLowerCase(c)) {
            throw new LowercaseSymbolException(line);
        }
        return c;
    }

    private static boolean isNumberStart(char c) {
        return (c >= '0' && c <= '9') || c == '-';
    }
}
