package victor.training.spring.web.spring.repo;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import victor.training.spring.web.controller.dto.ProductSearchCriteria;
import victor.training.spring.web.domain.Product;
import victor.training.spring.web.repo.ProductRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("db-mem")
public class ProductRepoSearchTest {
    @Autowired
    private ProductRepo repo;

    private ProductSearchCriteria criteria = new ProductSearchCriteria();

    @Test
    public void noCriteria() {
        repo.save(new Product());
        Assert.assertEquals(1, repo.search(criteria).size());
        Assertions.assertThat(repo.search(criteria)).hasSize(1);
    }

    // TODO finish

    // TODO base test class persisting supplier

    // TODO replace with composition
}

