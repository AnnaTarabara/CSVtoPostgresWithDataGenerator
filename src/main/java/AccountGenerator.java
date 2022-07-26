import java.io.*;

public class AccountGenerator {
    static void generateCSVorTXT(int numberOfAccounts, String txtOrCsvFileName) {

        String nameFormat = "name%03d";
        String passwordFormat = "password%03d";
        String emailFormat = "email%03d@mail.ru";
        String header = "name_account,password,email\n";
        int startNumberOfAccount = 1;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(txtOrCsvFileName));

            writer.write(header);

            for(int currNumberOfAccount = startNumberOfAccount; currNumberOfAccount <= numberOfAccounts; currNumberOfAccount++) {

                String[] row = {String.format(nameFormat, currNumberOfAccount),
                                String.format(passwordFormat, currNumberOfAccount),
                                String.format(emailFormat, currNumberOfAccount)};

                String stringToWrite = String.join(",", row)+"\n";
                writer.write(stringToWrite);
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}
