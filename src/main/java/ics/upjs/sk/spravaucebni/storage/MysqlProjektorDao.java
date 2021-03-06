package ics.upjs.sk.spravaucebni.storage;

import ics.upjs.sk.spravaucebni.Projektor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlProjektorDao implements ProjektorDao {

    private JdbcTemplate jdbcTemplate;

    public MysqlProjektorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Projektor> getAll() {
        String sql = "SELECT id, pocet_nasvietenych_hodin, kvalita_obrazu, nazov_modelu, ocakavana_zivotnost_lampy, ucebna_id FROM projektor ORDER BY id";
        return jdbcTemplate.query(sql, new RowMapper<Projektor>() {
            @Override
            public Projektor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Projektor projektor = new Projektor();
                projektor.setId(rs.getLong("id"));
                projektor.setPocetNasvietenychHodin(rs.getInt("pocet_nasvietenych_hodin"));
                projektor.setKvalitaObrazu(rs.getString("kvalita_obrazu"));
                projektor.setNazovModelu(rs.getString("nazov_modelu"));
                projektor.setOcakavanaZivotnostLampy(rs.getInt("ocakavana_zivotnost_lampy"));
                projektor.setUcebnaId(rs.getLong("ucebna_id"));
                return projektor;
            }
        });
    }
    
    @Override
    public List<Projektor> getByUcebnaId(Long id) {
        String sql = "SELECT id, pocet_nasvietenych_hodin, kvalita_obrazu, nazov_modelu, ocakavana_zivotnost_lampy, ucebna_id FROM projektor WHERE ucebna_id = " + id + " ORDER BY id";
        return jdbcTemplate.query(sql, new RowMapper<Projektor>() {
            @Override
            public Projektor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Projektor projektor = new Projektor();
                projektor.setId(rs.getLong("id"));
                projektor.setPocetNasvietenychHodin(rs.getInt("pocet_nasvietenych_hodin"));
                projektor.setKvalitaObrazu(rs.getString("kvalita_obrazu"));
                projektor.setNazovModelu(rs.getString("nazov_modelu"));
                projektor.setOcakavanaZivotnostLampy(rs.getInt("ocakavana_zivotnost_lampy"));
                projektor.setUcebnaId(rs.getLong("ucebna_id"));
                return projektor;
            }
        });
    }

    @Override
    public List<Projektor> getByPouzivatelId(Long id) {
        String sql = "SELECT p.id, p.pocet_nasvietenych_hodin, p.kvalita_obrazu, p.nazov_modelu, p.ocakavana_zivotnost_lampy, p.ucebna_id FROM projektor AS p JOIN ucebna AS u ON p.ucebna_id = u.id WHERE u.pouzivatel_id = " + id + " ORDER BY u.id";
        return jdbcTemplate.query(sql, new RowMapper<Projektor>() {
            @Override
            public Projektor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Projektor projektor = new Projektor();
                projektor.setId(rs.getLong("p.id"));
                projektor.setPocetNasvietenychHodin(rs.getInt("p.pocet_nasvietenych_hodin"));
                projektor.setKvalitaObrazu(rs.getString("p.kvalita_obrazu"));
                projektor.setNazovModelu(rs.getString("p.nazov_modelu"));
                projektor.setOcakavanaZivotnostLampy(rs.getInt("p.ocakavana_zivotnost_lampy"));
                projektor.setUcebnaId(rs.getLong("p.ucebna_id"));
                return projektor;
            }
        });
    }

    @Override
    public Projektor getById(Long id) {
        String sql = "SELECT id, pocet_nasvietenych_hodin, kvalita_obrazu, nazov_modelu, ocakavana_zivotnost_lampy, ucebna_id FROM projektor WHERE id = " + id;
        return jdbcTemplate.queryForObject(sql, new RowMapper<Projektor>() {
            @Override
            public Projektor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Projektor projektor = new Projektor();
                projektor.setId(rs.getLong("id"));
                projektor.setPocetNasvietenychHodin(rs.getInt("pocet_nasvietenych_hodin"));
                projektor.setKvalitaObrazu(rs.getString("kvalita_obrazu"));
                projektor.setNazovModelu(rs.getString("nazov_modelu"));
                projektor.setOcakavanaZivotnostLampy(rs.getInt("ocakavana_zivotnost_lampy"));
                projektor.setUcebnaId(rs.getLong("ucebna_id"));
                return projektor;
            }
        });
    }

    
    
    @Override
    public boolean save(Projektor p) {
        if (p == null) {
            return false;
        }
        try {
            if (p.getId() == null) {
                SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
                simpleJdbcInsert.withTableName("projektor");
                simpleJdbcInsert.usingGeneratedKeyColumns("id");
                simpleJdbcInsert.usingColumns("pocet_nasvietenych_hodin", "kvalita_obrazu", "nazov_modelu", "ocakavana_zivotnost_lampy", "ucebna_id");
                Map<String, Object> data = new HashMap<>();
                data.put("pocet_nasvietenych_hodin", p.getPocetNasvietenychHodin());
                data.put("kvalita_obrazu", p.getKvalitaObrazu());
                data.put("nazov_modelu", p.getNazovModelu());
                data.put("ocakavana_zivotnost_lampy", p.getOcakavanaZivotnostLampy());
                data.put("ucebna_id", p.getUcebnaId());
                p.setId(simpleJdbcInsert.executeAndReturnKey(data).longValue());
            } else {
                String sql = "UPDATE projektor SET pocet_nasvietenych_hodin = ?, kvalita_obrazu = ?, nazov_modelu = ?, ocakavana_zivotnost_lampy = ?, ucebna_id = ? WHERE id = " + p.getId();
                jdbcTemplate.update(sql, p.getPocetNasvietenychHodin(), p.getKvalitaObrazu(), p.getNazovModelu(), p.getOcakavanaZivotnostLampy(), p.getUcebnaId());
            }
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM projektor WHERE id = " + id;
        try {
            int zmazanych = jdbcTemplate.update(sql);
            return zmazanych == 1;
        } catch (Exception e) {
            return false;
        }
    }

}
