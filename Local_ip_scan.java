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
        ArrayList<String> ipaddr = new ArrayList<>();
        ArrayList<String> localip = new ArrayList<>();
        localip.add("127");
        localip.add("0");
        localip.add("192");
        localip.add("10");
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "netstat -ant");
            Process process = processBuilder.start();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
            String line = null;
            while ((line = buffer.readLine()) != null) {
                try {
                    String[] s = line.split("    ");
                    for (int i = 0; i < s.length; i++) {
                        s[i]=s[i].replace(" ","").replace("ESTABLISHED","");
                        if(!localip.contains(s[i].split("\\.")[0])){
                            Boolean res = Pattern.matches("\\d.*:.*", s[i]);
                            if (res.equals(true)) {
                                String s2 = s[i].split(":")[0];
                                ipaddr.add(s2+":"+s[i].split(":")[1]);
                            }
                        }
                    }

                }catch (Exception e){
                }
            }
            buffer.close();
        }catch (Exception e){
            e.printStackTrace();
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
