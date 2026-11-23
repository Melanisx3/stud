import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Locale;

/**
 * Читатель. Берёт строки из файла, отдаёт их парсеру через стек
 * и пишет отчёт. Если парсер бросил исправимое исключение, соответствующий
 * обработчик исправляет в строке только то, что вызвало ошибку,
 * и кладёт результат обратно на вершину стека.
 */
class CommandReader {
    private static final String NL = System.lineSeparator();

    private final CommandParser parser;
    private final Robot robot;
    private final Writer report;
    private final Deque<String> stack = new ArrayDeque<>();

    CommandReader(CommandParser parser, Robot robot, Writer report) {
        this.parser = parser;
        this.robot = robot;
        this.report = report;
    }

    /** Читает команды до конца файла или до строки X / Q. lineNo - сколько строк уже прочитано. */
    void readAll(BufferedReader in, int lineNo) throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            lineNo++;
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.equals("X") || line.equals("Q")) {
                break;
            }
            log("Строка " + lineNo + ": \"" + line + "\"");
            stack.push(line);
            processStack();
        }
    }

    private void processStack() throws IOException {
        while (!stack.isEmpty()) {
            String command = stack.pop();
            try {
                parser.parse(command);
                log("    выполнено: " + command + ", позиция " + robot.position());
            } catch (MissingSpaceException e) {
                fixMissingSpace(command);
            } catch (ExtraSpacesException e) {
                fixExtraSpaces(command);
            } catch (NegativeNumberException e) {
                fixNegative(command);
            } catch (ZeroNumberException e) {
                removeZero(command);
            } catch (LowercaseSymbolException e) {
                fixLowercase(command);
            } catch (MultipleSymbolsException e) {
                splitMultiple(command);
            } catch (CommandException e) {
                // все остальные ошибки команды исправить нельзя
                log("    НЕ ВЫПОЛНЕНО: \"" + command + "\": " + e.getMessage());
            } catch (CoordinateOverflowException e) {
                log("    НЕ ВЫПОЛНЕНО: \"" + command + "\": " + e.getMessage());
            }
        }
    }

    // ---------------- обработчики исключений ----------------

    /** Вставляет пробел перед первым символом числа (цифрой или минусом). */
    private void fixMissingSpace(String command) throws IOException {
        int i = 0;
        while (i < command.length() && !isNumberStart(command.charAt(i))) {
            i++;
        }
        String fixed = command.substring(0, i) + " " + command.substring(i);
        putBack("добавлен пробел", command, fixed);
    }

    /** Заменяет несколько пробелов одним. */
    private void fixExtraSpaces(String command) throws IOException {
        String fixed = command.replaceAll(" {2,}", " ");
        putBack("убраны лишние пробелы", command, fixed);
    }

    /** Убирает минус перед числом (число стоит в конце строки, минус - последний в ней). */
    private void fixNegative(String command) throws IOException {
        int minus = command.lastIndexOf('-');
        String fixed = command.substring(0, minus) + command.substring(minus + 1);
        putBack("убран минус", command, fixed);
    }

    /** Заменяет строчные буквы направления заглавными. */
    private void fixLowercase(String command) throws IOException {
        int space = command.indexOf(' ');
        String fixed = command.substring(0, space).toUpperCase(Locale.ROOT) + command.substring(space);
        putBack("строчная буква заменена заглавной", command, fixed);
    }

    /** Команда с нулевым шагом удаляется, в стек ничего не возвращается. */
    private void removeZero(String command) throws IOException {
        log("    исправление (нулевое расстояние): команда \"" + command + "\" удалена");
    }

    /** Разбивает "ABC n" на "A n", "B n", "C n". В стек кладём в обратном порядке. */
    private void splitMultiple(String command) throws IOException {
        int space = command.indexOf(' ');
        String symbols = command.substring(0, space);
        String number = command.substring(space + 1);

        List<String> parts = new ArrayList<>();
        for (char c : symbols.toCharArray()) {
            parts.add(c + " " + number);
        }
        for (int i = parts.size() - 1; i >= 0; i--) {
            stack.push(parts.get(i));
        }
        log("    исправление (несколько направлений): \"" + command + "\" разбита на: "
                + String.join(", ", parts));
    }

    // ---------------- вспомогательное ----------------

    private void putBack(String what, String from, String to) throws IOException {
        stack.push(to);
        log("    исправление (" + what + "): \"" + from + "\" -> \"" + to + "\"");
    }

    private static boolean isNumberStart(char c) {
        return (c >= '0' && c <= '9') || c == '-';
    }

    private void log(String text) throws IOException {
        report.write(text + NL);
    }
}
