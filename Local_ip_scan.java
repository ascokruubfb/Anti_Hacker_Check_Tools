import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class Local_ip_scan {

    public ArrayList<String> get_local_connect() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "netstat -ant");
        Process process = processBuilder.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
        String line;
        ArrayList<String> ipaddr = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            try {
                String[] s = line.split("    ");
                String s1 = s[2];
                Boolean res = Pattern.matches("\\d.*:.*", s1);
                if (res.equals(true)) {
                    String s2 = s1.split(":")[0];
                    ipaddr.add(s2+":"+s1.split(":")[1]);
                }
            } catch (Exception e) {
            }
        }
        return ipaddr;
    }
    public Local_ip_scan() throws IOException {
        ArrayList<String> ipaddr = get_local_connect();
        LinkedHashSet<String> ip2 = new LinkedHashSet<>(ipaddr);
        ArrayList<String> real_ip = new ArrayList<String>(ip2);
        if(real_ip.size()==0){
            System.out.println("\n程序提示:\n没有检测到远程IP的连接,请空闲后重新尝试。");
        }
        task_start(real_ip);
    }
    public void task_start(ArrayList<String> real_ip){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < real_ip.size(); i++) {
            String ip = real_ip.get(i);
            virusTotalScan virusTotalScan = new virusTotalScan();
            virusTotalScan.setIpaddress(ip);
            executorService.execute(virusTotalScan);
        }
        executorService.shutdown();

    }
}
