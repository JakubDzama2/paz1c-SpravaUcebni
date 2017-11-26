package ics.upjs.sk.spravaucebni.storage;

import ics.upjs.sk.spravaucebni.Tabula;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlTabulaDao implements TabulaDao {

    private JdbcTemplate jdbcTemplate;

    public MysqlTabulaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Tabula> getAll() {
        String sql = "SELECT id, typ, pocet_pisatiek FROM tabula";
        return jdbcTemplate.query(sql, new RowMapper<Tabula> () {
            @Override
            public Tabula mapRow(ResultSet rs, int rowNum) throws SQLException {
                Tabula tabula = new Tabula();
                tabula.setId(rs.getLong("id"));
                tabula.setTyp(rs.getString("typ"));
                tabula.setPocetPisatiek(rs.getInt("pocet_pisatiek"));
                return tabula;
            }
        });
    }

    @Override
    public List<Tabula> getByUcebnaId(Long id) {
        String sql = "SELECT id, typ, pocet_pisatiek FROM tabula WHERE ucebna_id = " + id;
        return jdbcTemplate.query(sql, new RowMapper<Tabula> () {
            @Override
            public Tabula mapRow(ResultSet rs, int rowNum) throws SQLException {
                Tabula tabula = new Tabula();
                tabula.setId(rs.getLong("id"));
                tabula.setTyp(rs.getString("typ"));
                tabula.setPocetPisatiek(rs.getInt("pocet_pisatiek"));
                return tabula;
            }
        });
    }
    
}