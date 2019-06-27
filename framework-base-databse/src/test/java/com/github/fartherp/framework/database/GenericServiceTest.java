/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.database;

import com.github.fartherp.framework.database.dao.UserMapper;
import com.github.fartherp.framework.database.pojo.User;
import com.github.fartherp.framework.database.service.UserService;
import com.github.fartherp.framework.database.service.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2019/6/27
 */
public class GenericServiceTest {

	@InjectMocks
	private UserService userService = new UserServiceImpl();

	@Mock
	private UserMapper userMapper;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testDeleteNull() {
		userService.delete(null);
	}

	@Test
	public void testDelete() {
		userService.delete(1);
	}

	@Test
	public void testDeleteBatchNull() {
		userService.deleteBatch(null);
	}

	@Test
	public void testDeleteBatchEmpty() {
		userService.deleteBatch(new ArrayList<>());
	}

	@Test
	public void testDeleteBatch() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		userService.deleteBatch(list);
	}

	@Test
	public void testFindAll() {
		List<User> result = new ArrayList<>();
		Mockito.when(userMapper.selectAll()).thenReturn(result);
		List<User> list = userService.findAll();
		assertEquals(list, result);
	}

	@Test
	public void testFindById() {
		User result = new User();
		Mockito.when(userMapper.selectByPrimaryKey(1)).thenReturn(result);
		User user = userService.findById(1);
		assertEquals(result, user);
	}

	@Test
	public void testCount() {
		long count = 10L;
		Mockito.when(userMapper.count()).thenReturn(count);
		long result = userService.count();
		assertEquals(result, count);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testSaveEntityNull() {
		userService.saveEntity(null);
	}

	@Test
	public void testSaveEntity() {
		User user = new User();
		user.setId(1);
		Integer id = userService.saveEntity(user);
		assertEquals(id, user.getId());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testSaveEntitySelectiveNull() {
		userService.saveEntitySelective(null);
	}

	@Test
	public void testSaveEntitySelective() {
		User user = new User();
		user.setId(1);
		Integer id = userService.saveEntitySelective(user);
		assertEquals(id, user.getId());
	}

	@Test
	public void testSaveBatchNull() {
		userService.saveBatch(null);
	}

	@Test
	public void testSaveBatchEmpty() {
		userService.saveBatch(new ArrayList<>());
	}

	@Test
	public void testSaveBatch() {
		List<User> list = new ArrayList<>();
		list.add(new User());
		userService.saveBatch(list);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateEntityNull() {
		userService.updateEntity(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateEntityIdNull() {
		userService.updateEntity(new User());
	}

	@Test
	public void testUpdateEntity() {
		User user = new User();
		user.setId(1);
		userService.updateEntity(user);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateEntitySelectiveNull() {
		userService.updateEntitySelective(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateEntitySelectiveIdNull() {
		userService.updateEntitySelective(new User());
	}

	@Test
	public void testUpdateEntitySelective() {
		User user = new User();
		user.setId(1);
		userService.updateEntitySelective(user);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testSaveOrUpdateNull() {
		userService.saveOrUpdate(null);
	}

	@Test
	public void testSaveOrUpdateIdNull() {
		User user = new User();
		User result = userService.saveOrUpdate(user);
		assertEquals(result, user);
	}

	@Test
	public void testSaveOrUpdateDBNotNull() {
		User user = new User();
		user.setId(1);
		Mockito.when(userMapper.selectByPrimaryKey(1)).thenReturn(user);
		User result = userService.saveOrUpdate(user);
		assertEquals(result, user);
	}

	@Test
	public void testSaveOrUpdateDBNull() {
		User user = new User();
		user.setId(1);
		Mockito.when(userMapper.selectByPrimaryKey(1)).thenReturn(null);
		User result = userService.saveOrUpdate(user);
		assertEquals(result, user);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testSaveOrUpdateSelectiveNull() {
		userService.saveOrUpdateSelective(null);
	}

	@Test
	public void testSaveOrUpdateSelectiveIdNull() {
		User user = new User();
		User result = userService.saveOrUpdateSelective(user);
		assertEquals(result, user);
	}

	@Test
	public void testSaveOrUpdateSelectiveDBNotNull() {
		User user = new User();
		user.setId(1);
		Mockito.when(userMapper.selectByPrimaryKey(1)).thenReturn(user);
		User result = userService.saveOrUpdateSelective(user);
		assertEquals(result, user);
	}

	@Test
	public void testSaveOrUpdateSelectiveDBNull() {
		User user = new User();
		user.setId(1);
		Mockito.when(userMapper.selectByPrimaryKey(1)).thenReturn(null);
		User result = userService.saveOrUpdateSelective(user);
		assertEquals(result, user);
	}
}
