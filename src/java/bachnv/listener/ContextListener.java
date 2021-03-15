    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.listener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author ngvba
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        Map<String, String> indexPage = new HashMap<>();
        String filename = sc.getRealPath("/WEB-INF/IndexPage.txt");
        try {
            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {                
                String[] s = line.split("=");
                String key = s[0];
                String value = s[1];
                indexPage.put(key, value);
            }
            br.close();
            isr.close();
            fis.close();
            sc.setAttribute("INDEXPAGE", indexPage);
        } catch (IOException ex) {
            sce.getServletContext().log("ContextListenner _ IO " + ex.getMessage());
        } 
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
