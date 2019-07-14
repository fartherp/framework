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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2019/6/27
 */
public class DbManagerTest {

	private DbManager dbManager;

	@BeforeMethod
	public void setUp() {
		dbManager = new DbManager();
		dbManager.setDriveName("com.mysql.jdbc.Driver");
		dbManager.setUser("root");
		dbManager.setPassword("root");
		dbManager.setUrl("jdbc:mysql://localhost:3306/test_tmp");
	}

	@Test
	public void testGetConnection() {
		Connection connection = dbManager.getConnection();
		assertNotNull(connection);
		dbManager.close(connection);
	}

	@Test
	public void testGetPreparedStatement() {
		Connection connection = dbManager.getConnection();
		PreparedStatement preparedStatement = dbManager.getPreparedStatement(connection, "select count(1) as count from tb_user");
		assertNotNull(preparedStatement);
		dbManager.close(connection, preparedStatement, null);
	}

	@Test
	public void testGetResultSet() throws Exception {
		Connection connection = dbManager.getConnection();
		PreparedStatement preparedStatement = dbManager.getPreparedStatement(connection, "select count(1) as count from tb_user");
		ResultSet resultSet = dbManager.getResultSet(preparedStatement);
		assertNotNull(resultSet);
		resultSet.next();
		long count = resultSet.getLong("count");
		assertEquals(count, 0);
		dbManager.close(connection, preparedStatement, resultSet);
	}

	@Test
	public void testGetTables() throws Exception {
		Connection connection = dbManager.getConnection();
		ResultSet resultSet = dbManager.getTables(connection, "", "test_tmp", "");
		assertNotNull(resultSet);
		int size = 0;
		while (resultSet.next()) {
			size++;
		}
		assertEquals(size, 1);
		dbManager.close(connection, null, resultSet);
	}

	@Test
	public void testGetColumns() throws Exception {
		Connection connection = dbManager.getConnection();
		ResultSet resultSet = dbManager.getColumns(connection, "", "test_tmp", "tb_user");
		assertNotNull(resultSet);
		int size = 0;
		while (resultSet.next()) {
			size++;
		}
		assertEquals(size, 6);
		dbManager.close(connection, null, resultSet);
	}

	@Test
	public void testGetPrimaryKeys() throws Exception {
		Connection connection = dbManager.getConnection();
		ResultSet resultSet = dbManager.getPrimaryKeys(connection, "", "test_tmp", "tb_user");
		assertNotNull(resultSet);
		int size = 0;
		while (resultSet.next()) {
			size++;
		}
		assertEquals(size, 1);
		dbManager.close(connection, null, resultSet);
	}

	@Test
	public void testGetResultSetMetaData() throws Exception {
		Connection connection = dbManager.getConnection();
		PreparedStatement preparedStatement = dbManager.getPreparedStatement(connection, "select count(1) as count from tb_user");
		ResultSet resultSet = dbManager.getResultSet(preparedStatement);
		ResultSetMetaData resultSetMetaData = dbManager.getResultSetMetaData(resultSet);
		assertNotNull(resultSetMetaData);
		assertEquals(resultSetMetaData.getColumnCount(), 1);
	}

	@Test
	public void testGetDatabaseMetaData() throws Exception {
		Connection connection = dbManager.getConnection();
		DatabaseMetaData databaseMetaData = dbManager.getDatabaseMetaData(connection);
		assertNotNull(databaseMetaData);
		assertEquals(databaseMetaData.getDatabaseProductName().toLowerCase(), "mysql");
		dbManager.close(connection);
	}
}
