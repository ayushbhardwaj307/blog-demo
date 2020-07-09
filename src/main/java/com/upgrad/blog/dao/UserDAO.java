package com.upgrad.blog.dao;

import com.upgrad.blog.db.DatabaseConnection;
import com.upgrad.blog.dto.UserDTO;
import com.upgrad.blog.interfaces.UserCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TODO: 6.5. Implement the UserCRUD interface.
 * TODO: 6.6. findByEmail() method should take email id as an input parameter and
 * return the user details corresponding to the email id from the USERS table defined
 * in the database.
 * TODO: 6.7. create() method should take user details as input and insert these details
 * into the USERS table. Return the same UserDAO object which was passed as an input argument.
 */
public class UserDAO implements UserCRUD {



    public static void main(String[] args) {
        try {
            UserDAO userDAO = new UserDAO();
            UserDTO temp = new UserDTO();
            temp.setUserId(1);
            temp.setEmailId("temp@temp.temp");
            temp.setPassword("temp");
            userDAO.create(temp);
            System.out.println(userDAO.findByEmail("temp@temp.temp"));
        } catch (Exception e) {
            System.out.println("FAILED");
        }
//
//		/**
//		 * Following should be the desired output of the main method.
//		 * UserDTO{userId=11, emailId='temp@temp.temp', password='temp'}
//		 */
//	}
    }

    @Override
    public UserDTO findByEmail(String emailId) throws SQLException {
        DatabaseConnection.getInstance();
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM user WHERE email_id = ?");
        st.setString(1, emailId);
        ResultSet rs = st.executeQuery();
        UserDTO u = new UserDTO();
        if(rs.next()) {
            u.setEmailId(rs.getString("emailId"));
            u.setPassword(rs.getString("password"));
            u.setUserId(rs.getInt("userId"));
        }
        return u;
    }

    @Override
    public UserDTO create(UserDTO userDTO) throws SQLException {
        DatabaseConnection.getInstance();
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement st = conn.prepareStatement("INSERT INTO user(id, email_id, password) VALUES (?, ?, ?);");
        st.setString(1, userDTO.getEmailId());
        st.setString(2, userDTO.getPassword());
        st.setInt(3, userDTO.getUserId());
        st.execute();
        return userDTO;
    }


}
