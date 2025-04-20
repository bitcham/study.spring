package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Slf4j
class MemberRepositoryV1est {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() throws Exception{
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV1", 10000);
        repository.save(member);

        //findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember : {}", findMember);
        assertThat(findMember).isEqualTo(member);

        //update
        repository.update(member.getMemberId(), 20000);
        Member updateMember = repository.findById(member.getMemberId());
        log.info("updateMember : {}", updateMember);
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        //delete
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId())).isInstanceOf(NoSuchElementException.class);
    }



}