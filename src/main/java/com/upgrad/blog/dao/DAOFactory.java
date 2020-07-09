package com.upgrad.blog.dao;

import com.upgrad.blog.interfaces.PostsCRUD;
import com.upgrad.blog.interfaces.UserCRUD;

/**
 * TODO: 6.8. Provide a factory method which returns PostDAO object. (Hint: Return type
 * of this method should be PostsCRUD)
 * TODO: 6.9. Provide a factory method which returns UserDAO object. (Hint: Return type
 * of this method should be UserCRUD)
 */
public class DAOFactory {
    private PostsCRUD postsCRUD;
    private UserCRUD userCRUD;

    public DAOFactory(UserCRUD userCRUD) {
        this.userCRUD = userCRUD;
    }



    public DAOFactory(PostsCRUD postsCRUD) {
        this.postsCRUD = postsCRUD;
    }

    public PostsCRUD getPostsCRUDS() {
        return postsCRUD;
    }
    public UserCRUD getUserCRUDS(){return userCRUD; }

}
