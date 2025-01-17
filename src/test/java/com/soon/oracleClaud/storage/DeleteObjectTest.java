package com.soon.oracleClaud.storage;

import com.oracle.bmc.Region;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;

public class DeleteObjectTest {

	public static void main(String[] args) throws Exception {
		
		String namespaceName = "axxquotspscm";  //put your tenancy objectstorage namespace
		String bucketName = "bucket-sdk-demo";      //put your bucket name
        String objectName = "oci-logo5.png";
        
        AuthentificationProvider authentificationProvider = new AuthentificationProvider();
        

        ObjectStorageClient client = new ObjectStorageClient(authentificationProvider.getAuthenticationDetailsProvider());
        client.setRegion(Region.AP_CHUNCHEON_1);
        
		DeleteObjectRequest request = 
        		DeleteObjectRequest.builder()
        			.bucketName(bucketName)
        			.namespaceName(namespaceName)
        			.objectName(objectName)
        			.build();
        		
        
        client.deleteObject(request);
        client.close();
	}
}
