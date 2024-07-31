package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ContactService;
import com.gabriel.prodmsv.ServiceImpl.CategoryService;
import com.gabriel.prodmsv.model.Contact;
import com.gabriel.prodmsv.model.Category;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class UpdateContactController implements Initializable {
    @Setter
    Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    ConManController controller;

    @FXML
    private TextField tfId;
    int id;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfNum;
    @FXML
    private TextField tfCategory;
    @FXML
    private ComboBox<Category> cbCategory;

    public void refresh() throws Exception{
        Contact contact = ConManController.contact;
        tfId.setText(Integer.toString(contact.getId()));
        tfName.setText(contact.getName());
        tfNum.setText(contact.getNumber());
        cbCategory.getItems().clear();
        Category[] categories =  (Category[]) CategoryService.getService().getCategories();
        cbCategory.getItems().addAll(categories);
        cbCategory.getSelectionModel().select(CategoryService.getService().getCategory(contact.getCategoryId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateContactController: initialize");
        tfId=new TextField();
        try {
            refresh();
        }
        catch(Exception ex){
            System.out.println("UpdateContactController: " + ex.getMessage());
        }
    }

    public void onSubmit(ActionEvent actionEvent) {
        Contact contact = new Contact();
        contact.setId(Integer.parseInt(tfId.getText()));
        contact.setName(tfName.getText());
        contact.setNumber(tfNum.getText());
        Category category = cbCategory.getSelectionModel().getSelectedItem();
        contact.setCategoryId(category.getId());
        contact.setCategoryName(category.getName());

        try{
            contact = ContactService.getService().update(contact);
            controller.refresh();
            controller.setControlTexts(contact);
            onBack(actionEvent);
        }
        catch(Exception ex){
            System.out.println("CreateProductController:onSubmit Error: " + ex.getMessage());
        }
    }

    public void onBack(ActionEvent actionEvent) {
        System.out.println("CreateProductController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }
}
