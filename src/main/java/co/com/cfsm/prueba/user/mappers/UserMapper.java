package co.com.cfsm.prueba.user.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import co.com.cfsm.prueba.user.dto.UserDto;
import co.com.cfsm.prueba.user.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  User createUserDtoToUser(UserDto createUserDto);
  
  UserDto userToUserDto(User createUserDto);


}
