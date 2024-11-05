package business;

import data.RechargeData;
import data.ServerConnection;
import data.UserData;
import domain.Recharge;
import domain.StudentRecharge;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class UIBinnacleController {
    @FXML
    private Button btnReturnStar;
    @FXML
    private TableColumn<StudentRecharge, String> tvStudentName;
    @FXML
    private TableColumn<StudentRecharge, String> tvStudentCarnet;
    @FXML
    private TableColumn<StudentRecharge, LocalDate> tvDateRecharges;
    @FXML
    private TableColumn<StudentRecharge, Double> tvStudentAmount;
    @FXML
    private TextField tfBinnacleStudent;
    @FXML
    private Button btnConsult;
    @FXML
    private TableView<StudentRecharge> tvBinnacleStudentRecharge;

    private ServerConnection serverConnection;

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla
        tvStudentCarnet.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCarnetStudent()));
        tvStudentName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStudentName()));
        tvDateRecharges.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRechargeDate()));
        tvStudentAmount.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRechargeAmount()));
    }

    @FXML
    public void consultBinnacleStudent(ActionEvent event) {
        String carnet = tfBinnacleStudent.getText().trim();

        // Obtener las recargas del archivo JSON usando LinkedList<Recharge>
        LinkedList<Recharge> recharges = new LinkedList<>(RechargeData.getRechargesByCarnet1(carnet));

        // Convertir las recargas a objetos StudentRecharge
        LinkedList<StudentRecharge> studentRecharges = recharges.stream()
            .map(recharge -> {
                // Obtener el nombre del estudiante asociado al carnet
                String studentName = UserData.getStudentNameByCedula(Integer.parseInt(recharge.getCarnetStudent()));
                return new StudentRecharge(
                    recharge.getCarnetStudent(),
                    studentName,
                    recharge.getDateEntry(),
                    recharge.getAmount()
                );
            }).collect(Collectors.toCollection(LinkedList::new));

        // Llenar la tabla con los datos
        tvBinnacleStudentRecharge.getItems().setAll(studentRecharges);
    }

    @FXML
    public void returnStartMain(ActionEvent event) {
        closeWindows();
    }

    @FXML
    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIProfile.fxml"));
            Parent root = loader.load();
            UIProfileController controller = loader.getController();
            controller.setServerConnection(serverConnection); 
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Stage temp = (Stage) btnReturnStar.getScene().getWindow();
            temp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}