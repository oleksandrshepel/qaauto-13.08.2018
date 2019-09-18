package exceptions;

import com.google.common.base.MoreObjects;

public class WaitingTimeoutValidationException extends SeleniumException {
    public static final String JS_JQUERY_PRIMEFACES_WAIT_FAILED = "Таймаут ожидания завершения активности jQuery и пустой очереди ajax запросов PrimeFaces";
    public static final String GUI_BLOCK_PANEL_HIDE_WAIT_FAILED = "Таймаут ожидания возврата из состояния uiStateWait (Блокирующая панель 'Зачекайте')";

    public WaitingTimeoutValidationException(Throwable cause, String message, String linkToScreenshot) {
        super(message, linkToScreenshot, cause);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("message", getMessage())
                .add("screenshot", getLinkToScreenshot())
                .add("cause", getCause()) //Исключение которое было причиной уже отображается в trace
                .toString();
    }
}
