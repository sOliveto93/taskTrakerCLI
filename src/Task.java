import java.util.Date;

public class Task {
    int id;
    String description;
    String status;
    Date createdAt;
    Date updatedAt;

    public Task() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
       
    }

    @Override
    public String toString() {
        return "{ \"id\": \"" + id + "\", " +
                "\"description\": \"" + (description != null ? description : "null") + "\", " +
                "\"status\": \"" + (status != null ? status : "null") + "\", " +
                "\"createdAt\": \"" + (createdAt != null ? DateUtil.formaDate(createdAt) : "null") + "\", " +
                "\"updatedAt\": \"" + (updatedAt != null ? DateUtil.formaDate(updatedAt) : "null") + "\" }";
    }

}
