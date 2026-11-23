import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * Формат входного файла:
 *   строка 1: K
 *   строка 2: x y (начальные координаты)
 *   далее по одной команде в строке; строка X или Q завершает список.
 */
public class Main {
    public static void main(String[] args) {
        String inputName = args.length > 0 ? args[0] : "commands.txt";
        String outputName = args.length > 1 ? args[1] : "result.txt";

        try (BufferedReader in = Files.newBufferedReader(Paths.get(inputName), StandardCharsets.UTF_8);
             BufferedWriter out = Files.newBufferedWriter(Paths.get(outputName), StandardCharsets.UTF_8)) {

            int k = readK(in);
            long[] start = readStart(in);

            Robot robot = new Robot(start[0], start[1]);
            CommandParser parser = new CommandParser(k, robot);
            CommandReader reader = new CommandReader(parser, robot, out);

            out.write("K = " + k + ", старт = " + robot.position() + System.lineSeparator());
            reader.readAll(in, 2);
            out.write("Итоговая позиция: " + robot.position() + System.lineSeparator());

            System.out.println("Готово, результат в файле " + outputName);
        } catch (NoSuchFileException e) {
            System.err.println("Файл не найден: " + e.getFile());
        } catch (AccessDeniedException e) {
            System.err.println("Нет доступа к файлу: " + e.getFile());
        } catch (InvalidHeaderException e) {
            System.err.println("Ошибка в заголовке файла: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }

    private static int readK(BufferedReader in) throws IOException, InvalidHeaderException {
        String line = in.readLine();
        if (line == null) {
            throw new InvalidHeaderException("в файле нет значения K");
        }
        try {
            int k = Integer.parseInt(line.trim());
            if (k <= 0) {
                throw new InvalidHeaderException("K должно быть положительным: " + k);
            }
            return k;
        } catch (NumberFormatException e) {
            throw new InvalidHeaderException("K должно быть целым числом: '" + line.trim() + "'");
        }
    }

    private static long[] readStart(BufferedReader in) throws IOException, InvalidHeaderException {
        String line = in.readLine();
        if (line == null) {
            throw new InvalidHeaderException("в файле нет начальных координат");
        }
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 2) {
            throw new InvalidHeaderException("ожидалось 'x y', получено '" + line.trim() + "'");
        }
        try {
            return new long[] {Long.parseLong(parts[0]), Long.parseLong(parts[1])};
        } catch (NumberFormatException e) {
            throw new InvalidHeaderException("координаты должны быть целыми: '" + line.trim() + "'");
        }
    }
}
