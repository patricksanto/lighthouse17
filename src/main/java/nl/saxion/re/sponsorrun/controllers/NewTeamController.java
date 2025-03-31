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
        String name = nameField.getText().trim();
        String province = provinceField.getText().trim();
        String city = cityField.getText().trim();
        String membersText = membersField.getText().trim();
        String teacher = teacherField.getText().trim();
        String contact = contactField.getText().trim();

        // Required fields validation
        if (name.isEmpty() || province.isEmpty() || city.isEmpty() ||
                membersText.isEmpty() || teacher.isEmpty() || contact.isEmpty()) {
            WindowHelper.showAlert("Please fill in all fields.", Alert.AlertType.WARNING);
            return;
        }

        // Check if the team name already exists (only for new entries or changed names)
        boolean nameExists = Data.getTeams().stream()
                .anyMatch(t -> t.getName().equalsIgnoreCase(name) && (!isEditMode || !t.equals(teamToEdit)));
        if (nameExists) {
            WindowHelper.showAlert("A team with this name already exists.", Alert.AlertType.WARNING);
            return;
        }

        // Validate number of members
        int members;
        try {
            members = Integer.parseInt(membersText);
            if (members <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            WindowHelper.showAlert("Invalid number of members. It must be a number greater than zero.", Alert.AlertType.WARNING);
            return;
        }

        // Validate Dutch phone number
        if (!contact.matches("^(\\+31|0)[1-9][0-9]{8}$")) {
            WindowHelper.showAlert("Invalid contact number. It must be a valid Dutch phone number (e.g., +31612345678 or 0612345678).", Alert.AlertType.WARNING);
            return;
        }

        try {
            Team team = new Team(name, province, city, members, teacher, contact);
            if (isEditMode) {
                Data.updateTeam(teamToEdit, team);
            } else {
                Data.addTeam(team);
            }
            Stage stage = (Stage) nameField.getScene().getWindow();
            WindowHelper.openMainLayoutWithView("team-list.fxml", stage);
        } catch (Exception e) {
            WindowHelper.showAlert("Error saving the team: " + e.getMessage(), Alert.AlertType.ERROR);
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
