package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ContactService;
import com.gabriel.prodmsv.model.Contact;
import javafx.event.ActionEvent;
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
    public TextField tfId;
    @javafx.fxml.FXML
    public TextField tfName;
    @javafx.fxml.FXML
    public TextField tfNum;
    @javafx.fxml.FXML
    public TextField tfCategory;
    @Setter
    Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    ContactService contactService;
    @Setter
    ConManController controller;
    @javafx.fxml.FXML
    private Button btnBack;
    @javafx.fxml.FXML
    private Button btnSubmit;

    public void refresh(){
        Contact contact = ConManController.contact;
        tfId.setText(Integer.toString(contact.getId()));
        tfName.setText(contact.getName());
        tfNum.setText(contact.getNumber());
        tfCategory.setText(contact.getCategoryName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("DeleteContactController: initialize");
        tfId=new TextField("");
        refresh();
    }

    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        System.out.println("CreateProductController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }


    @javafx.fxml.FXML
    public void onSubmit(ActionEvent actionEvent) {
        try {
            Contact contact = toObject(true);
            ContactService.getService().delete(contact.getId());
            controller.refresh();
            controller.clearControlTexts();
            Node node = ((Node) (actionEvent.getSource()));
            Window window = node.getScene().getWindow();
            window.hide();
            stage.setTitle("Manage Contact");
            stage.setScene(parentScene);
            stage.show();
        }
        catch (Exception e){
            String message="Error encountered deleting contact";
            showErrorDialog(message,e.getMessage());
        }
    }

    protected Contact toObject(boolean isEdit){
        Contact contact = new Contact();
        try {
            if(isEdit) {
                contact.setId(Integer.parseInt(tfId.getText()));
            }
            contact.setName(tfName.getText());
            contact.setNumber(tfNum.getText());
        }catch (Exception e){
            showErrorDialog("Error" ,e.getMessage());
        }
        return contact;
    }

    public void showErrorDialog(String message,String addtlMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(addtlMessage)));
        alert.showAndWait();
    }
}
