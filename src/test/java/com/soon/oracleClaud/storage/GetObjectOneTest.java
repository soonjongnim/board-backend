package com.soon.oracleClaud.storage;

import java.io.InputStream;

import com.oracle.bmc.Region;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;

public class GetObjectOneTest {

	public static void main(String[] args) throws Exception {
		
		String namespaceName = "axxquotspscm";  //put your tenancy objectstorage namespace
		String bucketName = "bucket-sdk-demo";      //put your bucket name
        String objectName = "oci-logo5.png";
        
        AuthentificationProvider authentificationProvider = new AuthentificationProvider();
        

        ObjectStorageClient client = new ObjectStorageClient(authentificationProvider.getAuthenticationDetailsProvider());
        client.setRegion(Region.AP_CHUNCHEON_1);
     
        GetObjectRequest request =
        		GetObjectRequest.builder()
        			.namespaceName(namespaceName)
        			.bucketName(bucketName)
        			.objectName(objectName)
        			.build();
        
        GetObjectResponse response = client.getObject(request);
        System.out.println(response);
//        InputStream fileStream = response.getInputStream();
        
        try (final InputStream fileStream = response.getInputStream()) {
        	System.out.println("fileStream: " + fileStream.toString());
//        	fileStream.close();
            // use fileStream
        } // try-with-resources automatically closes fileStream
        
        client.close();
	}
}
