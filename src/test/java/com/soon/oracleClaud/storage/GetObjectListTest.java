package com.soon.oracleClaud.storage;

import java.util.List;

import com.oracle.bmc.Region;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.ListObjects;
import com.oracle.bmc.objectstorage.model.ObjectSummary;
import com.oracle.bmc.objectstorage.requests.ListObjectsRequest;
import com.oracle.bmc.objectstorage.responses.ListObjectsResponse;

public class GetObjectListTest {

	public static void main(String[] args) throws Exception {
		
		String namespaceName = "axxquotspscm";  //put your tenancy objectstorage namespace
		String bucketName = "bucket-sdk-demo";      //put your bucket name
        String objectName = "oci-logo5.png";
        
        AuthentificationProvider authentificationProvider = new AuthentificationProvider();
        

        ObjectStorageClient client = new ObjectStorageClient(authentificationProvider.getAuthenticationDetailsProvider());
        client.setRegion(Region.AP_CHUNCHEON_1);
        
        ListObjectsRequest request =
        		ListObjectsRequest.builder()
        			.namespaceName(namespaceName)
        			.bucketName(bucketName)
        			.fields("size, md5, timeCreated, timeModified")
                    .prefix("1a330ae5-eb70-44af-ba0f-a609b0436245.PNG")
        			.build();
        
        System.out.println("request: " + request);
        ListObjectsResponse response = client.listObjects(request);
        System.out.println("response: " + response);
        ListObjects list = response.getListObjects();
        System.out.println("list: " + list);
        List<ObjectSummary> objectList = list.getObjects();
        System.out.println("objectList: " + objectList);
        
        
        for(int i=0; i<objectList.size(); i++) {
        	System.out.println("====================");
        	System.out.println("@@@@@@@@@@@@@@@@@ getName : " + objectList.get(i).getName());
        	System.out.println("@@@@@@@@@@@@@@@@@ getArchivalState : " + objectList.get(i).getArchivalState());
        	System.out.println("@@@@@@@@@@@@@@@@@ getSize : " + objectList.get(i).getSize());
        	System.out.println("@@@@@@@@@@@@@@@@@ getTimeCreated : " + objectList.get(i).getTimeCreated());
        	System.out.println("@@@@@@@@@@@@@@@@@ getTimeModified : " + objectList.get(i).getTimeModified());
        }
        
        client.close();
	}
}
