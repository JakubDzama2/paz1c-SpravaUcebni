package ics.upjs.sk.spravaucebni.storage;

import ics.upjs.sk.spravaucebni.Spotreba;
import java.util.List;

public interface SpotrebaDao {

    List<Spotreba> getAll();

    public List<Spotreba> getByUcebnaId(Long id);
    
}