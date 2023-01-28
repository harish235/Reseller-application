package com.quinbay.advertiz;

import com.quinbay.advertiz.Repositories.CategoryRepository;
import com.quinbay.advertiz.Repositories.SubcategoryRepository;
import com.quinbay.advertiz.model.Category;
import com.quinbay.advertiz.model.Subcategory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AdvertizApplication {


	public static void main(String[] args) {
		SpringApplication.run(AdvertizApplication.class, args);

	}

//	@Bean
//	public CommandLineRunner mappingDemo(CategoryRepository categoryRepository,
//										 SubcategoryRepository subcategoryRepository) {
//		return args -> {
//
//			// create a new book
//			Category category = new Category("Phone");
//
//			// save the book
//			categoryRepository.save(category);
//
//			// create and save new pages
//			subcategoryRepository.save(new Subcategory("Oneplus", category));
//			subcategoryRepository.save(new Subcategory("Apple", category));
//			subcategoryRepository.save(new Subcategory("Samsung", category));
//		};
//	}
}
