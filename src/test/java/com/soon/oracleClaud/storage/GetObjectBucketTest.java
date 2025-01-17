package com.soon.oracleClaud.storage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.oracle.bmc.Region;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.ListObjects;
import com.oracle.bmc.objectstorage.model.ObjectSummary;
import com.oracle.bmc.objectstorage.requests.GetBucketRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.ListObjectsRequest;
import com.oracle.bmc.objectstorage.responses.GetBucketResponse;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.responses.ListObjectsResponse;

public class GetObjectBucketTest {

	public static void main(String[] args) throws Exception {
		
		String namespaceName = "axxquotspscm";  //put your tenancy objectstorage namespace
		String bucketName = "bucket-sdk-demo";      //put your bucket name
        String objectName = "oci-logo5.png";
        
        AuthentificationProvider authentificationProvider = new AuthentificationProvider();
        

        ObjectStorageClient client = new ObjectStorageClient(authentificationProvider.getAuthenticationDetailsProvider());
        client.setRegion(Region.AP_CHUNCHEON_1);
     
        List<GetBucketRequest.Fields> fieldsList = new ArrayList<>(2);
        fieldsList.add(GetBucketRequest.Fields.ApproximateCount);
        fieldsList.add(GetBucketRequest.Fields.ApproximateSize);
        
        GetBucketRequest request =
                GetBucketRequest.builder()
                        .namespaceName(namespaceName)
                        .bucketName(bucketName)
                        .fields(fieldsList)
                        .build();

        System.out.println("Fetching bucket details");
        GetBucketResponse response = client.getBucket(request);

        System.out.println("Get Bucket : " + response.getBucket());
        System.out.println("Bucket Name : " + response.getBucket().getName());
        System.out.println("Bucket Compartment : " + response.getBucket().getCompartmentId());
        System.out.println(
                "The Approximate total number of objects within this bucket : "
                        + response.getBucket().getApproximateCount());
        System.out.println(
                "The Approximate total size of objects within this bucket : "
                        + response.getBucket().getApproximateSize());
        
        client.close();
	}
}
