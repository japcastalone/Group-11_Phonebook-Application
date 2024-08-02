package com.gabriel.prodmsv;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Data;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public class SplashController implements Initializable {
    @Setter
    Stage stage;
    @javafx.fxml.FXML
    @Setter
    public ImageView productImage;
    @Setter
    Image image;
    @javafx.fxml.FXML
    private Button btnProceed;
    @javafx.fxml.FXML
    private StackPane pane;
    @javafx.fxml.FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image = new Image(getClass().getResourceAsStream("/images/wink.gif"));
        productImage.setImage(image);
    }

   @javafx.fxml.FXML
    public void onProceed(ActionEvent actionEvent) {
        System.out.println("SplashApp:onClose ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SplashApp.class.getResource("conman-view.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            ConManController prodManController  = fxmlLoader.getController();
            prodManController.setStage(stage);
            Scene scene = new Scene(root);
            stage.setTitle("Contact Management");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Error occured" + ex.getMessage());
        }
    }
}
