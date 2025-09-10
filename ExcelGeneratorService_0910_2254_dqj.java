// 代码生成时间: 2025-09-10 22:54:51
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelGeneratorService {

    /**
# 优化算法效率
     * 生成Excel表格
     * @param input Excel模板文件
# 改进用户体验
     * @param data 需要写入的数据
# 扩展功能模块
     * @param outputStream 输出流，用于写入生成的Excel文件
     * @throws IOException 如果处理Excel文件时发生IO异常
     */
    public void generateExcel(MultipartFile input, List<List<Object>> data, OutputStream outputStream) throws IOException {
        // 检查输入文件是否为空
        if (input.isEmpty()) {
            throw new IllegalArgumentException(