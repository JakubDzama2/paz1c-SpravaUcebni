package ics.upjs.sk.spravaucebni.storage;

import ics.upjs.sk.spravaucebni.Pocitac;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PocitacDaoTest {

    private PocitacDao dao = DaoFactory.INSTANCE.getPocitacDao();
    
    public PocitacDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
 
    @Test
    public void getAllTest() {
        List<Pocitac> all = dao.getAll();
        assertTrue(all.size() > 0);
    }
    
    @Test
    public void getByUcebnaIdTest() {
        UcebnaDao ucebnaDao = DaoFactory.INSTANCE.getUcebnaDao();
        Long ucebnaId = ucebnaDao.getAll().get(0).getId();
        List<Pocitac> allByUcebnaId = dao.getByUcebnaId(ucebnaId);
        for (Pocitac pocitac : allByUcebnaId) {
            assertTrue(pocitac.getUcebnaId() == ucebnaId);
        }
    }
   
    @Test
    public void getByIdTest() {
        Pocitac o1 = dao.getAll().get(0);
        Pocitac o2 = dao.getById(o1.getId());
        assertEquals(o1.getId(), o2.getId());
    }
    
    @Test
    public void saveTest() {
        List<Pocitac> all = dao.getAll();
        Pocitac pocitac = new Pocitac();
        pocitac.setPoslednePouzitie(LocalDate.now());
        pocitac.setSerioveCislo("Rambo2000");
        pocitac.setMacAdresa("ajaj 12sd m5b4 8e9o");
        pocitac.setUcebnaId(1L);
        assertTrue(dao.save(pocitac));
        assertTrue(all.size() + 1 == dao.getAll().size());
        
    }
    
    @Test
    public void deleteTest() {
        int velkost = dao.getAll().size();
        Pocitac p = dao.getAll().get(velkost - 1);
        boolean jeVymazany = dao.delete(p.getId());
        assertTrue(jeVymazany);
    }

}
