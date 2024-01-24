package com.soon.board.provider;

import java.util.Properties;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.ConfigFileReader.ConfigFile;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimplePrivateKeySupplier;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class AuthentificationProvider {

    public AuthenticationDetailsProvider getAuthenticationDetailsProvider() throws IOException {

        ClassLoader classLoader = AuthentificationProvider.class.getClassLoader();
        InputStream tempConfigInputStream = classLoader.getResourceAsStream("BOOT-INF/classes/config"); // server용 클래스패스
//        InputStream tempOCIAPIKeyInputStream = classLoader.getResourceAsStream("BOOT-INF/classes/oci_api_key.pem"); // server용 클래스패스
//
        Properties properties = new Properties();
        if (tempConfigInputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(tempConfigInputStream))) {
                // Load the properties
                properties.load(reader);

                System.out.println("User: " + properties.getProperty("user"));
                System.out.println("Fingerprint: " + properties.getProperty("fingerprint"));
                System.out.println("Tenancy: " + properties.getProperty("tenancy"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Resource not found");
        }
//        File tempConfigFile = new File(classLoader.getResource("config").getFile());
//        File tempOCIAPIKey = new File(classLoader.getResource("oci_api_key.pem").getFile());

//        ConfigFile config = ConfigFileReader.parse(tempConfigFile.getPath(), "DEFAULT");
//        System.out.println("tempOCIAPIKey.getPath(): " + tempOCIAPIKey.getPath());
        Supplier<InputStream> privateKeySupplier = new SimplePrivateKeySupplier("BOOT-INF/classes/oci_api_key.pem");

        AuthenticationDetailsProvider provider = SimpleAuthenticationDetailsProvider.builder()
                .tenantId(properties.getProperty("tenancy")).userId(properties.getProperty("user")).fingerprint(properties.getProperty("fingerprint"))
                .privateKeySupplier(privateKeySupplier).region(Region.AP_CHUNCHEON_1).build();

        return provider;
    }

}