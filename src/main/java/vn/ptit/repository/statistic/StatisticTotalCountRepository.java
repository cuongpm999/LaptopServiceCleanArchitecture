package vn.ptit.repository.statistic;

import org.springframework.stereotype.Repository;
import vn.ptit.repository.laptop.LaptopJpa;
import vn.ptit.repository.order.OrderJpa;
import vn.ptit.repository.user.UserJpa;

@Repository
public class StatisticTotalCountRepository implements IStatisticTotalCountRepository {
    private final LaptopJpa laptopJpa;
    private final OrderJpa orderJpa;
    private final UserJpa userJpa;

    public StatisticTotalCountRepository(LaptopJpa laptopJpa, OrderJpa orderJpa, UserJpa userJpa) {
        this.laptopJpa = laptopJpa;
        this.orderJpa = orderJpa;
        this.userJpa = userJpa;
    }

    @Override
    public Integer totalLaptop() {
        return laptopJpa.countByIsDeleteFalse();
    }

    @Override
    public Double totalMoneyReceived() {
        return orderJpa.totalMoneyReceived();
    }

    @Override
    public Integer totalUserPurchased() {
        return userJpa.countUserPurchased();
    }
}
