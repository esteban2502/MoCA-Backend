package com.ucp.moca;

import com.ucp.moca.entity.PermissionEntity;
import com.ucp.moca.entity.RoleEntity;
import com.ucp.moca.entity.RoleEnum;
import com.ucp.moca.entity.UserEntity;
import com.ucp.moca.repository.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MocaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MocaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserEntityRepository userEntityRepository){
		return args->{
			/*Creo los permisos*/

			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();

			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();

			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();

			/*Creo los roles*/

			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissionList(Set.of(createPermission,readPermission,updatePermission,deletePermission))
					.build();

			/*CREATE USERS*/

			UserEntity userEsteban = UserEntity.builder()
					.email("juanes@gmail.com")
					.password("$2a$10$SF4XUCMWBG53RZ..HjyuRus8gkqWO36bk71n2wThEluHeb8BkyVJ.")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleUser))
					.build();

			UserEntity userDavid = UserEntity.builder()
					.email("david@gmail.com")
					.password("$2a$10$SF4XUCMWBG53RZ..HjyuRus8gkqWO36bk71n2wThEluHeb8BkyVJ.")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleUser))
					.build();

			userEntityRepository.saveAll(List.of(userEsteban,userDavid));

		};

	}
}
