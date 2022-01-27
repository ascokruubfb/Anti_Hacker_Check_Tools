import org.json.JSONException;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Main {

    public static void virusTotalScan(String ipaddress) throws JSONException {
        virusTotalScan virusTotalScan = new virusTotalScan();
        virusTotalScan.setIpaddress(ipaddress);
        virusTotalScan.run();
    }

    public static void task_Vt_Scan(String name) throws IOException, JSONException {
        FIO_Utils fio_utils = new FIO_Utils();
        String str = fio_utils.IO_Read(name);
        String[] ips = (String[]) str.split("\n");
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (String ip : ips) {
            virusTotalScan virusTotalScan = new virusTotalScan();
            virusTotalScan.setIpaddress(ip);
            executorService.execute(virusTotalScan);
        }
        executorService.shutdown();

    }

    public static void check_Local_Ip() throws IOException, JSONException {
        Local_ip_scan local_ip_scan = new Local_ip_scan();
    }



    public static void main(String[] args) throws JSONException, IOException {

        String info = "IP检测数据来源官方VirusTotal\nIP check report data from VirusTotal\n作者:Wineme Say:没有最安全的系统,只有不努力的屌丝.\n程序正在连接VT API.........\n";
        String error="选项错误，请输入正确的选项。\noptions error,please input right options.";
        Locale system_la = Locale.getDefault();
        if (system_la.getLanguage().equals("zh")) {
            if (args.length == 0) {
                Banner_menu.start_menu_chinese();
            } else {
                switch (args[0]) {
                    case "-help":
                        if (system_la.getLanguage().equals("zh")) {
                            Banner_menu.start_menu_chinese();
                        } else {
                            Banner_menu.start_menu_english();
                        }
                        break;
                    case "-h":
                        Banner_menu.log_Is_Output(args);
                        System.out.println(info);
                        try {
                            virusTotalScan(args[0 + 1]);
                        }catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("没有检测到输入值。");
                        }


                        break;
                    case "-l":
                        Banner_menu.log_Is_Output(args);
                        System.out.println(info);
                        try {
                            task_Vt_Scan(args[0 + 1]);
                        }catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("没有检测到输入值。");
                        }

                        break;
                    case "-lc":
                        Banner_menu.log_Is_Output(args);
                        System.out.println(info+"通常没有其他服务情况下连接的远程端口是443 或 80端口\n");
                        check_Local_Ip();
                        break;

                    default:
                            System.out.println(error);
                        break;
                }

            }
        } else {
            Banner_menu.start_menu_english();
        }


    }
}