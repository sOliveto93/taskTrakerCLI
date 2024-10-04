import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final TaskManager TASK_MANAGER = new TaskManager();
    private static final Scanner sc = new Scanner(System.in);
    List<Task> tasks;

    public App() {
        tasks = TASK_MANAGER.loadTasks();
    }

    public void menu(String command) {
        System.out.println("--------" + command + "-------");

        if (command.startsWith("add")) {
            List<Task>tasks=TASK_MANAGER.loadTasks();
            String description = "";
            try {
                description = command.split("\"")[1];
                
                if (description.trim().isEmpty()) {
                    System.out.println("description is empty");
                    return;
                }
            } catch (Exception e) {
                System.out.println("description is empty");
                return;
            }

            Task task = new Task();
            task.setId(TASK_MANAGER.getNextId());
            task.setDescription(description);
            task.setStatus("todo");
            Date now = new Date();
            task.setCreatedAt(now);
            task.setUpdatedAt(now);
            tasks.add(task);

            TASK_MANAGER.saveTask(tasks);
            TASK_MANAGER.setNextId(task.getId() + 1);

        } else if (command.equals("list")) {
            List<Task>tasks=TASK_MANAGER.loadTasks();
            listar(tasks);
            return;
        } else if (command.equals("list done")) {
            List<Task>tasks=TASK_MANAGER.loadTasks();
            listar(TASK_MANAGER.filter(tasks, "done")); 
            return;
        } else if (command.equals("list todo")) {
            List<Task>tasks=TASK_MANAGER.loadTasks();
            listar(TASK_MANAGER.filter(tasks, "todo")); 
            return;
        } else if (command.equals("list in-progress")) {
            List<Task>tasks=TASK_MANAGER.loadTasks();
            listar(TASK_MANAGER.filter(tasks, "in-progress"));
            return;
        } else if (command.startsWith("update")) {
            List<Task>tasks=TASK_MANAGER.loadTasks();
            String idString = command.split(" ")[1];
            int id = 0;
            try {
                id = Integer.parseInt(idString);
            } catch (Exception e) {
                System.out.println("ID no válido.");
                return;
            }

            String newDescription = command.split("\"")[1];
            for (Task t : tasks) {
                if (t.getId() == id) {
                    t.setDescription(newDescription);
                    break;
                }
            }
            TASK_MANAGER.saveTask(tasks);
            return;

        } else if (command.startsWith("delete")) {
            List<Task>tasks=TASK_MANAGER.loadTasks();
            List<Task> newList = new ArrayList<>();
            String idString = command.split(" ")[1];
            int id;
            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                System.out.println("Error: ID no válido.");
                return;
            }

            boolean found = false;

            for (Task t : tasks) {
                if (t.getId() != id) {
                    newList.add(t);
                } else {
                    found = true; 
                }
            }

            if (found) {
                TASK_MANAGER.saveTask(newList); 
                tasks = newList; 
                System.out.println("Tarea eliminada.");
            } else {
                System.out.println("No se encontró ninguna tarea con el ID: " + id);
            }

        } else if (command.startsWith("mark-in-progress")) {
            TASK_MANAGER.markStatus(TASK_MANAGER.loadTasks(),command,"in-progress");
        }else if(command.startsWith("mark-done")){
            TASK_MANAGER.markStatus(TASK_MANAGER.loadTasks(),command,"done");
        }
         else {
            System.out.println("Command not found");
        }
    }

    public void listar(List<Task> tasks) {
        if (!tasks.isEmpty()) {
            for (Task t : tasks) {
                System.out.println("id: " + t.getId());
                System.out.println("description: " + t.getDescription());
                System.out.println("status: " + t.getStatus());
                System.out.println("createdAt: " + t.getCreatedAt());
                System.out.println("updatedAt: " + t.getUpdatedAt());
            }
        } else {
            System.out.println("list empty --> []");
        }
    }

    public static void main(String[] args){
        App app = new App();
        String command;

        while (true) {
            System.out.println("Command : add, update, delete, list, list done, list todo, list in-progress");
            command = sc.nextLine();
            app.menu(command);
        }
    }
}
