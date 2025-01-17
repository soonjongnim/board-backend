package com.soon.oracleClaud.storage;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import com.oracle.bmc.Region;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.transfer.UploadConfiguration;
import com.oracle.bmc.objectstorage.transfer.UploadManager;
import com.oracle.bmc.objectstorage.transfer.UploadManager.UploadRequest;
import com.oracle.bmc.objectstorage.transfer.UploadManager.UploadResponse;

public class UploadObjectExampleWithAuthProv {
    public static void main(String[] args) throws Exception {

        String namespaceName = "axxquotspscm";  //put your tenancy objectstorage namespace
        String bucketName = "bucket-sdk-demo";      //put your bucket name
        String objectName = "oci-logo5.png";
        Map<String, String> metadata = null;
        String contentType = "image/png";
        String contentEncoding = null;
        String contentLanguage = null;
        File body = new File("D:\\react_springBoot_mysql_aws/board/fileUpload/oci-logo.png");       //put your local file path
        
        AuthentificationProvider authentificationProvider = new AuthentificationProvider();
        

        ObjectStorageClient client = new ObjectStorageClient(authentificationProvider.getAuthenticationDetailsProvider());
        client.setRegion(Region.AP_CHUNCHEON_1);

        // configure upload settings as desired
        UploadConfiguration uploadConfiguration =
                UploadConfiguration.builder()
                        .allowMultipartUploads(true)
                        .allowParallelUploads(true)
                        .build();

        UploadManager uploadManager = new UploadManager(client, uploadConfiguration);

        PutObjectRequest request =
                PutObjectRequest.builder()
                        .bucketName(bucketName)
                        .namespaceName(namespaceName)
                        .objectName(objectName)
                        .contentType(contentType)
                        .contentLanguage(contentLanguage)
                        .contentEncoding(contentEncoding)
                        .opcMeta(metadata)
                        .build();

        UploadRequest uploadDetails =
                UploadRequest.builder(body).allowOverwrite(true).build(request);

        // upload request and print result
        // if multi-part is used, and any part fails, the entire upload fails and will throw BmcException
        UploadResponse response = uploadManager.upload(uploadDetails);
        System.out.println(response);

        // fetch the object just uploaded
        GetObjectResponse getResponse =
                client.getObject(
                        GetObjectRequest.builder()
                                .namespaceName(namespaceName)
                                .bucketName(bucketName)
                                .objectName(objectName)
                                .build());

        // use the response's function to print the fetched object's metadata
        System.out.println(getResponse.getOpcMeta());

        // stream contents should match the file uploaded
        try (final InputStream fileStream = getResponse.getInputStream()) {
        	System.out.println("fileStream: " + fileStream);
            // use fileStream
        } // try-with-resources automatically closes fileStream
        
        client.close();
    }
}
