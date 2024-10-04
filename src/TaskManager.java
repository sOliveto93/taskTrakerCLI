import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private static final String JSON = "tasks.json";
    private int nextId;

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(JSON);

        if (!file.exists()) {
            try {
                file.createNewFile();
                setNextId(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    jsonBuilder.append(line);
                }
                String jsonString = jsonBuilder.toString();

                if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
                    jsonString = jsonString.replace("[{", "");
                    String[] taskStrings = jsonString.split("\\},\\s*\\{");

                    for (String s : taskStrings) {

                        Task t = parseTask(s);
                        tasks.add(t);
                    }

                    if (!tasks.isEmpty()) {
                        setNextId(tasks.get(tasks.size() - 1).getId() + 1);
                    } else {
                        setNextId(1);
                    }
                } else {
                    System.err.println("JSON format error: " + jsonString);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    public void saveTask(List<Task> tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(JSON))) {
            bw.write("[");
            for (int i = 0; i < tasks.size(); i++) {

                bw.write(tasks.get(i).toString());
                if (i < tasks.size() - 1) {
                    bw.write(",");
                }
            }
            bw.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Task parseTask(String json) {
        Task task = new Task();
        String[] parts = json.split(",");

        for (String part : parts) {

            String[] keyValue = part.split(":", 2);
            if (keyValue.length != 2) {
                continue;
            }

            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim().replace("\"", "");

            switch (key) {
                case "id":

                    task.setId(Integer.parseInt(value));

                    break;
                case "description":
                    task.setDescription(value);
                    break;
                case "status":
                    task.setStatus(value);
                    break;
                case "createdAt":
                    task.setCreatedAt(DateUtil.parseDate(value));
                    break;
                case "updatedAt":
                    task.setUpdatedAt(DateUtil.parseDate(value));
                    break;
                default:
                    System.out.println("No matching case for key: " + key);
                    break;
            }
        }

        return task;
    }


    public List<Task> filter(List<Task> tasks, String filter) {
        List<Task> filerList = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getStatus().equals(filter)) {
                filerList.add(t);
            }
        }
        return filerList;
    }

    public void setNextId(int id) {
        this.nextId = id;
    }

    public int getNextId() {
        return this.nextId;
    }
    public void markStatus(List<Task> tasks,String command,String status){
        String idString = "";
            int id;
            try {
                idString = command.split(" ")[1];
                if (idString.trim().isEmpty()) {
                    System.out.println("ID is empty");
                    return;
                }
                id = Integer.parseInt(idString);
            } catch (Exception e) {
                System.out.println("Error al procesar el ID.");
                return;
            }

            if (id > 0) {
                boolean found = false; 

                for (Task t : tasks) {
                    if (t.getId() == id) {
                        found = true; 
                        if (t.getStatus().equals(status)) {
                            System.out.println("status was update");
                            return;
                        }
                        System.out.println("exito al cambiar el status");
                        t.setStatus(status);
                        break; 
                    }
                }

                if (found) {
                    saveTask(tasks); 
                } else {
                    System.out.println("ID no válido");
                }
            } else {
                System.out.println("ID no válido");
            }

    }
}
