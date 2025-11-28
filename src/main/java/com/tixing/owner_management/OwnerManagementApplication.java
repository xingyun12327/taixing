package com.tixing.owner_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.tixing.owner_management.mapper.AdminMapper;
import com.tixing.owner_management.entity.Admin;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.tixing.owner_management.mapper")  // 添加这行
public class OwnerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwnerManagementApplication.class, args);
	}

	// 启动时可以创建一个默认管理员（如果不存在）
	@Bean
	public CommandLineRunner initData(AdminMapper adminMapper) {
		return args -> {
			if (adminMapper.findByUsername("admin") == null) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String hash = encoder.encode("123456"); // 初始密码 123456
				Admin a = new Admin();
				a.setUsername("admin");
				a.setPassword(hash);
				adminMapper.insert(a);
				System.out.println("默认管理员 admin 已创建, 密码 123456（已加密）");
			}
		};
	}
}