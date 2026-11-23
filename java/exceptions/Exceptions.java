// Все исключительные ситуации в одном отдельном файле.
// Ни один класс не вложен в другой.

// ---------------- Ошибки команды (проверяемые) ----------------

/** Корень иерархии: любая ошибка в строке с командой. */
class CommandException extends Exception {
    private final String line;

    CommandException(String line, String message) {
        super(message);
        this.line = line;
    }

    String getLine() {
        return line;
    }
}

/** Строка вообще не похожа на команду (пункт 9). Исправить нельзя. */
class UnrecognizedCommandException extends CommandException {
    UnrecognizedCommandException(String line) {
        super(line, "строка не интерпретируется как команда");
    }
}

// ---- ошибки символа направления ----

class SymbolException extends CommandException {
    SymbolException(String line, String message) {
        super(line, message);
    }
}

/** Пункт 1: вместо направления стоит не буква. */
class NotLetterSymbolException extends SymbolException {
    NotLetterSymbolException(String line, char c) {
        super(line, "символ '" + c + "' не является буквой");
    }
}

/** Пункт 2: буква, но не N, S, E, W. */
class UnsupportedLetterException extends SymbolException {
    UnsupportedLetterException(String line, char c) {
        super(line, "буква '" + c + "' не является направлением (допустимы N, S, E, W)");
    }
}

/** Пункт 3: n, s, e, w вместо заглавных. Исправимая. */
class LowercaseSymbolException extends SymbolException {
    LowercaseSymbolException(String line) {
        super(line, "направление записано строчной буквой");
    }
}

/** Пункт 4: несколько направлений подряд. Исправимая. */
class MultipleSymbolsException extends SymbolException {
    MultipleSymbolsException(String line) {
        super(line, "вместо одного направления записано несколько");
    }
}

// ---- ошибки числа ----

class NumberException extends CommandException {
    NumberException(String line, String message) {
        super(line, message);
    }
}

/** Пункт 5: вместо числа что-то другое. */
class InvalidNumberException extends NumberException {
    InvalidNumberException(String line, String text) {
        super(line, "'" + text + "' не является целым числом");
    }
}

/** Пункт 8: модуль числа больше K. */
class NumberOutOfRangeException extends NumberException {
    NumberOutOfRangeException(String line, int k) {
        super(line, "модуль числа больше K = " + k);
    }
}

/** Пункт 6: число меньше нуля. Исправимая. */
class NegativeNumberException extends NumberException {
    NegativeNumberException(String line) {
        super(line, "число отрицательное");
    }
}

/** Пункт 7: число равно нулю. Исправимая (команда удаляется). */
class ZeroNumberException extends NumberException {
    ZeroNumberException(String line) {
        super(line, "число равно нулю");
    }
}

// ---- ошибки пробелов ----

class SpacingException extends CommandException {
    SpacingException(String line, String message) {
        super(line, message);
    }
}

/** Пункт 10: нет пробела между символом и числом. Исправимая. */
class MissingSpaceException extends SpacingException {
    MissingSpaceException(String line) {
        super(line, "нет пробела между направлением и числом");
    }
}

/** Дополнительная ИС: между направлением и числом больше одного пробела. Исправимая. */
class ExtraSpacesException extends SpacingException {
    ExtraSpacesException(String line) {
        super(line, "между направлением и числом лишние пробелы");
    }
}

// ---------------- Прочие ----------------

/** Ошибка заголовка файла (K, стартовые координаты). Проверяемая: без заголовка работать нельзя. */
class InvalidHeaderException extends Exception {
    InvalidHeaderException(String message) {
        super(message);
    }
}

/**
 * Дополнительная ИС: координата вышла за пределы long. Непроверяемая:
 * это не ошибка формата строки, а состояние исполнителя, и обрабатывать её
 * на каждом шаге через throws было бы лишним.
 */
class CoordinateOverflowException extends RuntimeException {
    CoordinateOverflowException(String message) {
        super(message);
    }
}
