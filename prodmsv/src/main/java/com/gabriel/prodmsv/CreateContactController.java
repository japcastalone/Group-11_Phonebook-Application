package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ContactService;
import com.gabriel.prodmsv.ServiceImpl.CategoryService;
import com.gabriel.prodmsv.model.Contact;
import com.gabriel.prodmsv.model.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Setter
public class CreateContactController implements Initializable {
    @Setter
    private ConManController conManController; // Ensure this matches the name used in ConManController

    @FXML
    public TextField tfName;
    @FXML
    public TextField tfNum;
    @FXML
    private ComboBox<Category> cbCategory;
    @FXML
    public Button btnSubmit;
    @FXML
    public Button btnNext;
    @FXML
    private Button btnBack;

    @Setter
    Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    ContactService contactService;
    @Setter
    CategoryService categoryService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("CreateContactController: initialize");

        try {
            // Convert the Category array to a List
            Category[] categoriesArray = categoryService.getCategories(); // This returns Category[]
            List<Category> categories = Arrays.stream(categoriesArray).collect(Collectors.toList());

            cbCategory.getItems().clear();
            cbCategory.getItems().addAll(categories);

            tfName.setText("");
            tfNum.setText("");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearControlTexts(){
        tfName.setText("");
        tfNum.setText("");
        cbCategory.getSelectionModel().clearSelection();
    }

    @FXML
    public void onNext(ActionEvent actionEvent) {
        System.out.println("CreateContactController:onNext ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) throws Exception{
        Contact contact = new Contact();
        contact.setName(tfName.getText());
        contact.setNumber(tfNum.getText());
        Category category = cbCategory.getSelectionModel().getSelectedItem();
        if (category != null) {
            contact.setCategoryId(category.getId());
            contact.setCategoryName(category.getName());
        }
        try {
            contact = contactService.create(contact);
            conManController.refresh();  // Call the refresh method from ConManController
            onBack(actionEvent);
        } catch(Exception ex) {
            System.out.println("CreateContactController:onSubmit Error: " + ex.getMessage());
        }
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
        System.out.println("CreateContactController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }
}
