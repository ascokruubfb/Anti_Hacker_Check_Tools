import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;


public class virusTotalScan implements Runnable{
    String port="";
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;

        if(ipaddress.split(":").length==2){
            this.ipaddress=ipaddress.split(":")[0];
            port=ipaddress.split(":")[1];
        }

    }

    String ipaddress;

    public void start_Vtscan() throws JSONException {
        HashMap<String, String> request_headers = new HashMap<>();
        request_headers.put("x-apikey", "YOUR KEY");
        String url = "https://www.virustotal.com/api/v3/ip_addresses/" + ipaddress;
        String req = HttpRequestUtil.sendGet(url, "", request_headers);
        JSONObject jsonObject = new JSONObject(req);
        JSONObject attributes = jsonObject.getJSONObject("data").getJSONObject("attributes");
        JSONObject security_company = attributes.getJSONObject("last_analysis_results");
        JSONArray company_names = security_company.names();
        ArrayList<String> m_s_company = new ArrayList<>();
        int s_c_count = company_names.length();
        int malware_count = 0;
        for (int i = 0; i < s_c_count; i++) {
            String company_name = (String) company_names.get(i);
            JSONObject jsonObject1 = security_company.getJSONObject(company_name);
            if (jsonObject1.get("result").equals("malware") || jsonObject1.get("result").equals("malicious")) {
                malware_count = malware_count + 1;
                m_s_company.add(company_name);
            }
        }

        String log = "";
        if(!port.equals("")){
            ipaddress=ipaddress+":"+port;
        }
        if (m_s_company.size() == 0) {
            log += "IP:" + ipaddress + "\nSecurity | 安全的连接";
            log += "\n------------------------";
            System.out.println(log);
        } else {

            log += "IP:" + ipaddress + "\n" + "Unsafe Waining:" + s_c_count + "/" + malware_count + " 不保证安全性\n报毒安全引擎:\n";
            for (int i = 0; i < m_s_company.size(); i++) {
                log += "name:" + m_s_company.get(i) + "\n";
            }
            log += "------------------------";
            System.out.println(log);
        }
        S_Config s_config = new S_Config();
        if(s_config.getWrite_log().equals(1)){
            FIO_Utils fio_utils = new FIO_Utils();
            fio_utils.IO_Write("log.txt",log,true);
        }



    }

    @Override
    public void run() {
        try {
            start_Vtscan();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
