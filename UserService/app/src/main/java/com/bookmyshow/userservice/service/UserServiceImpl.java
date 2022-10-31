package com.bookmyshow.userservice.service;

import com.bookmyshow.userservice.dao.entity.Role;
import com.bookmyshow.userservice.dao.entity.User;
import com.bookmyshow.userservice.dao.repo.RoleRepository;
import com.bookmyshow.userservice.dao.repo.UserRepository;
import com.bookmyshow.userservice.dto.UserRequestDTO;
import com.bookmyshow.userservice.dto.UserResponseDTO;
import com.bookmyshow.userservice.exception.UserNotFoundException;
import com.bookmyshow.userservice.exception.RoleNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserResponseDTO save(UserRequestDTO userRequestDTO){

        if(userRequestDTO.getRoleId()!=null){
            Optional<Role> optionalRole = roleRepository.findById(userRequestDTO.getRoleId());
            if(optionalRole.isEmpty()) throw new RoleNotFoundException(userRequestDTO.getRoleId());
        }

        User user = modelMapper.map(userRequestDTO, User.class);
        user = repository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public List<UserResponseDTO> findAll(){
        return repository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    public UserResponseDTO findById(Long id){
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isEmpty()) throw new UserNotFoundException(id);

        return modelMapper.map(optionalUser.get(), UserResponseDTO.class);
    }

    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO)  {
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isEmpty()) throw new UserNotFoundException(id);

        User user = optionalUser.get();

        if(userRequestDTO.getRoleId()!=null){
            Optional<Role> optionalRole = roleRepository.findById(userRequestDTO.getRoleId());
            if(optionalRole.isEmpty()) throw new RoleNotFoundException(userRequestDTO.getRoleId());
            else user.setRole(optionalRole.get());
        }

        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());

        user = repository.save(user);

        return modelMapper.map(user, UserResponseDTO.class);
    }


    public void delete(Long id){
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isEmpty()) throw new UserNotFoundException(id);

        repository.delete(optionalUser.get());
    }


}
