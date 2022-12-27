package com.ticketapp.userservice.dao.repo;

import com.ticketapp.userservice.dao.entity.Permission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void tearDown() {
        permissionRepository.deleteAll();
    }

    @Test
    public void savePermission() {
        //given
        Permission permission = new Permission();
        permission.setCode("code");
        permission.setDescription("description");

        //when
        Permission savedPermission =  permissionRepository.save(permission);
        Integer rowCount = jdbcTemplate.queryForObject("SELECT Count(*) FROM T_PERMISSION", Integer.class);

        //then
        assertEquals(1, rowCount);
        assertNotNull(savedPermission);
        assertEquals(permission.getCode(), savedPermission.getCode());
        assertEquals(permission.getDescription(), savedPermission.getDescription());
        assertNotNull(savedPermission.getId());
    }

    @Test
    public void findById() {
        //given
        Permission permission = new Permission();
        permission.setCode("code");
        permission.setDescription("description");

        //when
        Long id =  permissionRepository.save(permission).getId();

        //then
        assertTrue(permissionRepository.existsById(id));
    }



}