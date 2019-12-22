package com.wbiao.util;

import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FastdfsClient {
    /**
     * 初始化加载fastdfs中TrackerServer的配置信息
     */
    static {
        try {
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        }catch (Exception e){
            e.printStackTrace();;
        }
    }


    /**
     *  文件上传
     * @param file
     * @return
     * @throws Exception
     */
    public static String[] upload(FastdfsFile file) throws Exception{

        StorageClient storageClient = getStorageClient();
        /**
         *  通过storageClient访问storage，实现文件上传
         *  参数：
         *      1、文件的字节数组
         *      2、文件的扩展名
         *      3、文件的附加信息
         *  返回值：
         *      [0]:组名
         *      [1]:文件名
         */

        String[] uploads = storageClient.upload_file(file.getContent(), file.getExt(), null);
        return  uploads;
    }


    /***
     * 获取文件信息
     * @param groupName:组名
     * @param remoteFileName：文件存储完整名
     * @return
     */
    public static FileInfo getFileInfo(String groupName,String remoteFileName)throws Exception{
        StorageClient storageClient = getStorageClient();
        FileInfo file_info = storageClient.get_file_info(groupName, remoteFileName);
        return file_info;
    }

    /**
     *  文件下载
     * @param groupName 组名
     * @param remoteFileName 文件存储完整名
     * @return
     * @throws Exception
     */
    public static InputStream downloadFile(String groupName, String remoteFileName) throws Exception{
        StorageClient storageClient = getStorageClient();
        byte[] bytes = storageClient.download_file(groupName, remoteFileName);
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        return is;
    }

    /**
     *  文件删除
     * @param groupName 组名
     * @param remoteFileName 文件存储完整名
     * @return
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFileName)throws Exception{
        StorageClient storageClient = getStorageClient();
        int i = storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     *  获取storage信息
     * @throws Exception
     * @return
     */
    public static StorageServer getStorage()throws Exception{
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     *  获取storage组的ip和端口信息
     * @throws Exception
     * @return
     */
    public static StorageServer getStorageInfo(String groupName, String remoteFileName)throws  Exception{
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = trackerClient.getFetchStorage(trackerServer,groupName,remoteFileName);
        return storageServer;
    }

    /**
     *  获取tracker的信息
     * @return
     * @throws Exception
     */
    public static String getTrackerInfo() throws Exception{
        TrackerServer trackerServer = getTrackerServer();
        // tracker的ip和端口
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int port = ClientGlobal.getG_tracker_http_port();
        String url = "http://"+ip+":"+port;
        return url;
    }

    /***
     * 获取Storage客户端
     * @return
     * @throws IOException
     */
    private static StorageClient getStorageClient() throws IOException {
        //获取trackerServer的的连接信息
        TrackerServer trackerServer = getTrackerServer();
        //通过trackerServer的连接信息，获取storage的连接信息，创建storageClient
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return  storageClient;
    }

    /***
     * 获取Tracker
     * @return
     * @throws IOException
     */
    private static TrackerServer getTrackerServer() throws IOException {
        //创建访问tracker的客户端对象
        TrackerClient trackerClient = new TrackerClient();

        //通过trackerClient访问trackerServer，获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();

        return  trackerServer;
    }
}
