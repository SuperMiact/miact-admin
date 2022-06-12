package cn.miact.service.impl;

import cn.miact.domain.common.PageQuery;
import cn.miact.domain.common.PageResult;
import cn.miact.domain.dto.UserDTO;
import cn.miact.domain.dto.UserExportDTO;
import cn.miact.domain.dto.UserQueryDTO;
import cn.miact.service.ExcelExportService;
import cn.miact.service.FileService;
import cn.miact.service.UserService;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Excel导出服务实现类
 */
@Service
@Slf4j
public class ExcelExportServiceImpl implements ExcelExportService {

    @Resource(name = "localFileServiceImpl")
    private FileService fileService;

    @Autowired
    private UserService userService;

    /**
     * 执行数据库查询和Excel导出，将数据写入到outputStream中
     * @param outputStream
     * @param query
     */
    private void export(OutputStream outputStream, UserQueryDTO query){
        // step1. 需要创建一个EasyExcel导出对象
        ExcelWriter excelWriter = EasyExcelFactory.write(
                        outputStream,
                        UserExportDTO.class)
                .build();

        // step2. 分批加载数据
        PageQuery<UserQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setQuery(query);
        pageQuery.setPageSize(2);

        int pageNo = 0;
        PageResult<List<UserDTO>> pageResult;

        do {
            // 先累加 再赋值 要跟pageNo++区分
            pageQuery.setPageNo(++pageNo);

            log.info("开始导出第[{}]页数据！",pageNo);

            pageResult = userService.query(pageQuery);

            // 数据转换: UserDTO 转换成 UserExportDTO
            List<UserExportDTO> userExportDTOList = Optional.ofNullable(pageResult.getData())
                    .map(List::stream)
                    .orElseGet(Stream::empty)
                    .map(userDTO -> {
                        UserExportDTO userExportDTO = new UserExportDTO();

                        // 转换
                        BeanUtils.copyProperties(userDTO,userExportDTO);
                        return userExportDTO;
                    })
                    .collect(Collectors.toList());

            // step3. 导出分批加载的数据
            // 将数据写入到不同的sheet页中
            WriteSheet writeSheet = EasyExcelFactory.writerSheet(pageNo,
                    "第" + pageNo + "页").build();

            excelWriter.write(userExportDTOList,writeSheet);

            log.info("结束导出第[{}]页数据！",pageNo);

            // 总页数 大于 当前页数 说明还有数据，需要再次执行
        } while (pageResult.getPageNum() > pageNo);


        // step4. 收尾，执行finish，才会关闭Excel文件流
        excelWriter.finish();

        log.info("完成导出！");
    }

    @Override
    public void export(UserQueryDTO query, String filename) {

        // 输出流
        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        // step1. 实现数据导出到Excel中
        export(outputStream,query);

        // 输入流
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        // step2. 实现文件上传
        fileService.upload(inputStream,filename);
    }

    /**
     * @Async 标注，使用线程池执行方法
     * @param query
     * @param filename
     */
    @Async("exportServiceExecutor")
    @Override
    public void asyncExport(UserQueryDTO query, String filename) {
        export(query,filename);

    }

}
