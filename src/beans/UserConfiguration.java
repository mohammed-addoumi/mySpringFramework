package beans;

import annotations.Bean;
import annotations.Configuration;

@Configuration
public class UserConfiguration {
	
	@Bean
	public User user() {
		return new User(1,"simo");
	}

	}
