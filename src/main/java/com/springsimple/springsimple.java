package com.springsimple;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.JavaVersion;
import org.springframework.core.SpringVersion;

import com.springsimple.Model.GameModel;
import com.springsimple.Model.PlayerModel;


@SpringBootApplication
public class springsimple implements ApplicationRunner {


	public static void main(String[] args) {
		
		System.out.println(SpringVersion.getVersion());
		System.out.println(JavaVersion.getJavaVersion());
		System.out.println("------------------------------------------");
		
		
		SpringApplication.run(springsimple.class, args);


	}

	@Override
	public void run(ApplicationArguments arg) {

	}



}
