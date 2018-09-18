package /packageName/.service;

import /packageName/.dao.entity.User;

/**
 * Created by leosoft on 2018/9/14.
 */
public interface IUserService {
    User login(String userName, String password);

    boolean transactionTest();
}
