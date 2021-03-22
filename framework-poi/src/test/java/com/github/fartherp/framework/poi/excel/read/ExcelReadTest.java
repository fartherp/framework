/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.poi.excel.read;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReadTest {

    @Test
    public void testRead() {
        // a.xls/a.xlsx
        ExcelRead.read(ExcelReadTest.class.getResourceAsStream("/a.xls"), new ExcelReadDeal<ExcelReadDto>() {
            // 单条数据处理（每个excel一行对应一个对象）
            @Override
			public ExcelReadDto dealBean(RowDelegate delegate) {
                ExcelReadDto dto = new ExcelReadDto();
				delegate.getString(0).map(BigDecimal::new).map(BigDecimal::longValue).ifPresent(dto::setId);
				delegate.getString(1).ifPresent(dto::setName);
				delegate.getString(2).map(Integer::valueOf).ifPresent(dto::setAge);
                return dto;
            }

            // 批量数据处理（可以批量入库）
            @Override
			public void dealBatchBean(List<ExcelReadDto> list) {
                Assert.assertEquals("name1", list.get(0).getName());
                Assert.assertEquals("name2", list.get(1).getName());
                Assert.assertEquals("name3", list.get(2).getName());
            }
        });
    }

    @Test
    public void testTicai() throws FileNotFoundException {
		List<ExcelReadA> listAll = new ArrayList<>();
		ExcelRead.read(new FileInputStream("D:\\1.xls"), new ExcelReadDeal<ExcelReadA>() {
			// 单条数据处理（每个excel一行对应一个对象）
			@Override
			public ExcelReadA dealBean(RowDelegate delegate) {
				ExcelReadA dto = new ExcelReadA();
				delegate.getString(0).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::setQishu);
				delegate.getString(1).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(2).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(3).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(4).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(5).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(6).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(7).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addLan);
				return dto;
			}

			// 批量数据处理（可以批量入库）
			@Override
			public void dealBatchBean(List<ExcelReadA> list) {
				listAll.addAll(list);
			}
		});

		Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
		listAll.forEach(a -> {
			List<Integer> lan = a.getLan();
			lan.forEach(b -> map.compute(b, (k, v) -> {
				Map<Integer, Integer> count = new HashMap<>();
				if (v != null) {
					count.putAll(v);
				}
				List<Integer> hong = a.getHong();
				hong.forEach(c -> count.compute(c, (d, f) -> {
					if (f == null) {
						return 1;
					}
					return ++f;
				}));
				return count;
			}));
		});

		Map<Integer, Integer> l1 = map.get(4);
		Map<Integer, Integer> l2 = map.get(6);

		Map<Integer, Integer> totalCount = new HashMap<>();
		for (int i = 1; i <= 35; i++) {
			Integer lv1 = l1.getOrDefault(i, 0);
			Integer lv2 = l2.getOrDefault(i, 0);
			totalCount.put(i, lv1 + lv2);
		}

		System.out.println(1);
	}

	@Test
	public void testFucai() throws FileNotFoundException {
		List<ExcelReadA> listAll = new ArrayList<>();
		ExcelRead.read(new FileInputStream("D:\\2.xls"), new ExcelReadDeal<ExcelReadA>() {
			// 单条数据处理（每个excel一行对应一个对象）
			@Override
			public ExcelReadA dealBean(RowDelegate delegate) {
				ExcelReadA dto = new ExcelReadA();
				delegate.getString(0).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::setQishu);
				delegate.getString(1).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(2).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(3).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(4).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(5).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(6).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addHong);
				delegate.getString(7).map(BigDecimal::new).map(BigDecimal::intValue).ifPresent(dto::addLan);
				return dto;
			}

			// 批量数据处理（可以批量入库）
			@Override
			public void dealBatchBean(List<ExcelReadA> list) {
				listAll.addAll(list);
			}
		});

		Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
		listAll.forEach(a -> {
			List<Integer> lan = a.getLan();
			lan.forEach(b -> map.compute(b, (k, v) -> {
				Map<Integer, Integer> count = new HashMap<>();
				if (v != null) {
					count.putAll(v);
				}
				List<Integer> hong = a.getHong();
				hong.forEach(c -> count.compute(c, (d, f) -> {
					if (f == null) {
						return 1;
					}
					return ++f;
				}));
				return count;
			}));
		});

		Map<Integer, Integer> count = new HashMap<>();
		listAll.forEach(o -> count.compute(o.getLan().get(0), (a, b) -> {
			if (b == null) {
				return 1;
			}
			return ++b;
		}));

		Map<Integer, Integer> l1 = map.get(3);

		System.out.println(1);
	}

	public static class ExcelReadA {
    	private Integer qishu;
    	private List<Integer> hong = new ArrayList<>();
    	private List<Integer> lan = new ArrayList<>();

		public Integer getQishu() {
			return qishu;
		}

		public void setQishu(Integer qishu) {
			this.qishu = qishu;
		}

		public List<Integer> getHong() {
			return hong;
		}

		public void addHong(Integer hong) {
			this.hong.add(hong);
		}

		public List<Integer> getLan() {
			return lan;
		}

		public void addLan(Integer lan) {
			this.lan.add(lan);
		}
	}
}
