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
public class Ticket extends BaseEntity {

    private String title;
    private String description;
    private Status status;
    private Category category;
    private Priority priority;
    private User requester;
    private User assignee;
    private LocalDateTime dueDate;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(title == null ? "" : title);
        builder.append(",");
        builder.append(description == null ? "" : description);
        builder.append(",");
        builder.append(idOrEmpty(status));
        builder.append(",");
        builder.append(idOrEmpty(category));
        builder.append(",");
        builder.append(idOrEmpty(priority));
        builder.append(",");
        builder.append(idOrEmpty(requester));
        builder.append(",");
        builder.append(idOrEmpty(assignee));
        builder.append(",");
        if (dueDate != null) {
            builder.append(dueDate);
        }
        return builder.toString();
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = CsvHelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            title = data[3];
        }
        if (data.length > 4) {
            description = data[4];
        }
        int legacyOffset = data.length > 11 ? 1 : 0;
        status = referenceFromId(valueAt(data, 5 + legacyOffset), Status::new);
        category = referenceFromId(valueAt(data, 6 + legacyOffset), Category::new);
        priority = referenceFromId(valueAt(data, 7 + legacyOffset), Priority::new);
        requester = referenceFromId(valueAt(data, 8 + legacyOffset), User::new);
        assignee = referenceFromId(valueAt(data, 9 + legacyOffset), User::new);
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
