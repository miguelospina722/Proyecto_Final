package co.edu.umanizales.helpdesku.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ticket extends baseentity {

    private String title;
    private String description;
    private String statusId;
    private String categoryId;
    private String priorityId;
    private String requesterId;
    private String assigneeId;
    private LocalDateTime dueDate;
    private String addres;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(title == null ? "" : title);
        builder.append(",");
        builder.append(description == null ? "" : description);
        builder.append(",");
        builder.append(statusId == null ? "" : statusId);
        builder.append(",");
        builder.append(categoryId == null ? "" : categoryId);
        builder.append(",");
        builder.append(priorityId == null ? "" : priorityId);
        builder.append(",");
        builder.append(requesterId == null ? "" : requesterId);
        builder.append(",");
        builder.append(assigneeId == null ? "" : assigneeId);
        builder.append(",");
        if (dueDate != null) {
            builder.append(dueDate);
        }
        return builder.toString();
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            title = data[3];
        }
        if (data.length > 4) {
            description = data[4];
        }
        int legacyOffset = data.length > 11 ? 1 : 0;
        if (data.length > 5 + legacyOffset) {
            statusId = data[5 + legacyOffset];
        }
        if (data.length > 6 + legacyOffset) {
            categoryId = data[6 + legacyOffset];
        }
        if (data.length > 7 + legacyOffset) {
            priorityId = data[7 + legacyOffset];
        }
        if (data.length > 8 + legacyOffset) {
            requesterId = data[8 + legacyOffset];
        }
        if (data.length > 9 + legacyOffset) {
            assigneeId = data[9 + legacyOffset];
        }
        int dueDateIndex = 10 + legacyOffset;
        if (data.length > dueDateIndex && data[dueDateIndex] != null && !data[dueDateIndex].isEmpty()) {
            dueDate = LocalDateTime.parse(data[dueDateIndex]);
        }
    }

    @Override
    public String[] headers() {
        return mergeHeaders(
                "title",
                "description",
                "status_id",
                "category_id",
                "priority_id",
                "requester_id",
                "assignee_id",
                "due_date"
        );
    }
}
