package innowise.zuevsky.helpdesk.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@NoArgsConstructor
public class TicketsFieldsValidator implements RegexValidator{

    private static final String TICKET_NAME = "^[a-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|}]{2,100}$";
    private static final String TICKET_DESCRIPTION = "^[A-Za-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|}]{2,500}$";

    private Pattern pattern;
    private Matcher matcher;

    @Override
    public boolean validateRegex(String field, String regex) {
        pattern = switch (regex) {
            case TICKET_NAME -> Pattern.compile(TICKET_NAME);
            case TICKET_DESCRIPTION -> Pattern.compile(TICKET_DESCRIPTION);
            default -> Pattern.compile("");
        };
        matcher = pattern.matcher(field);
        return matcher.find();
    }

    public boolean validateTicketName(String name) {
        return validateRegex(name, TICKET_NAME);
    }

    public boolean validateTicketDescription(String description) {
        return validateRegex(description, TICKET_DESCRIPTION);
    }

    public boolean validateTicketStateId(String stateId) {
        return switch (stateId) {
            case "Draft", "New", "Approved", "Declined", "In Progress", "Done", "Canceled" -> true;
            default -> false;
        };
    }

    public boolean validateTicketUrgencyId(String urgencyId) {
        return switch (urgencyId) {
            case "Critical", "High", "Average", "Low" -> true;
            default -> false;
        };
    }

    public boolean validateTicketCategoryId(int categoryId) {
        return switch (categoryId) {
            case 1, 2, 3, 4, 5, 6 -> true;
            default -> false;
        };
    }
}
