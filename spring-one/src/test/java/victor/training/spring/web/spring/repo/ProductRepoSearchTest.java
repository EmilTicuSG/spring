package victor.training.spring.web.spring.repo;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.ProductSearchCriteria;
import victor.training.spring.web.domain.Product;
import victor.training.spring.web.repo.ProductRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"db-mem","mock-user-service"})
@Transactional
public class ProductRepoSearchTest {
    @Autowired
    private ProductRepo repo;

    private ProductSearchCriteria criteria = new ProductSearchCriteria();

    @Before
    public void initialize() {
        repo.save(new Product().setName("Lampa"));
    }

    @Test
    public void noCriteria() {
        Assert.assertEquals(1, repo.search(criteria).size());
        Assertions.assertThat(repo.search(criteria)).hasSize(1);
    }
    @Test
    public void byName() {
        criteria.name = "mP";
        Assert.assertEquals(1, repo.search(criteria).size());
        Assertions.assertThat(repo.search(criteria)).hasSize(1);
    }

    // TODO finish

    // TODO base test class persisting supplier

    // TODO replace with composition
}

