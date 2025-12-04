/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.Properties;

public class MavenWrapperDownloader {

    private static final String WRAPPER_VERSION = "3.1.0";

    /**
     * Default constructor.
     */
    public MavenWrapperDownloader() {
    }

    /**
     * Downloads the maven-wrapper.jar from the remote repository.
     *
     * @throws Exception
     */
    public static void main(String[] args)
        throws Exception {
        System.out.println("- Downloading maven-wrapper.jar from remote repository.");

        Properties mavenWrapperProperties = readMavenWrapperProperties();

        String jarUrl = mavenWrapperProperties.getProperty("wrapperUrl", "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/"
            + WRAPPER_VERSION + "/maven-wrapper-" + WRAPPER_VERSION + ".jar");
        String jarPath = System.getProperty("user.home") + "/.m2/repository/org/apache/maven/wrapper/maven-wrapper/" + WRAPPER_VERSION + "/maven-wrapper-" + WRAPPER_VERSION
            + ".jar";

        tryDownloadFile(new URL(jarUrl), new File(jarPath));

        System.out.println("- Downloading maven-wrapper.jar to " + jarPath);
    }

    private static Properties readMavenWrapperProperties()
        throws IOException {
        String userHome = System.getProperty("user.home");
        File mavenWrapperPropertyFile = new File(userHome, ".m2/repository/org/apache/maven/wrapper/maven-wrapper.properties");

        Properties mavenWrapperProperties = new Properties();
        if ( mavenWrapperPropertyFile.exists() ) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(mavenWrapperPropertyFile);
                mavenWrapperProperties.load(fileInputStream);
            }
            catch (IOException e) {
                System.err.println("- ERROR loading '" + mavenWrapperPropertyFile + "'");
            }
            finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    }
                    catch (IOException e) {
                        // ignore
                    }
                }
            }
        }
        return mavenWrapperProperties;
    }

    protected static void tryDownloadFile(URL fileUrl, File file)
        throws Exception {
        if (!isWindows()) {
            tryDownloadFileUnix(fileUrl, file);
        } else {
            tryDownloadFileWin(fileUrl, file);
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private static void tryDownloadFileWin(URL fileUrl, File file)
        throws Exception {
        if (file.exists()) {
            System.out.println("- File already exists: " + file.getAbsolutePath());
            return;
        }

        File file2 = new File(file.getParentFile(), file.getName() + ".tmp");
        try {
            downloadFile(fileUrl, file2);
            if (!file2.renameTo(file)) {
                copyFile(file2, file);
                file2.delete();
            }
        } catch (Exception e) {
            if (file2.exists()) {
                file2.delete();
            }
            throw e;
        }
    }

    private static void tryDownloadFileUnix(URL fileUrl, File file)
        throws Exception {
        if (file.exists()) {
            System.out.println("- File already exists: " + file.getAbsolutePath());
            return;
        }
        File file2 = new File(file.getParentFile(), file.getName() + ".tmp");
        try {
            downloadFile(fileUrl, file2);
            if (!file2.setExecutable(true) || !file2.renameTo(file)) {
                copyFile(file2, file);
                file2.delete();
            }
        } catch (Exception e) {
            if (file2.exists()) {
                file2.delete();
            }
            throw e;
        }
    }

    private static void downloadFile(URL fileUrl, File file)
        throws Exception {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        System.out.println("- Downloading to: " + file.getAbsolutePath());
        URLConnection conn = fileUrl.openConnection();
        InputStream inStream = conn.getInputStream();
        FileOutputStream outStream = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        int bytes_read;
        while ((bytes_read = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytes_read);
        }
        inStream.close();
        outStream.close();
    }

    private static void copyFile(File srcFile, File destFile)
        throws IOException {
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }
        fis.close();
        fos.close();
    }
}
