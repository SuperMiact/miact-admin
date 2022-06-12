package cn.miact.service;

import cn.miact.domain.dto.UserQueryDTO;

/**
 * Excel导出服务接口
 */
public interface ExcelExportService {

    /**
     * 同步导出服务
     * @param query
     * @param filename
     */
    void export(UserQueryDTO query,String filename);

    /**
     * 异步导出服务
     * @param query
     * @param filename
     */
    void asyncExport(UserQueryDTO query,String filename);

}
