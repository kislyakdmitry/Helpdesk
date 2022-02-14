package innowise.zuevsky.helpdesk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TicketsCreationService {

    private TicketsFieldsValidator validator;

    public String getTicketName(String name) {
        if (validator.validateTicketName(name)) {
            return name;
        }
        return "";
    }

    public String getTicketDescription(String description) {
        if (validator.validateTicketDescription(description)) {
            return description;
        }
        return "";
    }

    public LocalDate getTicketCreatedOnDate() {
        return LocalDate.now();
    }

    public LocalDate getTicketDesiredResolutionDate(LocalDate date) {
        return date;
    }

    public Long getAssigneeId(Long id) {
        return id;
    }

    public Long getOwnerId(Long id) {
        return id;
    }

    public String getStateId(String stateId) {
        if (validator.validateTicketStateId(stateId)) {
            return stateId;
        }
        return "";
    }

    public int getCategoryId(int categoryId) {
        if (validator.validateTicketCategoryId(categoryId)) {
            return categoryId;
        }
        return 0;
    }

    public String getUrgencyId(String urgencyId) {
        if (validator.validateTicketUrgencyId(urgencyId)) {
            return urgencyId;
        }
        return "";
    }

    public Long getApproverId(Long id) {
        return id;
    }
}