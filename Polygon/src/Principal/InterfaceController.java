package Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class InterfaceController implements Initializable {

    Polyline polyline = new Polyline();
    
    private final BooleanProperty DrawMode;
    
    @FXML
    private AnchorPane canvas;
    @FXML
    private ColorPicker Colorpicker;
    @FXML
    private CheckBox Fill;

    public InterfaceController() {
        this.DrawMode = new SimpleBooleanProperty();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        polyline.setStrokeWidth(4);
        polyline.setStroke(Colorpicker.getValue());
        DrawMode.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                canvas.getScene().setCursor(Cursor.CROSSHAIR);
            } else {
                canvas.getScene().setCursor(Cursor.DEFAULT);
                DrawLine(polyline.getPoints().get(0), polyline.getPoints().get(1));
            }
        });
    }

    private void DrawLine(Double p1, Double p2) {
        polyline.getPoints().addAll(new Double[]{
            p1, p2
        });
    }

    @FXML
    private void Draw(MouseEvent event) {
        if (DrawMode.getValue()) {
            DrawLine(event.getX(), event.getY());
            canvas.getChildren().remove(polyline);
            canvas.getChildren().add(polyline);
        }
    }

    @FXML
    private void DrawLine(ActionEvent event) {
        DrawMode.set(!DrawMode.get());
    }

    @FXML
    private void moveLeft(ActionEvent event) {
        polyline.setTranslateX(polyline.getTranslateX() - 10);
    }

    @FXML
    private void moveRight(ActionEvent event) {
        polyline.setTranslateX(polyline.getTranslateX() + 10);
    }

    @FXML
    private void moveUp(ActionEvent event) {
        polyline.setTranslateY(polyline.getTranslateY() - 10);
    }

    @FXML
    private void moveDown(ActionEvent event) {
        polyline.setTranslateY(polyline.getTranslateY() + 10);
    }

    @FXML
    private void maximize(ActionEvent event) {
        polyline.setScaleX(polyline.getScaleX() + 0.25);
        polyline.setScaleY(polyline.getScaleY() + 0.25);
    }

    @FXML
    private void minimize(ActionEvent event) {
        polyline.setScaleX(polyline.getScaleX() - 0.25);
        polyline.setScaleY(polyline.getScaleY() - 0.25);
    }

    @FXML
    private void rotate(ActionEvent event) {
        polyline.setRotate(polyline.getRotate() + 90);
    }

    @FXML
    private void ChangeColor(Event event) {
        polyline.setStroke(Colorpicker.getValue());
        ChangeFill();
    }

    @FXML
    private void setFill(ActionEvent event) {
        ChangeFill();
    }

    public void ChangeFill() {
        if (Fill.isSelected()) {
            polyline.setFill(Colorpicker.getValue().deriveColor(2, 2, 2, 5));
        } else {
            polyline.setFill(Color.TRANSPARENT);
        }
    }

    @FXML
    private void credits(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Credits.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Integrantes del equipo");
        stage.getIcons().add(new Image("/Assets/Images/icosahedron.png"));
        stage.setScene(scene);
        stage.show();
    }
}
