package store.view.parser;

import store.util.ErrorMessage;

public final class StringToIntParser {

    private StringToIntParser() {
    }

    public static int parsePositiveInt(String value) {
        int number = parseInt(value);

        if (number <= 0) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX + "수량은 1 이상의 정수여야 합니다.");
        }
        return number;
    }

    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX + "올바른 정수 형식으로 입력해주세요.");
        }
    }
}
