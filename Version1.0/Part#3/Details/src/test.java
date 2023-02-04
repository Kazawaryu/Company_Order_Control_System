import java.io.*;

public class test {
    public static void main(String[] args) throws IOException {
        //character stream
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            BufferedReader infile = new BufferedReader(new FileReader("javaPart/src/contract_info.csv"));
            String writeName = "javaPart/src/test.csv";
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            String line;
            while ((line = infile.readLine()) != null) {
                out.write(line + "\n");
            }
            out.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("character stream time: "+(end - start));

        //byte stream
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            FileInputStream fis = new FileInputStream("javaPart/src/contract_info.csv");
            FileOutputStream fos = new FileOutputStream("javaPart/src/test.csv");
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fis.close();
            fos.close();
        }
        long end1 = System.currentTimeMillis();
        System.out.println("byte stream time: "+(end1 - start1));


    }
}
