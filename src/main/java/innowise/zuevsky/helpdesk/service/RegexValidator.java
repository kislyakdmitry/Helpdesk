package innowise.zuevsky.helpdesk.service;

import org.springframework.stereotype.Service;

@Service
public interface RegexValidator {

    boolean validateRegex(String field, String regex);
}
