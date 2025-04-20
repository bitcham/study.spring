package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * JDBC - ConnectionParam
 */
@Slf4j
public class MemberRepositoryV2 {

    private final DataSource dataSource;

    public MemberRepositoryV2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberId());
            preparedStatement.setInt(2, member.getMoney());
            preparedStatement.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Member member = new Member();
                member.setMemberId(resultSet.getString("member_id"));
                member.setMoney(resultSet.getInt("money"));
                return member;
            } else{
                throw new NoSuchElementException("memberId not found memberId : " + memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    public Member findById(Connection connection, String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Member member = new Member();
                member.setMemberId(resultSet.getString("member_id"));
                member.setMoney(resultSet.getInt("money"));
                return member;
            } else{
                throw new NoSuchElementException("memberId not found memberId : " + memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(preparedStatement);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, money);
            preparedStatement.setString(2, memberId);
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    public void update(Connection connection, String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, money);
            preparedStatement.setString(2, memberId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            JdbcUtils.closeStatement(preparedStatement);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        JdbcUtils.closeResultSet(resultSet);
        JdbcUtils.closeStatement(preparedStatement);
        JdbcUtils.closeConnection(connection);
    }

    private Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        log.info("connection : {}, class : {}", connection, connection.getClass());
        return connection;
    }

}
