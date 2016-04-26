/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsu.cmdsocket.client.config;

import by.bsu.cmdsocket.server.config.ServerProperties;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Антон
 */
public class ClientProperties {
    
    private static final Logger LOGGER = Logger.getLogger(ClientProperties.class.getName());
    public static final String PROPERTIES_FILE = "client_config.properties";
    private static final Properties PROPERTIES = new Properties();
    
    static {
        try (InputStream is = ServerProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            PROPERTIES.load(is);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not load client properties file: " + ServerProperties.class.getResource(PROPERTIES_FILE).toString(), e);
        }
    }
    
    public ClientProperties() {
        
    }
    
    public static String getProperty(Property prop, String defaultValue) {
        return PROPERTIES.getProperty(prop.toString(), defaultValue);
    }
    
    public static String getProperty(Property prop) {
        return PROPERTIES.getProperty(prop.toString());
    }
    
    public static String getServerHost() {
        return getProperty(Property.SERVER_HOST, "localhost");
    }
    
    public static Integer getServerPort() {
        String portString = getProperty(Property.SERVER_PORT, "8080");
        return Integer.valueOf(portString);
    }
    
    public static enum Property {

        SERVER_HOST("serverHost"),
        SERVER_PORT("serverPort");

        private final String key;

        private Property(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return key;
        }
    }
}
