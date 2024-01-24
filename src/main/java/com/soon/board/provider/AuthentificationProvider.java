package com.soon.board.provider;

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
//        URL resourceUrl = classLoader.getResource("config");
        InputStream inputStream = classLoader.getResourceAsStream("BOOT-INF/classes/config");

//        if (resourceUrl != null) {
//            String filePath = new File(resourceUrl.getFile()).getPath();
//            System.out.println("File Path: " + filePath);
//        } else {
//            System.err.println("Resource not found");
//        }
        
        if (inputStream != null) {
            // Now you can read from the InputStream as needed
            // For example, you can use a BufferedReader to read text lines:
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line as needed
            	System.out.println("line: " + line);
            }
            reader.close();
        } else {
            System.err.println("Resource not found");
        }
        File tempConfigFile = new File(classLoader.getResource("config").getFile());
        File tempOCIAPIKey = new File(classLoader.getResource("oci_api_key.pem").getFile());

        ConfigFile config = ConfigFileReader.parse(tempConfigFile.getPath(), "DEFAULT");

        Supplier<InputStream> privateKeySupplier = new SimplePrivateKeySupplier(tempOCIAPIKey.getPath());

        AuthenticationDetailsProvider provider = SimpleAuthenticationDetailsProvider.builder()
                .tenantId(config.get("tenancy")).userId(config.get("user")).fingerprint(config.get("fingerprint"))
                .privateKeySupplier(privateKeySupplier).region(Region.AP_CHUNCHEON_1).build();

        return provider;
    }

}