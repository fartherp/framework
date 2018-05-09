# poi框架 framework-poi
> 简单、好用且轻量级的海量excel，csv文件导入导出解决方案。
> 注：excel的合并功能及复杂功能，使用代码实现比较复杂，框架只提供单行的导入导出。

# 如何使用？
1. 引入Maven依赖或下载jar包

``` xml
    <dependency>
        <groupId>com.github.fartherp</groupId>
        <artifactId>framework-common</artifactId>
        <version>x.x.x</version>
    </dependency>
```

## CSV常用例子：
1.CSV文件导入：
> 示例代码：com.github.fartherp.framework.poi.csv.CSVReadTest
``` java
    CSVRead<CsvReadDto> excelRead = new CSVRead<CsvReadDto>();
    excelRead.read(CSVReadTest.class.getResourceAsStream("/a.csv"), new DefaultCSVReaderDeal<CsvReadDto>() {
        // 单条数据处理（每个excel一行对应一个javabean）
        public CsvReadDto dealBean(String[] arr) {
            CsvReadDto dto = new CsvReadDto();
            dto.setId(Long.valueOf(arr[0]));
            dto.setName(arr[1]);
            dto.setAge(Integer.valueOf(arr[2]));
            return dto;
        }

        // 批量数据处理（可以批量入库）
        public void dealBatchBean(List<CsvReadDto> list) {
            Assert.assertEquals("name1", list.get(0).getName());
            Assert.assertEquals("name2", list.get(1).getName());
            Assert.assertEquals("name3", list.get(2).getName());
        }

        // 批量加载多少数据，统一处理（默认1000）
        public int getBatchCount() {
            return super.getBatchCount();
        }

        // 从第几行开始加载（默认跳过第一行）
        public int skipLine() {
            return super.skipLine();
        }
    });
```

2.CSV文件导出：
``` java
    String filename = "TEST";
    String[] title = SheetsTitlesEnum.USER_LOGIN_LOG.getTitle();
    // 导出数据
    List<String[]> bodyList = new ArrayList<String[]>();
    CsvUtil.writeCsvFile(filename, title, bodyList);
```

3.浏览器下载CSV文件：
``` java
    HttpServletResponse response = null;
    HttpServletRequest request = null;
    String filename = "TEST";
    String[] title = SheetsTitlesEnum.USER_LOGIN_LOG.getTitle();
    // 导出数据
    List<String[]> bodyList = new ArrayList<String[]>();
    CsvUtil.writeCsvFile(response, request, filename, title, bodyList);
```

## Excel常用例子：
1.Excel文件导入：
> 示例代码：com.github.fartherp.framework.poi.excel.read.ExcelReadTest
``` java
    // 需要导入类的泛型
    ExcelRead<ExcelReadDto> excelRead = new ExcelRead<ExcelReadDto>();
    excelRead.read(ExcelReadTest.class.getResourceAsStream("/a.xls"), "a.xls", new DefaultExcelReadDeal<ExcelReadDto>() {
        // 单条数据处理（每个excel一行对应一个javabean）
        public ExcelReadDto dealBean(Row row) {
            ExcelReadDto dto = new ExcelReadDto();
            dto.setId(Long.valueOf(row.getCell(0).toString()));
            dto.setName(row.getCell(1).toString());
            dto.setAge(Integer.valueOf(row.getCell(2).toString()));
            return dto;
        }

        // 批量数据处理（可以批量入库）
        public void dealBatchBean(List<ExcelReadDto> list) {
            Assert.assertEquals("name1", list.get(0).getName());
            Assert.assertEquals("name2", list.get(1).getName());
            Assert.assertEquals("name3", list.get(2).getName());
        }

        // 批量加载多少数据，统一处理（默认1000）
        public int getBatchCount() {
            return super.getBatchCount();
        }

        // 从第几行开始加载（默认跳过第一行）
        public int skipLine() {
            return super.skipLine();
        }
    });
```

