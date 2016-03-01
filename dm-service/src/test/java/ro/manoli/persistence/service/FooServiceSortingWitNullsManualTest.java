package ro.manoli.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.manoli.config.PersistenceConfig;
import ro.manoli.persistence.model.Foo;

/**
 * 
 * @author Mihail
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FooServiceSortingWitNullsManualTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FooService service;

	@Test
	@SuppressWarnings("unchecked")
    public final void whenSortingByStringNullLast_thenLastNull() {
        service.create(new Foo());
        service.create(new Foo(randomAlphabetic(6), randomAlphabetic(5)));

        final String jql = "Select f from Foo as f order by f.name desc NULLS LAST";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        assertNull(fooList.get(fooList.toArray().length - 1).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName());
        }
    }

	@Test
	@SuppressWarnings("unchecked")
    public final void whenSortingByStringNullFirst_thenFirstNull() {
        service.create(new Foo());

        final String jql = "Select f from Foo as f order by f.name desc NULLS FIRST";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        assertNull(fooList.get(0).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName());
        }
    }

}