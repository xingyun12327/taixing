package com.tixing.owner_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.tixing.owner_management.mapper.AdminMapper;
import com.tixing.owner_management.entity.Admin;
import org.mybatis.spring.annotation.MapperScan;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.tixing.owner_management.mapper")
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

	/**
	 * 监听应用启动完成事件，自动打开浏览器
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void openBrowser() {
		String url = "http://localhost:8080/login";
		System.out.println("✅ 应用启动成功，正在尝试使用 Edge 浏览器打开: " + url);

		try {
			String os = System.getProperty("os.name").toLowerCase();
			// 仅针对 Windows 系统尝试调用 Edge
			if (os.contains("win")) {
				// "start msedge" 是 Windows 下启动 Edge 的命令
				Runtime.getRuntime().exec("cmd /c start msedge " + url);
			} else {
				// 非 Windows 系统，提示手动访问
				System.out.println("当前系统非 Windows，请手动访问: " + url);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("❌ 无法自动打开浏览器，请手动访问: " + url);
		}
	}
}