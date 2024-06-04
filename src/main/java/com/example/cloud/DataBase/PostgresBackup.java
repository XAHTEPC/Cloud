package com.example.cloud.DataBase;
    import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

    public class PostgresBackup {

        public static void main(String[] args) {
            // Database credentials
            String user = "your_username";
            String password = "your_password"; // Only required for non-prompt method
            String host = "localhost";
            String port = "5432";
            String database = "your_database";
            String backupPath = "/path/to/backup/your_database_backup.backup";

            // Command for pg_dump
            String[] command = new String[]{
                    "pg_dump",
                    "-U", user,
                    "-h", host,
                    "-p", port,
                    "-F", "c",  // Custom format
                    "-b",      // Include large objects
                    "-v",      // Verbose mode
                    "-f", backupPath,
                    database
            };

            // Setting the environment variable for the password (optional)
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.environment().put("PGPASSWORD", password);

            try {
                // Start the process
                Process process = processBuilder.start();

                // Reading the standard output and error streams of the process
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                while ((line = errorReader.readLine()) != null) {
                    System.err.println(line);
                }

                // Wait for the process to complete
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("Backup created successfully at " + backupPath);
                } else {
                    System.err.println("Error occurred during backup. Exit code: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

