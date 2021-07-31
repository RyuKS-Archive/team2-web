package com.kb.team2.repository;

import com.kb.team2.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    @Query("select m.email from member m where m.email = :email")
    public Optional<Member> chkByEmail(@Param("email") String email);

    @Query(value = "select * from member where email = :email and password = :password", nativeQuery = true)
    public Optional<Member> chkByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query("select new com.kb.team2.entity.Member(m.email, m.chk_code) from member m where m.email = :email")
    public Optional<Member> findByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update member set l_date = now() where idx = :idx", nativeQuery = true)
    public Integer updateLastLogin(@Param("idx") int idx);

    @Transactional
    @Modifying
    @Query(value = "insert into member (email, password, name) values (:email, :password, :name)", nativeQuery = true)
    public Integer insertMember(@Param("email") String email, @Param("password") String password, @Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "update member set chk_flg = 1 where chk_code = :code", nativeQuery = true)
    public Integer updateAuthFlg(@Param("code") String code);

    @Transactional
    @Modifying
    @Query(value = "update member set os_use_flg = 1, os_project_name = :p_name, os_user_domain_name = :u_domain, " +
            "os_project_domain_name = :p_domain where email = :email", nativeQuery = true)
    public Integer updateOsUse(@Param("p_name") String p_name, @Param("u_domain") String u_domain, @Param("p_domain")
            String p_domain, @Param("email") String email);

}

