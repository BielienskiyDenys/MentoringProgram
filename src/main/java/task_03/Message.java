package task_03;

public class Message {
    private Theme theme;
    private String body;

    public Message(Theme theme, String body) {
        this.theme = theme;
        this.body = body;
    }

    public Theme getTheme() {
        return theme;
    }

    public String getBody() {
        return body;
    }
}
