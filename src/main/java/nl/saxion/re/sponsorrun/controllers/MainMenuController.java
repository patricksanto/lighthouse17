package nl.saxion.re.sponsorrun.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;

public class MainMenuController {
    @FXML private StackPane contentArea;
    private String initialView = "home.fxml";

    @FXML
    private void initialize() {
        loadView(initialView);
    }

    public void setInitialView(String fxml) {
        this.initialView = fxml;
    }

    private void loadView(String fxml) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/nl/saxion/re/sponsorrun/" + fxml));
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void goHome() {
        loadView("home.fxml");
    }

    @FXML private void goTournaments() {
        loadView("tournaments.fxml");
    }

    @FXML private void goTeams() {
        loadView("team-list.fxml");
    }

    @FXML private void goSchedule() {
        loadView("schedule.fxml");
    }
}
