package edu.umanizales.helpdesk_u.service;

import edu.umanizales.helpdesk_u.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    public List<Comment> listCommentsByTicket(String ticketId) {
        throw new UnsupportedOperationException("Implement CSV-backed comment lookup");
    }

    public Comment addComment(String ticketId, Comment comment) {
        throw new UnsupportedOperationException("Implement CSV-backed comment storage");
    }
}
