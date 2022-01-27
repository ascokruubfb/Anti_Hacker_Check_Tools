import java.io.*;
public class FIO_Utils {
    public static void IO_Write(String filename,String content,boolean append) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename, append));
            out.write(content + "\n");
            out.close();
        } catch (IOException e) {
        }
    }
    public String IO_Read(String name) throws IOException {
        FileReader fileReader = new FileReader(name);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String lines="";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines+=line+"\n";
        }
        return lines;
    }
}
