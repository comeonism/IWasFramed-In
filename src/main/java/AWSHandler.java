import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class AWSHandler {
    private final static Logger logger = Logger.getLogger(AWSHandler.class.getName());
    private static AmazonS3 s3Client;

    static void fetch() {
        logger.info("connecting to S3 and setting credentials");

        s3Client = AmazonS3ClientBuilder.standard()
                                        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("", "")))
                                        .withRegion("")
                                        .build();

        ArrayList<String> bucketContents = getBucketContents();

        try {
            saveFiles(bucketContents);
        } catch (IOException ioe) {
            logger.severe("tried saving files, but this happened: ");
            ioe.printStackTrace();
        }
    }

    private static ArrayList<String> getBucketContents() {
        logger.info("getting bucket contents");

        ArrayList<String> dirs = new ArrayList<>();
        ObjectListing objects = s3Client.listObjects(Utils.BUCKET);
        List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();
        objectSummaries.forEach(summary -> dirs.add(summary.getKey()));

        return dirs;
    }

    private static void saveFiles(ArrayList<String> objects) throws IOException {
        logger.info("iterating through all S3 objects and deciding which need storing");

        ArrayList<String> nonExistentDirs = clearExistingObjects(objects);

        for (String dir : nonExistentDirs) {
            S3Object s3Object = s3Client.getObject(Utils.BUCKET, dir);
            File outputFile = new File(Utils.HOME_DIRECTORY + dir);

            if (dir.indexOf("/") == dir.length() - 1) outputFile.mkdir();
            else {
                outputFile.createNewFile();

                try (InputStream objectData = s3Object.getObjectContent();
                     FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                    byte[] bytes = new byte[1024];
                    int read;

                    while ((read = objectData.read(bytes)) != -1) outputStream.write(bytes, 0, read);
                }
            }
        }
    }

    private static ArrayList<String> clearExistingObjects(ArrayList<String> objects) {
        ArrayList<String> newObjects = new ArrayList<>();

        objects.forEach(objectFullName -> {
            String objectName = objectFullName.substring(0, objectFullName.indexOf("/"));
            File localObjectDir = new File(Utils.HOME_DIRECTORY + objectName);
            if (!localObjectDir.exists()) newObjects.add(objectFullName);
        });

        return newObjects;
    }

    static void delete(String dirName) {
        logger.info("deleting objects");
        DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(Utils.BUCKET);
        ArrayList<DeleteObjectsRequest.KeyVersion> keysForDeletion = new ArrayList<>();

        getBucketContents().forEach(bucketObject -> {
            if (bucketObject.contains(dirName)) keysForDeletion.add(new DeleteObjectsRequest.KeyVersion(bucketObject));
        });

        multiObjectDeleteRequest.setKeys(keysForDeletion);

        try {
            DeleteObjectsResult delObjRes = s3Client.deleteObjects(multiObjectDeleteRequest);
            System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());
        } catch (MultiObjectDeleteException e) {
            logger.severe("could not delete one or more objects because of this: ");
            e.printStackTrace();
        }
    }
}
