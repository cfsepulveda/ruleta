package co.com.cfsm.prueba.user.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import co.com.cfsm.prueba.commons.NotificationCode;
import co.com.cfsm.prueba.commons.exeptions.RouletteBusinessException;
import co.com.cfsm.prueba.user.dto.UserDto;
import co.com.cfsm.prueba.user.mappers.UserMapper;
import co.com.cfsm.prueba.user.model.User;
import co.com.cfsm.prueba.user.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServices {


	private UserRepository userRepository;
	private UserMapper mapper;


	public UserDto create(UserDto createUserDto) throws RouletteBusinessException {

		if (userRepository.findById(createUserDto.getDocumentNumber()).isPresent()) {
			throw new RouletteBusinessException(NotificationCode.USR_F_1);
		}

		User user = mapper.createUserDtoToUser(createUserDto);
		return mapper.userToUserDto(userRepository.save(user));
	}

	public UserDto update(UserDto createUserDto) throws RouletteBusinessException {
		User user = mapper.createUserDtoToUser(createUserDto);
		return mapper.userToUserDto(userRepository.save(user));
	}


	public UserDto findByDocumentNumber(String documentNumber) throws RouletteBusinessException {

		Optional<User> user = userRepository.findById(documentNumber);

		if (!user.isPresent()) {
			throw new RouletteBusinessException(NotificationCode.USR_F_2);
		}
		return mapper.userToUserDto(user.get());
	}


}
