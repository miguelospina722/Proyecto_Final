package edu.umanizales.helpdesk_u.service;

import edu.umanizales.helpdesk_u.model.Assignment;
import edu.umanizales.helpdesk_u.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    public List<Assignment> listAssignmentsByTicket(Ticket ticket) {
        throw new UnsupportedOperationException("Implement CSV-backed assignment lookup using ticket relationships");
    }

    public Assignment registerAssignment(Assignment assignment) {
        throw new UnsupportedOperationException("Implement CSV-backed assignment storage using assignment relationships");
    }
}
