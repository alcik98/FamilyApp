package Inteca.rekrutacja.FamilyMemberApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class FamilyMemberRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<FamilyMember> getAll() {
        return jdbcTemplate.query("Select familyId, familyName, givenName, age, id FROM FamilyMember",
                BeanPropertyRowMapper.newInstance(FamilyMember.class));
    }

    public List<FamilyMember> getFamily (int familyId) {
        System.out.println("getFamily: "+familyId);
        return jdbcTemplate.query("Select familyId, familyName, givenName, age, id FROM FamilyMember "+"WHERE familyId = ?",
                BeanPropertyRowMapper.newInstance(FamilyMember.class), familyId);
    }

    public FamilyMember getMember(int id) {
        return jdbcTemplate.queryForObject("Select familyId, familyName, givenName, age, id FROM FamilyMember "+"WHERE id = ?",
                BeanPropertyRowMapper.newInstance(FamilyMember.class), id);
    }

    public int createFamilyMember(FamilyMember member) {
        System.out.println("createFamilyMember");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "Insert into FamilyMember (familyId, familyName, givenName, age) VALUES (?,?,?,?)";


                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[] { "ID" });
                    ps.setInt(1, member.getFamilyId());
                    ps.setString(2, member.getFamilyName());
                    ps.setString(3, member.getGivenName());
                    ps.setInt(4, member.getAge());
                    return ps;
                }, keyHolder);

        //Zwracanie id utworzonych czlonkow rodziny
        //return keyHolder.getKey().intValue();
        return 1;
    }

}
