package org.example;

import org.apache.ibatis.annotations.Param;


public interface UserMapper {

    Integer verifyNotSafe(@Param("username") String username, @Param("password") String password);

    Integer verifySafe(@Param("username") String username, @Param("password") String password);

    User selectUserNotSafe(@Param("username") String username);

    User selectUserSafe(@Param("username") String username);
}
