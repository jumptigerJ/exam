package com.exam.film;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.exam.util.ConnectionFactory;

public class Film {

	public static void main(String[] args) {
		Connection conn = null;
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入CustomerID:");
		int customerId = scanner.nextInt();
		String sql1 = "select inventory_id,rental_date from rental where customer_id = "+customerId+" order by rental_date desc";
		
		try {
			conn = ConnectionFactory.getInstance().makeConnection();
			conn.setAutoCommit(false);
			
			Statement st = conn.createStatement();
			Statement st2 = conn.createStatement();
			ResultSet rs  = st.executeQuery(sql1);
			System.out.println("JOHNNIE:CHISHOLM租用的Film");
			System.out.println("Film ID|Film 名称|租用时间");
			while(rs.next()){
				int inventoryId = rs.getInt("inventory_id");
				Date rentalDater= rs.getDate("rental_date");
				
				String sql2 = "select title,film_id from film where film_id = (select film_id from inventory where inventory_id = "+inventoryId+")";			
				ResultSet rs2  = st2.executeQuery(sql2);
				while(rs2.next()){
					System.out.print(rs2.getInt("film_id")+"|");
					System.out.print(rs2.getString("title")+"|");
					System.out.print(rentalDater);
					System.out.println();

				}
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
