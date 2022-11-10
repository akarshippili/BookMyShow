package com.movieTicketService.userservice.service;

import com.movieTicketService.userservice.dao.entity.Role;
import com.movieTicketService.userservice.dao.entity.User;
import com.movieTicketService.userservice.dao.repo.RoleRepository;
import com.movieTicketService.userservice.dao.repo.UserRepository;
import com.movieTicketService.userservice.dto.UserRequestDTO;
import com.movieTicketService.userservice.dto.UserResponseDTO;
import com.movieTicketService.userservice.exception.RoleNotFoundException;
import com.movieTicketService.userservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    public UserResponseDTO save(UserRequestDTO userRequestDTO){

        Role role = null;
        if(userRequestDTO.getRoleId()!=null){
            Optional<Role> optionalRole = roleRepository.findById(userRequestDTO.getRoleId());
            if(optionalRole.isEmpty()) throw new RoleNotFoundException(userRequestDTO.getRoleId());
            role = optionalRole.get();
        }

        User user = modelMapper.map(userRequestDTO, User.class);
        user.setRole(role);
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
        return modelMapper.map(getById(id), UserResponseDTO.class);
    }

    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO)  {
        User user = getById(id);

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
        repository.delete(getById(id));
    }

    private User getById(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isEmpty()) throw new UserNotFoundException(id);

        return optionalUser.get();
    }


}
