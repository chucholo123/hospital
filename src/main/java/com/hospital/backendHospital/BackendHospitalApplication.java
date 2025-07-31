package com.hospital.backendHospital;

import com.hospital.backendHospital.models.entity.*;
import com.hospital.backendHospital.repositories.PermissionRepository;
import com.hospital.backendHospital.repositories.RoleRepository;
import com.hospital.backendHospital.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode =  EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class BackendHospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendHospitalApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner init(
			UserRepository userRepository,
			RoleRepository roleRepository,
			PermissionRepository permissionRepository,
			PasswordEncoder passwordEncoder) {

		return args -> {
			// 1. Crear permisos primero (con verificación de existencia)
			Permission createPermission = createPermissionIfNotExists(permissionRepository, "CREATE");
			Permission readPermission = createPermissionIfNotExists(permissionRepository, "READ");
			Permission updatePermission = createPermissionIfNotExists(permissionRepository, "UPDATE");
			Permission deletePermission = createPermissionIfNotExists(permissionRepository, "DELETE");
			Permission refactorPermission = createPermissionIfNotExists(permissionRepository, "REFACTOR");

			// 2. Crear roles (con verificación de existencia)
			Role roleAdmin = createRoleIfNotExists(roleRepository, RoleEnum.ADMIN,
					Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission));

			Role roleDoctor = createRoleIfNotExists(roleRepository, RoleEnum.DOCTOR,
					Set.of(createPermission, readPermission, updatePermission));

			Role roleRecepcionist = createRoleIfNotExists(roleRepository, RoleEnum.RECEPTIONIST,
					Set.of(createPermission, readPermission));

			Role rolePatient = createRoleIfNotExists(roleRepository, RoleEnum.PATIENT,
					Set.of(readPermission));

			roleRepository.save(rolePatient);

			// 3. Crear usuarios
			createUserIfNotExists(userRepository, passwordEncoder,
					"chucholo", "Irving Jesus", "Garcia Flores", "1234", roleAdmin);
		};
	}

	private Permission createPermissionIfNotExists(PermissionRepository repository, String name) {
		return repository.findByName(name)
				.orElseGet(() -> repository.save(
						Permission.builder().name(name).build()
				));
	}

	private Role createRoleIfNotExists(RoleRepository repository, RoleEnum roleEnum, Set<Permission> permissions) {
		return repository.findByRoleEnum(roleEnum)
				.orElseGet(() -> {
					Role role = Role.builder()
							.roleEnum(roleEnum)
							.build();
					Role savedRole = repository.save(role);
					// Establecer permisos después de guardar el rol
					savedRole.setPermissions(permissions);
					return repository.save(savedRole); // Guardar nuevamente con permisos
				});
	}

	private void createUserIfNotExists(UserRepository repository,
									   PasswordEncoder encoder,
									   String username,
									   String firstName,
									   String lastName,
									   String password,
									   Role role) {
		if (!repository.existsByEmail(username)) {
			User user = User.builder()
					.email(username)
					.firstName(firstName)
					.lastName(lastName)
					.password(encoder.encode(password))
					.build();
			User savedUser = repository.save(user);
			// Establecer rol después de guardar el usuario
			savedUser.setRoles(Set.of(role));
			repository.save(savedUser);
		}
	}
}