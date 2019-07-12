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
package com.github.fartherp.framework.common.util;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author cuiyuqiang
 * @date 2019/7/1
 */
public class CsvUtilTest {

	File tmpdir = PlatformDependent.tmpdir();

	String fileName = new File(tmpdir.getPath() ,"test_csv").getPath();

	@AfterMethod
	public void tearDown() throws IOException {
		File file = new File(fileName + ".csv");
		Assert.assertTrue(file.exists());
		file.delete();
	}

	@Test
	public void testStringArrayWriteCsvFile() {
		String[] title = {"id" , "name"};
		List<String[]> bodyList = new ArrayList<>();
		String[] data = new String[2];
		data[0] = "1";
		data[1] = "张三";
		bodyList.add(data);
		data = new String[2];
		data[0] = "2";
		data[1] = "李四";
		bodyList.add(data);
		CsvUtil.writeCsvFile(fileName, title, bodyList);
	}

	@Test
	public void testObjectWriteCsvFile() {
		String[] title = {"id" , "name"};
		List<User> objs = new ArrayList<>();
		User user = new User();
		user.setId(1);
		user.setName("张三");
		objs.add(user);
		user = new User();
		user.setId(2);
		user.setName("李四");
		objs.add(user);
		CsvUtil.writeCsvFile(fileName, title, objs, t -> {
			String[] bodyList = new String[2];
			bodyList[0] = t.getId() + "";
			bodyList[1] = t.getName();
			return bodyList;
		});
	}

	public class User {
		private Integer id;
		private String name;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
