package exceptions;

public class SeleniumException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected String linkToScreenshot;

    public SeleniumException(String message, String linkToScreenshot,Throwable cause) {
        super(message, cause);
        this.linkToScreenshot = linkToScreenshot;
    }

    public SeleniumException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeleniumException() {
    }

    public String getLinkToScreenshot() {
        return linkToScreenshot;
    }

    public void setLinkToScreenshot(String linkToScreenshot) {
        this.linkToScreenshot = linkToScreenshot;
    }

}
