package nl.saxion.re.sponsorrun.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.saxion.re.sponsorrun.data.Data;
import nl.saxion.re.sponsorrun.data.Team;
import nl.saxion.re.sponsorrun.util.WindowHelper;

public class NewTeamController {
    private Team createdTeam;
    private boolean isEditMode = false;
    private Team teamToEdit;

    @FXML private TextField nameField;
    @FXML private TextField provinceField;
    @FXML private TextField cityField;
    @FXML private TextField membersField;
    @FXML private TextField teacherField;
    @FXML private TextField contactField;

    public Team getCreatedTeam() {
        return createdTeam;
    }

    @FXML
    private void handleSave() {
        try {
            Team team = new Team(
                    nameField.getText(),
                    provinceField.getText(),
                    cityField.getText(),
                    Integer.parseInt(membersField.getText()),
                    teacherField.getText(),
                    contactField.getText()
            );

            if (isEditMode) {
                Data.updateTeam(teamToEdit, team);
            } else {
                Data.addTeam(team);
            }
            Stage stage = (Stage) nameField.getScene().getWindow();
            WindowHelper.openMainLayoutWithView("team-list.fxml", stage);

        } catch (Exception e) {
            WindowHelper.showAlert("Erro ao salvar o time: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void setEditMode(Team team) {
        this.isEditMode = true;
        this.teamToEdit = team;

        nameField.setText(team.getName());
        provinceField.setText(team.getProvince());
        cityField.setText(team.getCity());
        membersField.setText(String.valueOf(team.getMembers()));
        teacherField.setText(team.getTeacher());
        contactField.setText(team.getContact());
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        WindowHelper.openMainLayoutWithView("team-list.fxml", stage);
    }
}
