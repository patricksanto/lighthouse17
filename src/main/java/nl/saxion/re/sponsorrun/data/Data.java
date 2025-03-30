package nl.saxion.re.sponsorrun.data;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;

import javafx.scene.control.Alert;
import nl.saxion.re.sponsorrun.util.WindowHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    private static final String FILE_NAME = "teams.csv";
    private static final char SEPARATOR = ';';

    private static final ArrayList<Team> teams = new ArrayList<>();

    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public static void addTeam(Team team) {
        teams.add(team);
        saveToDisk();
    }

    public static void updateFromDisk() {
        teams.clear();
        File file = new File("src/main/resources/teams.csv");

        if (!file.exists()) {
            System.out.println("Arquivo " + FILE_NAME + " não encontrado.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            boolean isFirstLine = true;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // pula o header
                }

                String[] parts = line.split(";");
                if (parts.length >= 6) {
                    try {
                        Team team = new Team(
                                parts[0].trim(), // name
                                parts[1].trim(), // province
                                parts[2].trim(), // city
                                Integer.parseInt(parts[3].trim()), // members
                                parts[4].trim(), // teacher
                                parts[5].trim()  // contact
                        );
                        teams.add(team);
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter número de membros: " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo " + FILE_NAME + ": " + e.getMessage());
        }
    }

    public static void deleteTeam(Team team) {
        teams.removeIf(t -> t.equals(team));
        saveToDisk();
    }

    public static void updateTeam(Team oldTeam, Team updatedTeam) {
        int index = teams.indexOf(oldTeam);
        if (index >= 0) {
            teams.set(index, updatedTeam);
            saveToDisk();
        }
    }

    public static void saveToDisk() {
        File file = new File("src/main/resources/teams.csv");

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("Name;Province;City;Members;Teacher;Contact");

            for (Team team : teams) {
                String line = String.join(";",
                        team.getName(),
                        team.getProvince(),
                        team.getCity(),
                        String.valueOf(team.getMembers()),
                        team.getTeacher(),
                        team.getContact()
                );
                writer.println(line);
            }

            System.out.println("Arquivo salvo com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo CSV: " + e.getMessage());
        }
    }
}
