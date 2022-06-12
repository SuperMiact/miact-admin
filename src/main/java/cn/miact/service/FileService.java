package cn.miact.service;

import java.io.File;
import java.io.InputStream;

/**
 * 文件上传服务接口
 */
public interface FileService {
    /**
     * 文件上传
     */
    void upload(InputStream inputStream,String filename);

    /**
     * 文件上传
     */
    void upload(File file);
}
