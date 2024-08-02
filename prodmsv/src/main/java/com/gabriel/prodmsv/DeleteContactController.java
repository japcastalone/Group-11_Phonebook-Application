package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ContactService;
import com.gabriel.prodmsv.model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class DeleteContactController implements Initializable {
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfNum;
    @FXML
    private TextField tfCategory;
    @Setter
    private Stage stage;
    @Setter
    private Scene parentScene;
    @Setter
    private ContactService contactService;
    @Setter
    private ConManController controller;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSubmit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("DeleteContactController: initialize");
        try {
            refresh();
        } catch (Exception e) {
            showErrorDialog("Initialization Error", e.getMessage());
        }
    }

    public void refresh() throws Exception {
        Contact contact = controller.getContact(); // Get contact from controller
        if (contact != null) {
            tfId.setText(Integer.toString(contact.getId()));
            tfName.setText(contact.getName());
            tfNum.setText(contact.getNumber());
            tfCategory.setText(contact.getCategoryName());
        } else {
            // Handle case where contact is null, e.g., show an error or clear fields
            showErrorDialog("Error", "No contact data available to display.");
        }
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
        System.out.println("DeleteContactController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) {
        try {
            Contact contact = toObject();
            contactService.delete(contact.getId());
            controller.refresh();
            controller.clearControlTexts();
            onBack(actionEvent);
        } catch (Exception e) {
            showErrorDialog("Error encountered deleting contact", e.getMessage());
        }
    }

    private Contact toObject() {
        Contact contact = new Contact();
        try {
            contact.setId(Integer.parseInt(tfId.getText()));
            contact.setName(tfName.getText());
            contact.setNumber(tfNum.getText());
            // Note: Category is not used in delete, hence it's not set here.
        } catch (NumberFormatException e) {
            showErrorDialog("Error", "Invalid ID format");
        } catch (Exception e) {
            showErrorDialog("Error", e.getMessage());
        }
        return contact;
    }

    private void showErrorDialog(String message, String additionalMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(additionalMessage)));
        alert.showAndWait();
    }
}
