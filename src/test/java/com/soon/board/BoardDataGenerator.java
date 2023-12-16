package com.soon.board;

import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@SpringBootTest
public class BoardDataGenerator {
	public static void main(String[] args) {
//        String jdbcUrl = "jdbc:mysql://localhost:3306/board?serverTimezone=UTC&characterEncoding=UTF-8";
//        String username = "root";
//        String password = "1234";
//
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
//            int numberOfPostsToGenerate = 10; // 생성할 게시물 수
//
//            for (int i = 0; i < numberOfPostsToGenerate; i++) {
//                String title = generateRandomTitle();
//                String content = generateRandomContent();
//                String writerEmail = "writer" + i + "@example.com";
//                int favoriteCount = new Random().nextInt(100);
//                int commentCount = new Random().nextInt(30);
//                int viewCount = new Random().nextInt(50);
//                String writeDatetime = generateRandomDate();
//
//                String insertQuery = "INSERT INTO board (title, content, writer_email, favorite_count, comment_count, " +
//                        "view_count, write_datetime) " +
//                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
//                
//                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
//                    preparedStatement.setString(1, title);
//                    preparedStatement.setString(2, content);
//                    preparedStatement.setString(3, writerEmail);
//                    preparedStatement.setInt(4, favoriteCount);
//                    preparedStatement.setInt(5, commentCount);
//                    preparedStatement.setInt(6, viewCount);
//                    preparedStatement.setString(7, writeDatetime);
//                    
//                    preparedStatement.executeUpdate();
//                }
//            }
//
//            System.out.println(numberOfPostsToGenerate + " 게시물이 생성되었습니다.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    private static String generateRandomTitle() {
        // 무작위 제목 생성 로직을 추가하세요
        return "Random Title " + new Random().nextInt(1000);
    }

    private static String generateRandomContent() {
        // 무작위 내용 생성 로직을 추가하세요
        return "Random Content " + new Random().nextInt(1000);
    }

    private static String generateRandomDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}