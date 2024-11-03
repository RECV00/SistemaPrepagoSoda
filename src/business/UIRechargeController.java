package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import data.RechargeData;
import data.StudentData;
import data.UserData;
import domain.StudentRecharge;
import domain.Recharge;
import domain.Student;
import domain.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;

public class UIRechargeController {
    @FXML
    private Label lTitule;
    @FXML
    private TextField tfCarnetStudent;
    @FXML
    private TextField tfNewAmount;
    @FXML
    private DatePicker dpDateEntry;
    @FXML
    private Label lCarnetStudent;
    @FXML
    private Label lAmountNew;
    @FXML
    private Button bRecharge;
    @FXML
    private Button bBack;

    private double saldo = 0.0;
    private User currentStudent;

    public void recoveredData(User student) {
        if (student != null) {
            currentStudent = student;
            tfCarnetStudent.setText(String.valueOf(student.getId()));
            tfCarnetStudent.setEditable(false);
            dpDateEntry.setValue(LocalDate.now());
            saldo = student.getMoneyAvailable();
        }
    }

    @FXML
    public void saveRecharge(ActionEvent event) {
        double amountToAdd = Double.parseDouble(tfNewAmount.getText());
        double newAmount = amountToAdd + saldo;
        LocalDate newDateEntry = dpDateEntry.getValue();

        if (amountToAdd < 1000 || amountToAdd > 10000) {
            JOptionPane.showMessageDialog(null, "El monto debe estar entre 1,000 y 10,000.");
            return;
        }

        // Crear un nuevo objeto StudentRecharge y guardarlo en JSON
        String carnet = String.valueOf(currentStudent.getId());
        StudentRecharge newRecharge = new StudentRecharge(String.valueOf(currentStudent.getId()), currentStudent.getName(), newDateEntry, newAmount);
        Recharge recharge = new Recharge(carnet, amountToAdd, newDateEntry);
        
        boolean rechargeSaved = RechargeData.saveRecharge(recharge); // Guardar en JSON

            if (rechargeSaved) {
                // Ahora actualizar también en la base de datos
                currentStudent.setMoneyAvailable(newAmount);
                UserData.updateUser(currentStudent);  // Este método actualizará la base de datos
                JOptionPane.showMessageDialog(null, "Recarga guardada y monto actualizado exitosamente en JSON y base de datos.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el monto del estudiante en JSON.");
            }
       
    }


    @FXML
    public void returnMain(ActionEvent event) {
        closeWindows();
    }

    @FXML
    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIViewStudent.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage temp = (Stage) bBack.getScene().getWindow();
            temp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}