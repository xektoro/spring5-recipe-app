package com.example.spring5recipeapp.repositories;

import com.example.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

// With Junit 5, we do not need @RunWith(SpringRunner.class) anymore.
// Spring tests are executed with @ExtendWith(SpringExtension.class) and @SpringBootTest

// if we have multiple integration tests in one class, then the Spring Context is going to be loaded only once

// that annotation will bring up an embedded database and it's also going
// to configure Spring Data JPA for us
@DataJpaTest
// by convention ending with "IT" means "Integration Test"
class UnitOfMeasureRepositoryIT {

    // now the Spring is going to do a DI on our integration test here
    // so the Spring Context will start up and we will get an instance of UnitOfMeasureRepository injected into this
    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByDescription() {

        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", uomOptional.get().getDescription());
    }

    // if we have multiple integration tests in one class, then the Spring Context is going to be loaded only once
    // but if we annotate a test with @DirtiesContext, then when this test is getting executed, the Spring Context is going to be reloaded
    // that is usually done when we have changed the data and we don't want to pollute the other tests
    @Test
    @DirtiesContext
    void findByDescriptionCup() {

        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");
        assertEquals("Cup", uomOptional.get().getDescription());
    }
}