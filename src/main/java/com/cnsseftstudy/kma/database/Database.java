package com.cnsseftstudy.kma.database;

import com.cnsseftstudy.kma.models.Brand;
import com.cnsseftstudy.kma.models.Category;
import com.cnsseftstudy.kma.models.Product;
import com.cnsseftstudy.kma.repositories.BrandRepository;
import com.cnsseftstudy.kma.repositories.CategoryRepository;
import com.cnsseftstudy.kma.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    @Bean
    CommandLineRunner initDatabase(BrandRepository brandRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Brand c1 = new Brand("CASIO","Casio là thương hiệu đến từ công ty chế tạo thiết bị điện tử của Nhật Bản. " +
//                        "Casio được Kashio Tadao thành lập vào năm 1946, hiện công ty có trụ sở chính tại Tokyo.");
//                Brand c2 = new Brand("CITIZEN","Năm 1918, thị trưởng Tokyo Shinpei Goto cùng với Kamekichi Yakamazi đã sáng lập" +
//                        " nên thương hiệu đồng hồ Citizen" );
//                brandRepository.save(c1);
//                brandRepository.save(c2);
            }
        };
    }
}
