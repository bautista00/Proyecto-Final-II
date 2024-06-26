package com.proyectointegrador.msusers;

import com.proyectointegrador.msusers.domain.Ticket;
import com.proyectointegrador.msusers.domain.User;
import com.proyectointegrador.msusers.repository.TicketRepository;
import com.proyectointegrador.msusers.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {
	@Mock
	private Keycloak keycloak;

	@Mock
	private RealmResource realmResource;

	@Mock
	private UsersResource usersResource;

	private UserRepository userRepository;

	@Mock
	private TicketRepository ticketRepository;


	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(keycloak.realm(anyString())).thenReturn(realmResource);
		when(realmResource.users()).thenReturn(usersResource);
		userRepository = new UserRepository(keycloak,ticketRepository);
		userRepository.setRealm("boca");
	}



	@Test
	void testFindUserById() {
		String userId = "user_id";
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setId(userId);
		when(usersResource.get(userId)).thenReturn(mock(UserResource.class));
		when(usersResource.get(userId).toRepresentation()).thenReturn(userRepresentation);

		Optional<User> user = userRepository.findUserById(userId);

		assertEquals(userId, user.orElseThrow().getId());
	}

	@Test
	void testFindUserByUsername() {
		String username = "testuser";
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setId("1");
		userRepresentation.setUsername(username);
		when(usersResource.search(username)).thenReturn(Collections.singletonList(userRepresentation));

		List<User> users = userRepository.findUserByUsername(username);

		assertEquals(1, users.size());
		assertEquals(username, users.get(0).getUserName());
	}

	@Test
	void testFindAll() {
		UserRepresentation userRepresentation1 = new UserRepresentation();
		userRepresentation1.setId("1");
		UserRepresentation userRepresentation2 = new UserRepresentation();
		userRepresentation2.setId("2");
		when(usersResource.list()).thenReturn(Arrays.asList(userRepresentation1, userRepresentation2));

		List<User> users = userRepository.findAll();

		assertEquals(2, users.size());
	}

	@Test
	void testDeleteUserById() {
		String userId = "1";

		userRepository.deleteUserById(userId);

		verify(usersResource, times(1)).delete(userId);
	}

	@Test
	void testUpdateUser() {
		String userId = "1";
		List<Ticket> ticketList = null;
		User user = new User(userId, "test", "Test", "User", "test@example.com",ticketList);
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setId(userId);
		userRepresentation.setUsername(user.getUserName());
		userRepresentation.setFirstName(user.getFirstName());
		userRepresentation.setLastName(user.getLastName());
		userRepresentation.setEmail(user.getEmail());

		UserResource userResourceMock = mock(UserResource.class);
		when(usersResource.get(userId)).thenReturn(userResourceMock);
		when(userResourceMock.toRepresentation()).thenReturn(userRepresentation);
		doNothing().when(userResourceMock).update(any(UserRepresentation.class));

		User updatedUser = userRepository.updateUser(user);

		assertEquals(user, updatedUser);
	}

	@Test
	void testDeleteRoleToUser() {
		String userId = "1";
		String roleName = "role1";
		RoleRepresentation roleRepresentation = new RoleRepresentation();
		roleRepresentation.setName(roleName);

		UserResource userResource = mock(UserResource.class);
		RolesResource rolesResource = mock(RolesResource.class);
		RoleResource roleResource = mock(RoleResource.class);
		RoleScopeResource roleScopeResource = mock(RoleScopeResource.class);

		when(usersResource.get(userId)).thenReturn(userResource);
		when(realmResource.roles()).thenReturn(rolesResource);
		when(rolesResource.get(roleName)).thenReturn(roleResource);
		when(realmResource.roles().get(roleName).toRepresentation()).thenReturn(roleRepresentation);
		when(userResource.roles()).thenReturn(mock(RoleMappingResource.class));
		when(userResource.roles().realmLevel()).thenReturn(roleScopeResource);

		userRepository.deleteRoleToUser(userId, roleName);

		List<String> remainingRoles = userRepository.getUserRoles(userId);
		assertFalse(remainingRoles.contains(roleName));
	}



	@Test
	void testGetUserRoles() {
		String userId = "1";
		String roleName = "role1";
		RoleRepresentation roleRepresentation = new RoleRepresentation();
		roleRepresentation.setName(roleName);
		UserResource userResource = mock(UserResource.class);
		RoleMappingResource roleMappingResource = mock(RoleMappingResource.class);
		RoleScopeResource roleScopeResource = mock(RoleScopeResource.class);
		when(usersResource.get(userId)).thenReturn(userResource);
		when(userResource.roles()).thenReturn(roleMappingResource);
		when(roleMappingResource.realmLevel()).thenReturn(roleScopeResource);
		when(roleScopeResource.listAll()).thenReturn(Collections.singletonList(roleRepresentation));

		List<String> roles = userRepository.getUserRoles(userId);

		assertEquals(1, roles.size());
		assertEquals(roleName, roles.get(0));
	}

	@Test
	void testAddRoleToUser() {
		String userId = "1";
		String roleName = "role1";
		RoleRepresentation roleRepresentation = new RoleRepresentation();
		roleRepresentation.setName(roleName);

		UserResource userResource = mock(UserResource.class);
		when(usersResource.get(userId)).thenReturn(userResource);

		RoleMappingResource roleMappingResource = mock(RoleMappingResource.class);
		RolesResource rolesResource = mock(RolesResource.class);
		RoleResource roleResource = mock(RoleResource.class);
		RoleScopeResource roleScopeResource = mock(RoleScopeResource.class);
		when(userResource.roles()).thenReturn(roleMappingResource);
		when(realmResource.roles()).thenReturn(rolesResource);
		when(rolesResource.get(roleName)).thenReturn(roleResource);
		when(roleResource.toRepresentation()).thenReturn(roleRepresentation);
		when(roleMappingResource.realmLevel()).thenReturn(roleScopeResource);
		doNothing().when(roleScopeResource).add(anyList());

		List<String> roles = userRepository.addRoleToUser(userId, roleName);

		assertEquals(0, roles.size());
	}


}


