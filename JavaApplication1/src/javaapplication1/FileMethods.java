package javaapplication1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public interface FileMethods {
    static void updateData(HashMap<String, Employee> list) {
        try {
            // Creating an instance of the object BufferedWriter and putting the file path as an argument
            BufferedWriter file = new BufferedWriter(new FileWriter("employees_details"));
            // This for loop enters the hashmap and for each item in the hashmap it will create a line of details
            // Format
            // Name | Phone Number | Position | SSS | TIN | PHIL-HEALTH | PAG_IBIG | GROSS PAY | TOTAL DEDUCTIONS | NET PAY
            for(Map.Entry<String, Employee> in : list.entrySet()) {
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
    // This line will get all the data from the file/database and store it on a hash amp
    static void getData(HashMap<String, Employee> list) {
        // Clearing the list first before adding the details to avoid duplication
        list.clear();
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
                list.put(data[0].trim(), new Employee(data[0], data[1], data[2], data[3], data[4], data[5], data[6], Double.parseDouble(data[7]), Double.parseDouble(data[8]), Double.parseDouble(data[9])));
            }
            reader.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

