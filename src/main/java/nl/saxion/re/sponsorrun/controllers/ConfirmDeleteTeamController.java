package nl.saxion.re.sponsorrun.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import nl.saxion.re.sponsorrun.data.Data;
import nl.saxion.re.sponsorrun.data.Team;
import nl.saxion.re.sponsorrun.util.WindowHelper;

public class ConfirmDeleteTeamController {
    private Team team;

    @FXML private Label teamInfoLabel;

    public void setTeam(Team team) {
        this.team = team;
        teamInfoLabel.setText(
                "Name: " + team.getName() + "\n" +
                        "Province: " + team.getProvince() + "\n" +
                        "City: " + team.getCity() + "\n" +
                        "Members: " + team.getMembers() + "\n" +
                        "Teacher: " + team.getTeacher() + "\n" +
                        "Contact: " + team.getContact()
        );
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) teamInfoLabel.getScene().getWindow();
        WindowHelper.setRoot("team-list.fxml", stage);
    }

    @FXML
    private void handleDelete() {
        Data.deleteTeam(team);
        Stage stage = (Stage) teamInfoLabel.getScene().getWindow();
        WindowHelper.setRoot("team-list.fxml", stage);
    }
}
