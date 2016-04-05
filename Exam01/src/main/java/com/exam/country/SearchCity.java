package com.exam.country;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.exam.util.ConnectionFactory;

public class SearchCity {
	public static void main(String[] args) {
		Connection conn = null;
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入CountryID:");
		int countryId = scanner.nextInt();
		String sql = "select city_id,city from city where country_id = "+countryId+"";
		try {
			conn = ConnectionFactory.getInstance().makeConnection();
			conn.setAutoCommit(false);
			
			Statement st = conn.createStatement();
			ResultSet rs  = st.executeQuery(sql);
			System.out.println("Country Spain的城市->");
			System.out.println("城市的ID|城市的名称");
			while(rs.next()){
				System.out.print(rs.getInt("city_id")+"|");
				System.out.print(rs.getString("city"));
				System.out.println();
		
			}
			conn.commit();
			
		} catch (Exception e) {
			System.out.println("====执行数据库操作错误=======");
			try {
				conn.rollback();
				System.out.println("====回滚成功====");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
}
