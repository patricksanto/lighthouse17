package nl.saxion.re.sponsorrun.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nl.saxion.re.sponsorrun.data.Data;
import nl.saxion.re.sponsorrun.data.Team;
import nl.saxion.re.sponsorrun.util.WindowHelper;

import java.io.IOException;

public class TeamListController {

    @FXML
    private GridPane teamGrid;

    @FXML
    private void initialize() {
        for (var team : Data.getTeams()) {
            VBox card = createTeamCard(team);
            int index = teamGrid.getChildren().size();
            teamGrid.add(card, index % 3, index / 3);
        }
    }

    private VBox createTeamCard(Team team) {
        VBox box = new VBox(5);
        box.setStyle("-fx-border-color: #cccccc; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        box.setPrefWidth(200);

        Label nameLabel = new Label(team.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label cityLabel = new Label(team.getCity());
        Label membersLabel = new Label(team.getMembers() + " members");

        Button editBtn = new Button("Edit Team");
        Button deleteBtn = new Button("Delete");

        deleteBtn.setOnAction(e -> {
            Stage stage = (Stage) deleteBtn.getScene().getWindow();
            ConfirmDeleteTeamController controller = WindowHelper.setRootWithController("confirm_delete_team.fxml", stage);
            controller.setTeam(team);
        });

        editBtn.setOnAction(e -> {
            Stage stage = (Stage) editBtn.getScene().getWindow();
            NewTeamController controller = WindowHelper.setRootWithController("new_team_form.fxml", stage);
            controller.setEditMode(team);
        });


        HBox buttons = new HBox(5, editBtn, deleteBtn);
        box.getChildren().addAll(nameLabel, cityLabel, membersLabel, buttons);
        return box;
    }


    @FXML
    private void handleAddTeam() {
        try {
            Stage stage = (Stage) teamGrid.getScene().getWindow();
            WindowHelper.setRootWithController("new_team_form.fxml", stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTeam(nl.saxion.re.sponsorrun.data.Team team) {
        VBox card = createTeamCard(team);
        int index = teamGrid.getChildren().size();
        teamGrid.add(card, index % 3, index / 3);
    }

}
