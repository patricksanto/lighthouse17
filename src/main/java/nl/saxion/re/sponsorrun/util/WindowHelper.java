package nl.saxion.re.sponsorrun.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import nl.saxion.re.sponsorrun.LighthouseApp;
import nl.saxion.re.sponsorrun.controllers.MainMenuController;

import java.io.IOException;

public class WindowHelper {

    /***
     * Open a new window with the given fxml file
     * @param fxmlResource The fxml filename without path
     * @param title the title of the window
     * @param width the width of the window
     * @param height the height of the window
     */
    public static void openWindow(String fxmlResource, String title, int width, int height) {
        openWindow(fxmlResource, title, width, height, new Stage());
    }

    /***
     * Open a new window with the given fxml file
     * @param fxmlResource The fxml filename without path
     * @param title the title of the window
     * @param width the width of the window
     * @param height the height of the window
     * @param stage (optional) stage if you already have one
     */
    public static void openWindow(String fxmlResource, String title, int width, int height, Stage stage) {

        FXMLLoader fxmlLoader = new FXMLLoader(LighthouseApp.class.getResource(fxmlResource));
        try {

            var scene = new Scene(fxmlLoader.load(), width, height);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            showAlert("Error opening window for " + fxmlResource + ".\n\n" + e, Alert.AlertType.ERROR);
        }
    }

    public static void openMainLayoutWithView(String initialFxml, Stage stage) {
        try {
            double width = stage.getWidth();
            double height = stage.getHeight();

            FXMLLoader fxmlLoader = new FXMLLoader(LighthouseApp.class.getResource("main_layout.fxml"));
            Parent root = fxmlLoader.load();

            MainMenuController controller = fxmlLoader.getController();
            controller.setInitialView(initialFxml);

            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle("Sponsor Run App");
            stage.show();

        } catch (IOException e) {
            showAlert("Erro ao carregar layout principal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    /***
     * close any window
     * @param randomControlOnWindow One of the controls on the window. May be any control (button, label, ...)
     */
    public static void closeWindow(Control randomControlOnWindow) {
        Stage stage = (Stage) randomControlOnWindow.getScene().getWindow();
        stage.close();
    }

    /***
     * Show a popup dialog with a message
     * @param message the message to show
     */
    public static void showAlert(String message) {
       showAlert(message, Alert.AlertType.INFORMATION);
    }

    public static void showAlert(String message, Alert.AlertType type) {
        new Alert(type, message).show();
    }

    public static void setRoot(String fxmlResource, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LighthouseApp.class.getResource(fxmlResource));
            Parent root = (Parent) fxmlLoader.load();
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            showAlert("Error setting window root for " + fxmlResource + ".\n\n" + e, Alert.AlertType.ERROR);
        }
    }

    public static <T> T setRootWithController(String fxmlResource, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LighthouseApp.class.getResource(fxmlResource));
            Parent root = (Parent) fxmlLoader.load();
            stage.getScene().setRoot(root);
            return fxmlLoader.getController();
        } catch (Exception e) {
            showAlert("Error setting window root for " + fxmlResource + ".\n\n" + e, Alert.AlertType.ERROR);
            return null;
        }
    }
}
