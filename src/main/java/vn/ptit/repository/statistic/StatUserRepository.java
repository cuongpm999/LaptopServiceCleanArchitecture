package vn.ptit.repository.statistic;

import org.springframework.stereotype.Repository;
import vn.ptit.model.QueryFilter;
import vn.ptit.model.UserStat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class StatUserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<UserStat> userWithTotalMoney(QueryFilter filter) {
        String sql = "SELECT users.*, A.TongTien FROM users, " +
                "(SELECT SUM(total_money) AS TongTien, user_id FROM payments, orders " +
                "WHERE status = 2 AND payments.id = orders.payment_id GROUP BY user_id) AS A " +
                "WHERE users.id = A.user_id AND is_delete = FALSE";

        if (filter.getSort().equals("asc")) {
            sql += " order by A.TongTien asc";
        } else sql += " order by A.TongTien desc";

        Query query = entityManager.createNativeQuery(sql);

        query.setFirstResult(filter.getPage() * filter.getLimit());
        query.setMaxResults(filter.getLimit());
        List<Object[]> records = query.getResultList();

        List<UserStat> userStats = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            UserStat userStat = new UserStat();
            userStat.setId(Long.parseLong(records.get(i)[0].toString()));
            userStat.setAddress(records.get(i)[3].toString());
            userStat.setAvatar(records.get(i)[4].toString());
            userStat.setDateOfBirth((Date) records.get(i)[5]);
            userStat.setEmail(records.get(i)[6].toString());
            userStat.setFullName(records.get(i)[7].toString());
            userStat.setMobile(records.get(i)[9].toString());
            userStat.setPosition(records.get(i)[11].toString());
            userStat.setSex((Boolean) records.get(i)[12]);
            userStat.setUsername(records.get(i)[13].toString());
            userStat.setTotalMoney(Double.parseDouble(records.get(i)[14].toString()));
            userStats.add(userStat);
        }

        return userStats;
    }
}
