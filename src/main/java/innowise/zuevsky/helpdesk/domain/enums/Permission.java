package innowise.zuevsky.helpdesk.domain.enums;

public enum Permission {
    TICKET_GET("ticket:get"),
    TICKET_POST("ticket:post");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
