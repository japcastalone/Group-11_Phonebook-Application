package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ContactService;
import com.gabriel.prodmsv.ServiceImpl.CategoryService;
import com.gabriel.prodmsv.model.Contact;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class ConManController implements Initializable {
    @Setter
    Stage stage;
    @Setter
    Scene createViewScene;
    @Setter
    Scene updateViewScene;
    @Setter
    Scene deleteViewScene;

    @FXML
    public TextField tfId;
    @FXML
    public TextField tfName;
    @FXML
    public TextField tfNum;
    @FXML
    public TextField tfCategory;
    @FXML
    public ImageView productImage;

    Image puffy;
    Image wink;

    @FXML
    public Button createButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button closeButton;

    public static Contact contact; // Ensure this is public static

    @FXML
    private ListView<Contact> lvContacts;

    UpdateContactController updateProductController;
    DeleteContactController deleteProductController;
    CreateContactController createContactController;
    ContactService contactService;
    CategoryService categoryService;
    @FXML
    private HBox prodman;

    void refresh() throws Exception {
        contactService = ContactService.getService();
        Contact[] contacts = contactService.getContacts();
        lvContacts.getItems().clear();
        lvContacts.getItems().addAll(contacts);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ConManController: initialize");
        disableControls();

        try {
            refresh();
            try {
                puffy = new Image(getClass().getResourceAsStream("/images/puffy.gif"));
                wink = new Image(getClass().getResourceAsStream("/images/wink.gif"));
                productImage.setImage(puffy);
            } catch (Exception ex) {
                System.out.println("Error with image: " + ex.getMessage());
            }
        } catch (Exception ex) {
            showErrorDialog("Message: " + ex.getMessage());
        }
    }

    public void disableControls() {
        tfId.setEditable(false);
        tfName.setEditable(false);
        tfNum.setEditable(false);
        tfCategory.setEditable(false);
    }

    public void setControlTexts(Contact contact) {
        tfName.setText(contact.getName());
        tfNum.setText(contact.getNumber());
        tfCategory.setText(contact.getCategoryName());
    }

    public void clearControlTexts() {
        tfId.setText("");
        tfName.setText("");
        tfNum.setText("");
        tfCategory.setText("");
    }

    @FXML
    public void onMouseClicked(MouseEvent mouseEvent) {
        contact = lvContacts.getSelectionModel().getSelectedItem();
        if (contact == null) {
            return;
        }
        tfId.setText(Integer.toString(contact.getId()));
        setControlTexts(contact);
        System.out.println("clicked on " + contact);
    }

    @FXML
    public void onCreate(ActionEvent actionEvent) {
        System.out.println("ConmanController:onNewContact ");
        Node node = ((Node) (actionEvent.getSource()));
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        try {
            if (createViewScene == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(SplashApp.class.getResource("create-contact.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                createContactController = fxmlLoader.getController();
                createContactController.setStage(this.stage);
                createContactController.setParentScene(currentScene);
                createContactController.setContactService(contactService);
                createContactController.setCategoryService(categoryService);
                createContactController.setConManController(this);
                createViewScene = new Scene(root);
                stage.setTitle("Manage Contact");
                stage.setScene(createViewScene);
                stage.show();
            } else {
                stage.setScene(createViewScene);
                stage.show();
            }
            createContactController.clearControlTexts();
            clearControlTexts();
        } catch (Exception ex) {
            System.out.println("ConmanController: " + ex.getMessage());
        }
    }

    @FXML
    public void onUpdate(ActionEvent actionEvent) {
        System.out.println("ConmanController:onUpdate ");
        Node node = ((Node) (actionEvent.getSource()));
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        try {
            if (updateViewScene == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(SplashApp.class.getResource("update-contact.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                updateProductController = fxmlLoader.getController();
                updateProductController.setController(this);
                updateProductController.setStage(this.stage);
                updateProductController.setParentScene(currentScene);
                updateViewScene = new Scene(root);
            } else {
                updateProductController.refresh();
            }
            stage.setTitle("Update Contact");
            stage.setScene(updateViewScene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("ConmanController: " + ex.getMessage());
        }
    }

    @FXML
    public void onDelete(ActionEvent actionEvent) {
        System.out.println("ConmanController:onDelete ");
        Node node = ((Node) (actionEvent.getSource()));
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        try {
            if (deleteViewScene == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(SplashApp.class.getResource("delete-contact.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                deleteProductController = fxmlLoader.getController();
                deleteProductController.setController(this);
                deleteProductController.setStage(this.stage);
                deleteProductController.setParentScene(currentScene);
                deleteViewScene = new Scene(root);
            } else {
                deleteProductController.refresh();
            }
            stage.setTitle("Delete Contact");
            stage.setScene(deleteViewScene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("ConmanController: " + ex.getMessage());
        }
    }

    @FXML
    public void onClose(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and lose changes?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
    }

    void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void addItem(Contact contact) {
        lvContacts.getItems().add(contact);
    }

    public Contact getContact() {
        return contact;
    }
}