2.Excel文件导出：
> 示例代码：com.github.fartherp.framework.poi.excel.write.FileExcelWriteTest
``` java
    String[] title = new String [6];
    title[0] = "登录时间";
    title[1] = "用户名";
    title[2] = "访问端";
    title[3] = "版本系统";
    title[4] = "登录IP";
    title[5] = "状态";
    String fileName = "D:\\style1.xls";
    FileExcelWrite<ExcelDto> excelDtoExcelWrite = new FileExcelWrite<ExcelDto>(title, fileName);
    excelDtoExcelWrite.deal(
            new ExcelWriteStyle.DefaultWriteDeal<ExcelDto>() {
                // 每个javabean转换成对应excel一行记录
                public String[] dealBean(ExcelDto obj) {
                    String[] result = new String[6];
                    result[0] = obj.getTime();
                    result[1] = obj.getName();
                    result[2] = obj.getClient();
                    result[3] = obj.getVersion();
                    result[4] = obj.getIp();
                    result[5] = obj.getStatus() + "";
                    return result;
                }
            });
    // 默认情况下导出数据达到excel最大行，自动切换sheet，（xlsx=1048576，xls=65536）
    excelDtoExcelWrite.list(ExcelWriteStyleTest.getList())
            .list(ExcelWriteStyleTest.getList1())
            .write();
```

3.Excel文件导出（风格，可以自定义风格）：
> 示例代码：com.github.fartherp.framework.poi.excel.write.ExcelWriteStyleTest
``` java
    String[] title = new String [6];
    title[0] = "登录时间";
    title[1] = "用户名";
    title[2] = "访问端";
    title[3] = "版本系统";
    title[4] = "登录IP";
    title[5] = "状态";
    String head = "用户登录日志";
    String fileName = "D:\\style.xls";
    String condition = "用户类型：投资用户    登录时间：XXXX-XX-XX至XXXX-XX-XX     查询条件：XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    FileExcelWrite<ExcelDto> excelDtoExcelWrite = new FileExcelWrite<ExcelDto>(title, fileName);
    ExcelWriteStyle<ExcelDto> writeStyle = new ExcelWriteStyle<ExcelDto>(excelDtoExcelWrite);
    writeStyle.condition(condition).head(head).deal(
            new ExcelWriteStyle.DefaultWriteDeal<ExcelDto>() {
                public String[] dealBean(ExcelDto obj) {
                    String[] result = new String[6];
                    result[0] = obj.getTime();
                    result[1] = obj.getName();
                    result[2] = obj.getClient();
                    result[3] = obj.getVersion();
                    result[4] = obj.getIp();
                    result[5] = obj.getStatus() + "";
                    return result;
                }
            });
    writeStyle.list(ExcelWriteStyleTest.getList())
            .list(ExcelWriteStyleTest.getList1())
            .write();
```

4.浏览器下载Excel文件：
> 示例代码：com.github.fartherp.framework.poi.excel.write.SheetsTitlesEnum
``` java
    String header = SheetsTitlesEnum.USER_LOGIN_LOG.getHead();
    String[] title = SheetsTitlesEnum.USER_LOGIN_LOG.getTitle();
    String condition = new StringBuilder()
            .append("用户类型：").append(OrgEnum.getOrg(orgId).getDesc())
            .append(" 登录时间：").append(StringUtils.trimToEmpty(startTime)).append(" 至 ")
            .append(StringUtils.trimToEmpty(endTime))
            .append(" 查询条件：").append(StringUtils.trimToEmpty(loginName)).toString();
    String fileName = "系统登录记录.xlsx";

    // 需要导出的数据
    List<LogLogin> loginLogList = null;

    HttpServletResponseExcelWrite excelWrite = new HttpServletResponseExcelWrite(title, fileName, request, response);
    ExcelWriteStyle<LogLogin> writeStyle = new ExcelWriteStyle<LogLogin>(excelWrite);
    writeStyle.head(header).condition(condition).deal(new ExcelWriteStyle.DefaultWriteDeal<LogLogin>() {
        @Override
        public String[] dealBean(LogLogin userLoginLog) {
            String[] result = new String[5];
            result[0] = DateUtil.format(DateUtil.yyyy_MM_dd_HH_mm_ss, userLoginLog.getCreateTime());
            result[1] = userLoginLog.getLoginName();
            result[2] = userLoginLog.getOs();
            result[3] = userLoginLog.getIp();
            result[4] = UserLoginLogStatusEnum.getStatus(userLoginLog.getStatus()).getDesc();
            return result;
        }
    }).list(loginLogList).write();
```

