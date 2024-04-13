package javaapplication1;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PayrollManager extends JFrame implements Assets, Calculators {
    private final JTextField employeeName = new JTextField();
    private final JTextField salaryRate = new JTextField();
    private final JTextField daysWorked = new JTextField();
    private final JTextField hoursOvertime = new JTextField();
    private final JTextField hoursLate = new JTextField();
    private final JTextField advancedPay = new JTextField();
    private final JTextField regularHoliday = new JTextField();
    private final JTextField specialHolidays = new JTextField();
    private final JLabel sssLabel = new JLabel();
    private final JLabel tinLabel = new JLabel();
    private final JLabel philHealthLabel = new JLabel();
    private final JLabel pagIbigLabel = new JLabel();
    private final JLabel grossPayLabel = new JLabel();
    private final JLabel deductionsLabel = new JLabel();
    private final JLabel netPayLabel = new JLabel();
    private final JLabel fullNameLabel = new JLabel("Nathaniel Alvarez");
    private final JLabel basic = new JLabel("12,002");
    private double grossPay;
    private double totalGovernmentDeductions;
    private double netPay;
    HashMap<String, Employee> employeeList = new HashMap<>();
    // Panel for the payroll page that holds all the components
    public JPanel payrollPage() {
        JPanel panel = new JPanel();
        panel.setBackground(SECONDARY_BACKGROUND);
        panel.setBounds(0, 0, 800, 600);
        panel.setLayout(null);
        add(panel);
        panel.add(salaryManagementPanel());
        panel.add(payslipPanel());
        return panel;
    }
    // Panel for the salary manager
    public JPanel salaryManagementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(PRIMARY_BACKGROUND);
        panel.setBorder(new RoundedBorder(20, Color.BLACK));
        panel.setBounds(10, 10, 300, 500);
        add(panel);
        title(panel);
        calculateButton(panel);
        textField(panel, 40, "Employee Name", employeeName);
        textField(panel, 90, "Salary Rate", salaryRate);
        textField(panel, 140, "Number of Days Worked", daysWorked);
        textField(panel, 190, "Number of Overtime Hours", hoursOvertime);
        textField(panel, 240, "Number of Hours Late", hoursLate);
        textField(panel, 290, "Advanced Pay (bale)", advancedPay);
        textField(panel, 340, "Number of Regular Holidays", regularHoliday);
        textField(panel, 390, "Number of Special Holidays", specialHolidays);
        return panel;
    }
    // Just the text or h1 for salary manager panel
    public void title(JPanel panel) {
        JLabel label = new JLabel("SALARY MANAGER");
        label.setBounds(10, 10, 290, 20);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        panel.add(label);
    }
    // Text fields for the information needed
    public void textField(JPanel panel, int y, String title, JTextField value) {
        JLabel label = new JLabel(title);
        label.setBounds(10, y + 20, 250, 30);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        panel.add(label);
        value.setBounds(10, y, 280, 30);
        panel.add(value);
    }
    // Button to do the calculation
    public void calculateButton(JPanel panel) {
        JButton button = new JButton("Calculate");
        button.setBounds(190, 460, 100, 20);
        button.setBackground(SECONDARY_BACKGROUND);

        button.addActionListener(e -> {
            getEmployeesData();
            try {
                // I used ternary operator for less line of codes
                // Example
                // salaryRate.getText().isEmpty() ? 0 : Double.parseDouble(salaryRate.getText());
                // if the text is empty, set the value to zero else set the value by getting the text from text field and converting it to a double
                double _salaryRate = salaryRate.getText().isEmpty() ? 0 : Double.parseDouble(salaryRate.getText());
                int _daysWorked = daysWorked.getText().isEmpty() ? 0 : Integer.parseInt(daysWorked.getText());
                int _hoursOvertime = hoursOvertime.getText().isEmpty() ? 0 : Integer.parseInt(hoursOvertime.getText());
                int _hoursLate = hoursLate.getText().isEmpty() ? 0 : Integer.parseInt(hoursLate.getText());
                double _advancedPay = advancedPay.getText().isEmpty() ? 0 : Double.parseDouble(advancedPay.getText());
                int _regularHoliday = regularHoliday.getText().isEmpty() ? 0 : Integer.parseInt(regularHoliday.getText());
                int _specialHoliday = specialHolidays.getText().isEmpty() ? 0 : Integer.parseInt(specialHolidays.getText());

                grossPay = Calculators.grossPayCalculator(_salaryRate, _daysWorked, _hoursOvertime, _regularHoliday, _specialHoliday, _hoursLate, _advancedPay);
                totalGovernmentDeductions = Calculators.governmentDeductionsCalculator(grossPay);
                netPay = Calculators.netPayCalculator(grossPay, totalGovernmentDeductions);

                updateDataBaseDetails();

            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please input a valid number.");
            }
        });

        panel.add(button);
    }
    // Deductions Panel
    public JPanel payslipPanel() {
        JPanel panel = new JPanel();
        panel.setBounds(320, 10, 450, 500);
        panel.setBackground(PRIMARY_BACKGROUND);
        panel.setBorder(new RoundedBorder(20, Color.BLACK));
        panel.add(companyNameLabel());
        panel.setLayout(null);
        aboutMoney(panel, "EARNINGS", 90);
        aboutMoney(panel, "DEDUCTIONS", 260);
        titleAndAmountLabel(panel, fullNameLabel, "Name: ", 10, 20, 70);
        titleAndAmountLabel(panel, basic, "Basic: ", 75, 70, 120);
        dateAndTime(panel);
        return panel;
    }
    // h1 for government deductions panel
    public JLabel companyNameLabel() {
        JLabel label = new JLabel("EXL CORPORATION");
        label.setBounds(20, 25, 300, 20);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        return label;
    }
    // Texts to show the deductions information
    public void titleAndAmountLabel(JPanel panel, JLabel amountLabel, String title, int y, int x, int x1) {
        JLabel label = new JLabel(title);
        label.setBounds(x, y, 150, 100);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        panel.add(label);
        amountLabel.setBounds(x1, y, 300, 100);
        amountLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        panel.add(amountLabel);
    }
    // EARNING/DEDUCTIONS LABEL
    public void aboutMoney(JPanel panel, String title, int x) {
        JLabel label = new JLabel(title);
        label.setBounds(x, 85, 300, 15);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        panel.add(label);
    }
    // This line will get all the data from the file/database
    public void getEmployeesData () {
        // Clearing the list first before adding the details to avoid duplication
        employeeList.clear();
        try {
            // Creating an instance of the object BufferedWriter and putting the file path as an argument
            BufferedReader reader = new BufferedReader(new FileReader("employees_details"));
            String line;
            // While the line is not equals to null it will continue the loop
            while((line = reader.readLine()) != null) {
                // Converting the line of details to an array
                // Result will be
                // [name, phone, position, etc.]
                String[] data = line.split("\\|");
                // Putting the data on the hashmap and making the name of the employee as the key
                 employeeList.put(data[0].trim(), new Employee(data[0], data[1], data[2], data[3], data[4], data[5], data[6], Double.parseDouble(data[7]), Double.parseDouble(data[8]), Double.parseDouble(data[9])));
            }
            reader.close();
        }catch (FileNotFoundException e) {
            System.out.println("error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // This function updates the database/file whenever the button is clicked
    public void updateDataBaseDetails() {
        try {
            // Creating an instance of the object BufferedWriter and putting the file path as an argument
            BufferedWriter file = new BufferedWriter(new FileWriter("employees_details"));
            // This for loop enters the hashmap and for each item in the hashmap it will create a line of details
            // Format
            // Name | Phone Number | Position | SSS | TIN | PHIL-HEALTH | PAG_IBIG | GROSS PAY | TOTAL DEDUCTIONS | NET PAY
            for(Map.Entry<String, Employee> in : employeeList.entrySet()) {
                file.write(in.getValue().getFullName() + " | " +
                        in.getValue().getPhoneNumber() + " | " +
                        in.getValue().getPosition() + " | " +
                        in.getValue().getSss() + " | " +
                        in.getValue().getTin() + " | " +
                        in.getValue().getPhilHeath() + " | " +
                        in.getValue().getPagIbig() + " | " +
                        in.getValue().getGrossPay() + " | " +
                        in.getValue().getTotalDeductions() + " | " +
                        in.getValue().getNetPay() + "\n"
                );
            }
            file.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // This function displays the current time and date
    static void dateAndTime(JPanel panel) {
        // Creating an instance of the object DateTimeFormatter and passing an argument for the format of the time and date
        DateTimeFormatter currDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        // Getting the current time and date
        LocalDateTime now = LocalDateTime.now();
        JLabel label = new JLabel(currDate.format(now));
        label.setBounds(340, 10, 200, 10);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        panel.add(label);
    }
}
