package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.RoleDto;
import com.oasis.ocrspring.dto.RoleReqDto;
import com.oasis.ocrspring.model.Role;
import com.oasis.ocrspring.repository.RoleRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock
    private RoleRepository roleRepo;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        // Setup can be used for common initialization if needed
    }

    @Test
    void updateRoleTest() {
        // Arrange
        String id = "507f1f77bcf86cd799439011";
        RoleDto roleDetails = new RoleDto("System Admin", List.of(1, 2, 3));
        Role existingRole = new Role("oldRole", List.of(4, 5, 6));

        when(roleRepo.findById(new ObjectId(id))).thenReturn(Optional.of(existingRole));

        // Act
        roleService.updateRole(id, roleDetails);

        // Assert
        verify(roleRepo).save(existingRole);
        assertEquals("System Admin", existingRole.getRole());
        assertEquals(List.of(1, 2, 3), existingRole.getPermissions());
    }

    @Test
    void allRoleDetailsTest() {
        // Arrange
        List<Role> roles = List.of(new Role("System Admin", List.of(1, 2)), new Role("User", List.of(3, 4)));
        when(roleRepo.findAll()).thenReturn(roles);

        // Act
        List<Role> result = roleService.allRoleDetails();

        // Assert
        assertEquals(2, result.size());
        verify(roleRepo).findAll();
    }

    @Test
    void createRoleTest() {
        // Arrange
        Role role = new Role("System Admin", List.of(1, 2, 3));
        when(roleRepo.save(role)).thenReturn(role);

        // Act
        Role createdRole = roleService.createRole(role);

        // Assert
        assertEquals("System Admin", createdRole.getRole());
        assertEquals(List.of(1, 2, 3), createdRole.getPermissions());
        verify(roleRepo).save(role);
    }

    @Test
    void getRoleByRoleTest() {
        // Arrange
        String roleName = "System Admin";
        Role role = new Role(roleName, List.of(1, 2, 3));
        when(roleRepo.findByRole(roleName)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleService.getRoleByrole(roleName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(roleName, result.get().getRole());
        verify(roleRepo).findByRole(roleName);
    }

    @Test
    void getRoleByIdTest() {
        // Arrange
        String id = "507f1f77bcf86cd799439011";
        Role role = new Role("System Admin", List.of(1, 2, 3));
        when(roleRepo.findById(new ObjectId(id))).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleService.getRoleById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("System Admin", result.get().getRole());
        verify(roleRepo).findById(new ObjectId(id));
    }

    @Test
    void addRoleTest_Success() {
        // Arrange
        RoleReqDto roleReqDto = new RoleReqDto("System Admin", List.of(1, 2, 3));
        when(roleRepo.findByRoleIgnoreCase("System Admin")).thenReturn(Optional.empty());

        // Act
        boolean result = roleService.addRole(roleReqDto);

        // Assert
        assertTrue(result);
        verify(roleRepo).save(any(Role.class));
    }

    @Test
    void addRoleTest_Failure() {
        // Arrange
        RoleReqDto roleReqDto = new RoleReqDto("System Admin", List.of(1, 2, 3));
        Role existingRole = new Role("System Admin", List.of(1, 2, 3));
        when(roleRepo.findByRoleIgnoreCase("System Admin")).thenReturn(Optional.of(existingRole));

        // Act
        boolean result = roleService.addRole(roleReqDto);

        // Assert
        assertFalse(result);
        verify(roleRepo, never()).save(any(Role.class));
    }
}
