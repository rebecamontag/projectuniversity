package com.rebecamontag.projectuniversity;

import com.rebecamontag.projectuniversity.configuration.PostgresContainerConfiguration;
import com.rebecamontag.projectuniversity.repository.ProfessorRepository;
import com.rebecamontag.projectuniversity.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(initializers = PostgresContainerConfiguration.class)
@AutoConfigureMockMvc
public class BaseIT {

    @Autowired
    protected ProfessorRepository professorRepository;

    @Autowired
    protected StudentRepository studentRepository;

    @Autowired
    protected MockMvc mockMvc;



}
