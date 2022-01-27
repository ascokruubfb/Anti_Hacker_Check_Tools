

public class Banner_menu {
    public static void start_menu_english() {
        System.out.println("------------------------------------------------");
        System.out.println("Author:Wineme 2022 1.24 say:NO SECURITY SYSTEM");
        System.out.println("This is a anti malicious IP tools.");
        System.out.println("You can run the log to check the connected IP security.");
        System.out.println("-h ip address");
        System.out.println("-l input file path for more ip check");
        System.out.println("-lc local connected ip check");
        System.out.println("------------------------------------------------");
    }

    public static void start_menu_chinese() {
        System.out.println("------------------------------------------------");
        System.out.println("作者:Wineme 2022 1.24 IP安全性检测工具");
        System.out.println("这是一款IP连接安全性分析的工具");
        System.out.println("你可以查看你本机的所有IP进行分析");
        System.out.println("-h 分析IP");
        System.out.println("-l 通过一个文件批量分析IP");
        System.out.println("-lc 本地自检连接IP");
        System.out.println("-log 指定是否输出报告 默认不输出");
        System.out.println("------------------------------------------------");
    }

    public static void log_Is_Output(String[] args){

        switch (args.length){
            case 3:
                if(args[0 + 2].equals("-log")){
                    S_Config s_config = new S_Config();
                    s_config.setWrite_log(1);
                }
                break;
            case 2:
                if(args[0 + 1].equals("-log")){
                    S_Config s_config = new S_Config();
                    s_config.setWrite_log(1);
                }
                break;

        }

    }
}
