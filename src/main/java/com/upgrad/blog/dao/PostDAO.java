package com.upgrad.blog.dao;

/**
 * TODO: 6.12. Implement the PostsCRUD interface.
 * TODO: 6.13. Define the following methods and return null for each of them. You will provide
 * a proper implementation for each of these methods, later in this project.
 * a. findByEmail
 * b. findByTag
 * c. findAllTags
 * d. deleteById
 * TODO: 6.14. create() method should take post details as input and insert these details
 * into the POSTS table. Return the same PostDTO object which was passed as an input argument.
 */

/**
 * TODO: 7.1. Implement findByEmail() method which takes email id as an input
 * parameter and returns all the posts corresponding to the email id from the POSTS
 * table defined in the database.
 */

/**
 * TODO: 7.6. Implement deleteById() method which takes post id and email id
 * as an input parameters and delete the corresponding post from the database. If
 * the post was deleted successfully, then return true, otherwise return false.
 */

import com.upgrad.blog.db.DatabaseConnection;
import com.upgrad.blog.dto.PostDTO;
import com.upgrad.blog.dto.UserDTO;
import com.upgrad.blog.interfaces.PostsCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * TODO: 7.10. Implement findAllTags() method which returns a set of all unique tags
 * in the POSTS table.
 * TODO: 7.11. Implement findByTag() method which takes "tag" as an input parameter and
 * returns all the posts corresponding to the input "tag" from the POSTS table defined
 * in the database.
 */
public class PostDAO implements PostsCRUD {

    @Override
    public List<PostDTO> findByEmail(String emailId) throws SQLException {
        DatabaseConnection.getInstance();
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM post WHERE emailId = ? ;");
        st.setString(1, emailId);
        ResultSet rs = st.executeQuery();
        List<PostDTO> array = new ArrayList<>();
        while(rs.next()) {
            PostDTO p = new PostDTO();
            p.setPostId(rs.getInt("id"));
            p.setEmailId(rs.getString("email_id"));
            p.setTitle(rs.getString("title"));
            p.setDescription(rs.getString("description"));
            p.setTag(rs.getString("tag"));
            p.setTimestamp(rs.getObject(4, LocalDateTime.class));
            array.add(p);
        }
        return array;
    }

    @Override
    public PostDTO create(PostDTO postDTO) throws SQLException {
        DatabaseConnection.getInstance();
        java.sql.Date date = java.sql.Date.valueOf(postDTO.getTimestamp().toLocalDate());
        java.sql.Time time = java.sql.Time.valueOf(postDTO.getTimestamp().toLocalTime());
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement st = conn.prepareStatement("INSERT INTO user(id, email_id, title,) VALUES (?, ?, ?, ?, ?, ?);");
        st.setInt(1, postDTO.getPostId());
        st.setString(2,postDTO.getEmailId());
        st.setString(3,postDTO.getTitle());
        st.setString(4,postDTO.getDescription());
        st.setString(5,postDTO.getTag());
        st.setTime(6,time);
        st.execute();
        return postDTO;
    }


    @Override
    public boolean deleteById(int id, String emailId) throws SQLException {
        return false;
    }

    @Override
    public List<PostDTO> findByTag(String tag) throws SQLException {
        return null;
    }

    @Override
    public HashSet<String> findAllTags() throws SQLException {
        return null;
    }
}
