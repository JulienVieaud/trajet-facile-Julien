package com.poe.trajetfacile.business.delegate;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoDelegateServiceTests {

    @Test
    public void testCasMajeur() {
        // création du jeux de données
        LocalDate majeur = LocalDate.now().minusYears(30);
        assertThat(DemoDelegateService.estMajeurEnFrance(majeur)).isTrue();
    }

    @Test
    public void testCasMineur() {
        LocalDate mineur = LocalDate.now().minusYears(10);
        assertThat(DemoDelegateService.estMajeurEnFrance(mineur)).isFalse();
    }

    @Test
    public void testCas18() {
        LocalDate age18ans = LocalDate.now().minusYears(18);
        assertThat(DemoDelegateService.estMajeurEnFrance(age18ans)).isTrue();
    }
}
