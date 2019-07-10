package com.messenger.dao.impl;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.messenger.dao.CommonDAO;
import com.messenger.logger.Logging;
import com.messenger.mapper.RowMapper;

public class CommonDAOImpl<T> implements CommonDAO<T> {
	
	private static Logger logger = Logging.getLogger();
	
	public Connection getConnection() {
		Logger logger = Logging.getLogger();
		
		String driver = "";
		String url = "";
		String username = "";
		String password = "";
		
		FileReader reader = null;
		Properties p = null;
		try {
			reader = new FileReader("config/application.properties");
			p = new Properties();  
		    p.load(reader);  
		    driver = p.getProperty("DRIVER");
		    url = p.getProperty("URL");
		    username = p.getProperty("USERNAME");
		    password = p.getProperty("PASSWORD");
		} catch (IOException e) {
			logger.severe("CommonDAO: " + e.getMessage());
		} finally {
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.severe("CommonDAO: " + e.getMessage());
				}
			}
		}
		
		try {
			Class.forName(driver);  
			Connection con=DriverManager.getConnection(url,username,password);
			return con;
		} catch (SQLException e) {
			logger.severe("CommonDAO: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.severe("CommonDAO: " + e.getMessage());
		}  

		return null;
	}

	
	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
		List<T> results = new ArrayList<T>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			statement = conn.prepareStatement(sql);
			setParameter(statement, parameters);
			rs = statement.executeQuery();
			while(rs.next()) {
				results.add(rowMapper.mapRow(rs));
			}
			return results;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return results;
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
		}
		
	}

	
	@Override
	public long insert(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		long id = -1;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			setParameter(preparedStatement, parameters);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				id = resultSet.getLong(1);
			}
			conn.commit();
			return id;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					logger.severe(e1.getMessage());
				}
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
		}
		return -1;
	}

	@Override
	public void update(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			preparedStatement = conn.prepareStatement(sql);
			setParameter(preparedStatement, parameters);
			preparedStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					logger.severe(e1.getMessage());
				}
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
		}
	}

	@Override
	public void delete(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			preparedStatement = conn.prepareStatement(sql);
			setParameter(preparedStatement, parameters);
			preparedStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					logger.severe(e1.getMessage());
				}
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
		}
	}

	@Override
	public long count(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			int count = 0;
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			setParameter(statement, parameters);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return 0;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				logger.severe(e.getMessage());
			}
		}
	}
	
	private void setParameter(PreparedStatement statement, Object... parameters) {
		try {
			for (int i = 0; i < parameters.length; i++) {
				Object parameter = parameters[i];
				int index = i + 1;
				if (parameter instanceof Long) {
					statement.setLong(index, (Long) parameter);
				} else if (parameter instanceof String) {
					statement.setString(index, (String) parameter);
				} else if (parameter instanceof Integer) {
					statement.setInt(index, (Integer) parameter);
				} else if (parameter instanceof Timestamp) {
					statement.setTimestamp(index, (Timestamp) parameter);
				}
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}

}
