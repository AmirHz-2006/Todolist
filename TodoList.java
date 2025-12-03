import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TodoList extends JFrame {
    private ArrayList<String> tasks;
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskField;

    public TodoList() {
        tasks = new ArrayList<>();
        listModel = new DefaultListModel<>();
        // Fenster-Einstellungen
        setTitle("Aufgaben-Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        initializeComponents();
    }
    //    Initialisierung von Komponeten
    private void initializeComponents() {
        // Hauptpanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder( BorderFactory.createEmptyBorder(17, 17, 17, 17));
        mainPanel.setBackground(new Color(56, 106, 126));

        // Oberes Panel (Titel + Eingabe)
        JPanel topPanel = new JPanel(new BorderLayout(0, 8));
        topPanel.setBackground(new Color(56, 106, 126));;

        // Titel-Label
        JLabel titleLabel = new JLabel("Task Manager");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font( "Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Eingabe-Panel
        JPanel inputPanel = new JPanel(new BorderLayout(0,0));
        inputPanel.setBackground(new Color(255, 253, 253));

        // Eingabefeld für Aufgaben
        taskField = new JTextField();
        taskField.setBackground(Color.WHITE);
        taskField.setFont(new Font("Arial", Font.BOLD, 14));
        taskField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(56, 106, 126),1),
                BorderFactory.createEmptyBorder(8,12,8,12)
        ));

        // Button zum Hinzufügen
        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(0, 152, 246));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 30));
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        topPanel.add(inputPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Aufgabenliste
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setBackground(Color.WHITE);
        taskList.setPreferredSize(new Dimension(5,5));
        taskList.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(56, 106, 126, 255), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        taskList.setFont(new Font("Arial", Font.BOLD, 13));
        // Scrollbar für lange Listen
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,3,8));
        buttonPanel.setBackground(new Color(56, 106, 126));

        // Button-Panel
        JButton removeButton = new JButton("Remove" );
        JButton editButton = new JButton("Edit");
        JButton clearButton = new JButton("Clear All");
        JButton completeButton = new JButton("Complete");
        JButton exitButton = new JButton("Exit");

        //      Einstellung von Farben für Tasten
        removeButton.setBackground(new Color(201, 26, 0));
        removeButton.setForeground(Color.WHITE);

        editButton.setBackground(new Color(110, 0, 183));
        editButton.setForeground(Color.WHITE);

        clearButton.setBackground(new Color(161, 175, 0));
        clearButton.setForeground(Color.WHITE);

        completeButton.setBackground(new Color(0, 162, 0));
        completeButton.setForeground(Color.WHITE);

        exitButton.setBackground(new Color(120, 120, 120));
        exitButton.setForeground(Color.WHITE);

        // Gleiches Styling für Buttons
        JButton[] buttons = {removeButton, editButton, completeButton, clearButton, exitButton, addButton};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Arial", Font.BOLD, 13));
            btn.setPreferredSize(new Dimension(87,30));
            btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        // Buttons ins Panel zufügen
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(exitButton);
        // Zufügen Komponeten Hauptpanel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Ereignis-Handler (Event Listener)
        addButton.addActionListener((ActionEvent e) -> addTask());
        removeButton.addActionListener((ActionEvent e) -> removeTask());
        editButton.addActionListener((ActionEvent e) -> editTask());
        clearButton.addActionListener((ActionEvent e) -> clearAllTasks());
        completeButton.addActionListener((ActionEvent e) -> markTaskCompleted());
        exitButton.addActionListener((ActionEvent e) -> System.exit(0));
        add(mainPanel);;
    }
    //    Aufgabe hinzufügen
    private void addTask() {
        if (taskField != null && !taskField.getText().trim().isEmpty()) {
            String date = JOptionPane.showInputDialog(this,
                    "Enter date (YYYY-MM-DD): ",
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            if (date != null) {
                // اعتبارسنجی ساده تاریخ
                if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    String taskWithDate = taskField.getText().trim() + " -                     " +
                            "                       " +
                            "                       " + date;
                    tasks.add(taskWithDate);
                    listModel.addElement(taskWithDate);
                    taskField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Please use YYYY-MM-DD format (e.g., 2024-01-15)");
                }
            }
        }else {
            JOptionPane.showMessageDialog(this, "Please enter a task.");
        }
    }
    //    Aufgabe entfernen
    int selectedIndex;
    private void removeTask() {
        selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to remove.");
        }
    }
    //    Aufgabe bearbeiten
    private void editTask() {
        selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String currentTask = listModel.getElementAt(selectedIndex);
            String taskPart = "";
            if (currentTask.contains(" - ")) {
                taskPart = currentTask.substring(0, currentTask.indexOf(" - "));
            }
            String newTask = JOptionPane.showInputDialog(this, "Edit task: ", taskPart);
            if (newTask != null && !newTask.trim().isEmpty()) {
                String date = JOptionPane.showInputDialog(this,
                        "Enter date (YYYY-MM-DD):",
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                if (date != null && date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    String taskWithDate = newTask.trim() + " - " + date;
                    tasks.set(selectedIndex, taskWithDate);
                    listModel.set(selectedIndex, taskWithDate);
                } else if (date != null) {
                    JOptionPane.showMessageDialog(this, "Please use YYYY-MM-DD format (e.g., 2024-01-15)");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to edit.");
        }
    }
    //    Aufgabe als etw Gemachtes markieren
    private void markTaskCompleted() {
        selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String currentTask = listModel.getElementAt(selectedIndex);
            if (!currentTask.endsWith("  [DONE]")) {
                // Mark as completed
                String completedTask = currentTask + "  [DONE]";
                tasks.set(selectedIndex, completedTask);
                listModel.set(selectedIndex, completedTask);

                // Select the next task
                if (listModel.size() > 0) {
                    int newIndex = Math.min(selectedIndex, listModel.size() - 1);
                    taskList.setSelectedIndex(newIndex);
                }
            } else {
                // Mark as incomplete
                String incompleteTask = currentTask.replace("  [DONE]" , "");
                tasks.set(selectedIndex, incompleteTask);
                listModel.set(selectedIndex, incompleteTask);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark."); // پیام خطا
        }
    }
}