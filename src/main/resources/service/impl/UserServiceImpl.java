package /packageName/.service.impl;

import /packageName/.dao.entity.User;
import /packageName/.dao.mapper.IUserDAO;;
import /packageName/.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by leosoft on 2018/9/14.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public User login(String userName, String password) {
        return userDAO.selectByPrimaryKey(1);
    }

    @Override
    public boolean transactionTest() {
        return false;
    }
}
