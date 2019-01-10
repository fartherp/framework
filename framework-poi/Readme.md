# poi框架 framework-poi
> 简单、好用且轻量级的海量excel，csv文件导入导出解决方案。解决火狐浏览器中文编码问题。
> 注：excel的合并功能及复杂功能，使用代码实现比较复杂，框架只提供单行的导入导出。

# 如何使用？
1. 引入Maven依赖或下载jar包

``` xml
    <dependency>
        <groupId>com.github.fartherp</groupId>
        <artifactId>framework-poi</artifactId>
        <version>x.x.x</version>
    </dependency>
```

## CSV常用例子：
1.CSV文件导入：
> 示例代码：com.github.fartherp.framework.poi.csv.CSVReadTest
``` java
    CSVRead<CsvReadDto> csvRead = new CSVRead<>();
    csvRead.read(CSVReadTest.class.getResourceAsStream("/a.csv"), new CSVReadDeal<CsvReadDto>() {
        // 单条数据处理（每一行对应一个javabean）
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
    List<String[]> bodyList = new ArrayList<>();
    CsvUtil.writeCsvFile(filename, title, bodyList);
```

3.浏览器下载CSV文件：
``` java
    HttpServletResponse response = null;
    HttpServletRequest request = null;
    String filename = "TEST";
    String[] title = SheetsTitlesEnum.USER_LOGIN_LOG.getTitle();
    List<String[]> bodyList = new ArrayList<>();
    CsvUtil.writeCsvFile(response, request, filename, title, bodyList);
```

## Excel常用例子：
1.Excel文件导入：
``` java
    ExcelRead<ExcelReadDto> excelRead = new ExcelRead<>();
    excelRead.read(ExcelReadTest.class.getResourceAsStream("/a.xls"), new ExcelReadDeal<ExcelReadDto>() {
        // 单条数据处理（每一行对应一个javabean）
        public ExcelReadDto dealBean(Row row) {
            ExcelReadDto dto = new ExcelReadDto();
            dto.setId(new BigDecimal(row.getCell(0).toString()).longValue());
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
``` java
    String[] title = new String [6];
    title[0] = "登录时间";
    title[1] = "用户名";
    title[2] = "访问端";
    title[3] = "版本系统";
    title[4] = "登录IP";
    title[5] = "状态";
    String fileName = "D:\\style1.xls";
    FileExcelWrite<ExcelDto> excelWrite = new FileExcelWrite<>(title, fileName);
    excelWrite.setLargeDataMode(false).deal(obj -> {
        String[] result = new String[6];
        result[0] = obj.getTime();
        result[1] = obj.getName();
        result[2] = obj.getClient();
        result[3] = obj.getVersion();
        result[4] = obj.getIp();
        result[5] = obj.getStatus() + "";
        return result;
    }).list(ExcelWriteStyleTest.getList())// 默认情况下导出数据达到excel最大行，自动切换sheet，（xlsx=1048576，xls=65536）
            .list(ExcelWriteStyleTest.getList1())
            .write();
```

3.Excel文件导出（风格，可以自定义风格）：
``` java
    Map<String, Object> map = new HashMap<>();
    map.put("quoteCurrency", "ETH");
    map.put("symbol", "USDT_ETH");
    map.put("startTime", "2019-01-09 00:00:00");
    map.put("endTime", "2019-01-09 12:00:00");
    String fileName = "D:\\styleInputStream.xls";
    FileExcelWrite<ExcelDto> excelWrite = new FileExcelWrite<>(this.getClass().getResourceAsStream("/c.xls"), fileName);
    excelWrite.additional(map).deal(new WriteDeal<ExcelDto>() {
        public String[] dealBean(ExcelDto obj) {
            String[] result = new String[3];
            result[0] = obj.getId() + "";
            result[1] = obj.getName();
            result[2] = obj.getAge() + "";
            return result;
        }

        public int skipLine() {
            return 4;
        }
    }).list(getList()).write();
```

4.浏览器下载Excel文件：
``` java
    String[] title = new String [6];
    title[0] = "登录时间";
    title[1] = "用户名";
    title[2] = "访问端";
    title[3] = "版本系统";
    title[4] = "登录IP";
    title[5] = "状态";
    String fileName = "D:\\style1.xls";
    HttpServletResponseExcelWrite<ExcelDto> excelWrite = new HttpServletResponseExcelWrite<>(title, fileName, request, response);
    excelWrite.setLargeDataMode(false).deal(obj -> {
        String[] result = new String[6];
        result[0] = obj.getTime();
        result[1] = obj.getName();
        result[2] = obj.getClient();
        result[3] = obj.getVersion();
        result[4] = obj.getIp();
        result[5] = obj.getStatus() + "";
        return result;
    }).list(ExcelWriteStyleTest.getList())// 默认情况下导出数据达到excel最大行，自动切换sheet，（xlsx=1048576，xls=65536）
            .list(ExcelWriteStyleTest.getList1())
            .write();
```

