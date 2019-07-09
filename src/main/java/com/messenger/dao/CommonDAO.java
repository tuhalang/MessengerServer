package com.messenger.dao;

import java.util.List;

import com.messenger.mapper.RowMapper;

public interface CommonDAO<T> {
	<T> List<T> query(String sql, RowMapper<T> rowMapper, Object...parameters);
	long insert(String sql, Object...parameters);
	void update(String sql, Object...parameters);
	void delete(String sql, Object...parameters);
	long count(String sql, Object...parameters);
}
