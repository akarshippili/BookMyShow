package com.ticketapp.userservice.service;

import com.ticketapp.userservice.dao.entity.Role;
import com.ticketapp.userservice.dao.entity.User;
import com.ticketapp.userservice.dao.repo.RoleRepository;
import com.ticketapp.userservice.dao.repo.UserRepository;
import com.ticketapp.userservice.dto.UserDTO;
import com.ticketapp.userservice.exception.RoleNotFoundException;
import com.ticketapp.userservice.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends AbstractService implements UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository repository, RoleRepository roleRepository) {
        super(modelMapper);
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public UserDTO save(UserDTO userRequestDTO){

        Role role = null;
        if(userRequestDTO.getRoleId()!=null){
            Optional<Role> optionalRole = roleRepository.findById(userRequestDTO.getRoleId());
            if(optionalRole.isEmpty()) throw new RoleNotFoundException(userRequestDTO.getRoleId());
            role = optionalRole.get();
        }

        User user = modelMapper.map(userRequestDTO, User.class);
        user.setRole(role);
        user = repository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> findAll(){
        return repository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id){
        return modelMapper.map(getById(id), UserDTO.class);
    }

    public UserDTO update(Long id, UserDTO userRequestDTO)  {
        User user = getById(id);

        if(userRequestDTO.getRoleId()!=null){
            Optional<Role> optionalRole = roleRepository.findById(userRequestDTO.getRoleId());
            if(optionalRole.isEmpty()) throw new RoleNotFoundException(userRequestDTO.getRoleId());
            else user.setRole(optionalRole.get());
        }

        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());

        repository.save(user);
        return modelMapper.map(user, UserDTO.class);
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
