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
    private status status;
    private category category;
    private priority priority;
    private user requester;
    private user assignee;
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
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            title = data[3];
        }
        if (data.length > 4) {
            description = data[4];
        }
        int legacyOffset = data.length > 11 ? 1 : 0;
        status = referenceFromId(valueAt(data, 5 + legacyOffset), status::new);
        category = referenceFromId(valueAt(data, 6 + legacyOffset), category::new);
        priority = referenceFromId(valueAt(data, 7 + legacyOffset), priority::new);
        requester = referenceFromId(valueAt(data, 8 + legacyOffset), user::new);
        assignee = referenceFromId(valueAt(data, 9 + legacyOffset), user::new);
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
