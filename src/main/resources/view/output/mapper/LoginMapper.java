package /packageName/.web.view.output.mapper;


import /packageName/.dao.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by leosoft on 2018/8/6.
 */
@Mapper
public interface LoginMapper {

    /packageName/.web.view.output.mapper.LoginMapper INSTANTCE= Mappers.getMapper(/packageName/.web.view.output.mapper.LoginMapper.class);

    @Mapping(source = "id",target = "userId")
    /packageName/.web.view.output.LoginOutput userToLoginOutput(User user);
}
