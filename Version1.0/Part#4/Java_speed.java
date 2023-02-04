import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Java_speed {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String fileName = null;
        boolean verbose = false;

        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
                switch (args[0]) {
                    case "-v":
                        verbose = true;
                        break;
                    default:
                        System.err.println("Usage: java [-v] Loader filename");
                        System.exit(1);
                }
                fileName = args[1];
                break;
            default:
                System.err.println("Usage: java [-v] Loader filename");
                System.exit(1);
        }

        try {
            BufferedReader infile = new BufferedReader(new FileReader(fileName));

            System.out.println("Input 1 to select, 2 to delete, 3 to update, 4 to insert, -1 to quit.");
            int operation = in.nextInt();

            if (operation == 1/*Select*/) {
                System.out.print("From ");
                int begin = in.nextInt();
                System.out.print(" to ");
                int over = in.nextInt();

                Object[][] table = new Object[over - begin + 1][7];

                long start = System.currentTimeMillis();

                infile.readLine();
                String line, parts[];
                int cnt = 0;
                while ((line = infile.readLine()) != null) {
                    parts = line.split(",");
                    if (parts.length > 1) {
                        if (Integer.parseInt(parts[0]) > over) break;
                        if (Integer.parseInt(parts[0]) >= begin) {
                            table[cnt][0] = parts[0];
                            table[cnt][1] = parts[1];
                            table[cnt][2] = parts[2];
                            table[cnt][3] = parts[3];
                            table[cnt][4] = parts[4];
                            table[cnt][5] = parts[5];
                            cnt++;
                        }
                    }
                }

                boolean nextline = true;
                for (int i = 0; i < over - begin; i++) {
                    for (int j = 0; j < 6; j++) {
                        if (table[i][j] != null)
                            System.out.print(table[i][j] + " ");
                        else {
                            nextline = false;
                            break;
                        }
                    }
                    if (nextline)
                        System.out.println();
                    nextline = true;
                }
                long end = System.currentTimeMillis();
                System.out.println("Time: " + (end - start) + "ms");
                int ave;
                if (end - start != 0)
                    ave = (int) ((over - begin + 1) * 1000 / (end - start));
                else ave = 0;
                System.out.print("Totally " + (over - begin + 1) + " Java deleting statement. \nLoading speed is " + ave + " records/s");

                infile.close();
            } else if (operation == 2/*Delete*/) {
                System.out.println("Input 1 to batch delete ,2 to select delete");
                int choose = in.nextInt();


                if (choose == 1) {
                    System.out.print("From ");
                    int begin = in.nextInt();
                    System.out.print(" to ");
                    int over = in.nextInt();
                    long start = System.currentTimeMillis();

                    File TemporaryFiles = new File("Temporary Files.csv");
                    TemporaryFiles.createNewFile();

                    BufferedWriter tempWriter = new BufferedWriter(new FileWriter(TemporaryFiles));
                    String title = infile.readLine();
                    tempWriter.write(title);
                    tempWriter.newLine();
                    tempWriter.flush();

                    String line, parts[];
                    int cnt = 0;
                    while ((line = infile.readLine()) != null) {
                        parts = line.split(",");
                        if (parts.length > 1) {
                            if (Integer.parseInt(parts[0]) < begin || Integer.parseInt(parts[0]) > over) {
                                tempWriter.write(line);
                                tempWriter.newLine();
                                cnt++;
                            }
                        }
                    }
                    tempWriter.flush();
                    infile.close();
                    tempWriter.close();

                    BufferedReader tempReader = new BufferedReader(new FileReader(TemporaryFiles));
                    BufferedWriter outfile = new BufferedWriter(new FileWriter(fileName));

                    cnt = 0;
                    while ((line = tempReader.readLine()) != null) {
                        outfile.write(line);
                        outfile.newLine();
                        cnt++;
                    }
                    outfile.flush();

                    tempReader.close();
                    outfile.close();
                    TemporaryFiles.delete();

                    long end = System.currentTimeMillis();

                    System.out.println("Time: " + (end - start) + "ms");
                    int ave;
                    if (end - start != 0)
                        ave = (int) ((over - begin) * 1000 / (end - start));
                    else ave = 0;
                    System.out.print("Totally " + (over - begin) + " Java deleting statement. \nLoading speed is " + ave + " records/s");

                } else if (choose == 2) {
                    System.out.println("Input deleting lines: ");
                    int l = in.nextInt();
                    ArrayList<Integer> lines = new ArrayList<>();
                    for (int i = 0; i < l; i++) {
                        lines.add(in.nextInt());
                    }

                    long start = System.currentTimeMillis();
                    File TemporaryFiles = new File("Temporary Files.csv");
                    TemporaryFiles.createNewFile();

                    BufferedWriter tempWriter = new BufferedWriter(new FileWriter(TemporaryFiles));
                    String title = infile.readLine();
                    tempWriter.write(title);
                    tempWriter.newLine();
                    tempWriter.flush();

                    String line, parts[];
                    int cnt = 0;
                    while ((line = infile.readLine()) != null) {
                        parts = line.split(",");
                        if (parts.length > 1) {
                            if (!lines.contains(Integer.parseInt(parts[0]))) {
                                tempWriter.write(line);
                                tempWriter.newLine();
                                cnt++;
                            }
                        }
                    }

                    tempWriter.flush();
                    infile.close();
                    tempWriter.close();

                    BufferedReader tempReader = new BufferedReader(new FileReader(TemporaryFiles));
                    BufferedWriter outfile = new BufferedWriter(new FileWriter(fileName));

                    cnt = 0;
                    while ((line = tempReader.readLine()) != null) {
                        outfile.write(line);
                        outfile.newLine();
                        cnt++;
                    }
                    outfile.flush();

                    tempReader.close();
                    outfile.close();
                    TemporaryFiles.delete();

                    long end = System.currentTimeMillis();

                    System.out.println("Time: " + (end - start) + "ms");
                    int ave;
                    if (end - start != 0)
                        ave = (int) ((l) * 1000 / (end - start));
                    else ave = 0;
                    System.out.print("Totally " + (l) + " Java deleting statement. \nLoading speed is " + ave + " records/s");

                }
            } else if (operation == 3/*Update*/) {

                File file = new File("Update.cvs");

                System.out.print("From ");
                int begin = in.nextInt();
                System.out.print(" to ");
                int over = in.nextInt();
                long start = System.currentTimeMillis();
                Random random = new Random();
                int contract_id = 5000;
                int product_id = 325;
                int salesman_id = 990;
                int product_num;
                String date;

                int con_id;
                int pro_id;
                int sal_id;

                File TemporaryFiles = new File("Temporary Files.csv");
                TemporaryFiles.createNewFile();

                BufferedWriter tempWriter = new BufferedWriter(new FileWriter(TemporaryFiles));
                String title = infile.readLine();
                tempWriter.write(title);
                tempWriter.newLine();
                tempWriter.flush();

                String line, parts[];
                int cnt = 0, infop = 0;
                while ((line = infile.readLine()) != null) {
                    parts = line.split(",");
                    if (parts.length > 1) {
                        if (Integer.parseInt(parts[0]) < begin || Integer.parseInt(parts[0]) > over) {
                            tempWriter.write(line);
                            tempWriter.newLine();
                        } else {
                            con_id = random.nextInt((contract_id - 1) + 1) + 1;
                            pro_id = random.nextInt((product_id - 1) + 1) + 1;
                            sal_id = random.nextInt((salesman_id - 1) + 1) + 1;
                            product_num = random.nextInt((10000 - 1) + 1) + 1;
                            date = String.valueOf(random.nextInt((2021 - 2000) + 1) + 2000) + "-" + (random.nextInt((12 - 1) + 1) + 1) + "-" + (random.nextInt((28 - 1) + 1) + 1) + "c";

                            tempWriter.write(parts[0] + "," + con_id + "," + pro_id + "," + sal_id + "," + pro_id + "," + product_num + "," + date);
                            tempWriter.newLine();
                        }
                        cnt++;
                    }
                }
                tempWriter.flush();
                infile.close();
                tempWriter.close();

                BufferedReader tempReader = new BufferedReader(new FileReader(TemporaryFiles));
                BufferedWriter outfile = new BufferedWriter(new FileWriter(fileName));

                cnt = 0;
                while ((line = tempReader.readLine()) != null) {
                    outfile.write(line);
                    outfile.newLine();
                    cnt++;
                }
                outfile.flush();

                tempReader.close();
                outfile.close();
                TemporaryFiles.delete();

                long end = System.currentTimeMillis();

                System.out.println("Time: " + (end - start) + "ms");
                int ave;
                if (end - start != 0)
                    ave = (int) ((over - begin) * 1000 / (end - start));
                else ave = 0;
                System.out.print("Totally " + (over - begin) + " Java deleting statement. \nLoading speed is " + ave + " records/s");

            } else if (operation == 4/*Insert*/) {

                Random random = new Random();
                int contract_id = 5000;
                int product_id = 325;
                int salesman_id = 990;
                int product_num;
                String date;

                System.out.print("Count ");
                int count = in.nextInt();

                int con_id;
                int pro_id;
                int sal_id;
                long start = System.currentTimeMillis();

                File TemporaryFiles = new File("Temporary Files.csv");
                TemporaryFiles.createNewFile();

                BufferedWriter tempWriter = new BufferedWriter(new FileWriter(TemporaryFiles));
                String title = infile.readLine();
                tempWriter.write(title);
                tempWriter.newLine();
                tempWriter.flush();

                String line, parts[];
                int cnt = 0;
                while ((line = infile.readLine()) != null) {
                    parts = line.split(",");
                    if (parts.length > 1) {
                        tempWriter.write(line);
                        tempWriter.newLine();
                        cnt++;
                    }
                }
                tempWriter.flush();
                infile.close();

                for (int i = 0; i <= count; i++) {
                    con_id = random.nextInt((contract_id - 1) + 1) + 1;
                    pro_id = random.nextInt((product_id - 1) + 1) + 1;
                    sal_id = random.nextInt((salesman_id - 1) + 1) + 1;
                    product_num = random.nextInt((10000 - 1) + 1) + 1;
                    date = String.valueOf(random.nextInt((2021 - 2000) + 1) + 2000) + "-" + (random.nextInt((12 - 1) + 1) + 1) + "-" + (random.nextInt((28 - 1) + 1) + 1);

                    tempWriter.write(cnt++ + "," + con_id + "," + pro_id + "," + sal_id + "," + product_num + "," + date);
                    tempWriter.newLine();
                }
                tempWriter.flush();
                tempWriter.close();


                BufferedReader tempReader = new BufferedReader(new FileReader(TemporaryFiles));
                BufferedWriter outfile = new BufferedWriter(new FileWriter(fileName));

                cnt = 0;
                while ((line = tempReader.readLine()) != null) {
                    outfile.write(line);
                    outfile.newLine();
                    cnt++;
                }
                outfile.flush();

                tempReader.close();
                outfile.close();
                TemporaryFiles.delete();

                long end = System.currentTimeMillis();

                System.out.println("Time: " + (end - start) + "ms");
                int ave;
                if (end - start != 0)
                    ave = (int) ((count) * 1000 / (end - start));
                else ave = 0;
                System.out.print("Totally " + (count) + " Java deleting statement. \nLoading speed is " + ave + " records/s");

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
