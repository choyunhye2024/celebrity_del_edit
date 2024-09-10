package com.list;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Celebrity {

	static Connection con = null;
	static Statement st = null;
	static ResultSet result = null;
	static Scanner sc = new Scanner(System.in);

	public static void run() {

		dbInit();
		loop: while (true) {

			dbPostCount();

			System.out.println("1. 리스트 / 2. 쓰기 /3. 수정 / 4. 삭제 / e. 종료");
			String cmd = sc.next();
			System.out.println(cmd);

			switch (cmd) {

			case "1":
				// 리스트
				System.out.println("-----------명단-----------");
				System.out.println("번호 / 이름 / 생일 / 직업");
				System.out.println("-------------------------");

				try {
					result = st.executeQuery("select * from celebrity");

					while (result.next()) {

						String no = result.getString("l_no");
						String name = result.getString("l_name");
						String birthday = result.getString("l_birthday");
						String job = result.getString("l_job");

						System.out.println(no + "" + name + "" + birthday + "" + job);

					}

				} catch (Exception e) {

					e.printStackTrace();
				}
				break;

			case "2":
				// 쓰기

				System.out.println("이름을 입력해주세요");
				String name = sc.next();
				System.out.println("생년월일을 입력해주세요:");
				String birthday = sc.next();
				System.out.println("직업을 입력해주세요:");
				String job = sc.next();

				try {
					// insert into celebrity (l_name, l_birthday, l_job) values
					// ('강동원','810118','배우');

					String sql = "insert into celebrity (l_name, l_birthday, l_job)" + "values ('" + name + "','"
							+ birthday + "','" + job + "')";
					System.out.println(sql);
					st.executeUpdate(sql);
				} catch (Exception e) {

					e.printStackTrace();
				}
				break;

			case "3":
				// 수정
				System.out.println("수정할 글 번호를 입력해주세요:");
				String editNo = sc.next();
				System.out.println("이름을 입력해주세요:");
				String edName = sc.next();
				System.out.println("생년월일을 입력해주세요:");
				String edBirthday = sc.next();
				System.out.println("직업을 입력해주세요:");
				String job1 = sc.next();

				try {

					String sql = "update celebrity set l_name='" + edName + "',l_birthday='" + edBirthday + "',l_job='"
							+ job1 + "'where l_no='" + editNo + "'";
					System.out.println(sql);
					st.executeUpdate(sql);
				} catch (Exception e) {

					e.printStackTrace();
				}

				break;

			case "4":
				System.out.println("삭제할 명단 번호를 입력해주세요:");
				String delNo = sc.next();
				dbExecuteUpdate("delete from celebrity where l_no=" + delNo);
				// 삭제

				break;

			case "e":
				// 종료
				break loop;
			}

		}

	}

	private static void dbInit() {

		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_cat", "root", "root");
			st = con.createStatement();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private static void dbPostCount() {

		try {

			result = st.executeQuery("select count(*) from celebrity");
			result.next();
			String count = result.getString("count(*)");
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private static void dbExecuteUpdate(String query) {

		try {

			int resultCount = st.executeUpdate(query);
			System.out.println("처리된 행 수:" + resultCount);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
}
