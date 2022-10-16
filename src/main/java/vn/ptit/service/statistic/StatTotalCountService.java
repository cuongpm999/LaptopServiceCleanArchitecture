package vn.ptit.service.statistic;

import org.springframework.stereotype.Service;
import vn.ptit.repository.statistic.IStatisticRepository;

@Service
public class StatTotalCountService implements IStatTotalCountService {
    private final IStatisticRepository statisticRepository;

    public StatTotalCountService(IStatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public Integer totalLaptop(){
        return statisticRepository.totalLaptop();
    }

    @Override
    public Double totalMoneyReceived() {
        return statisticRepository.totalMoneyReceived();
    }

    @Override
    public Integer totalUserPurchased() {
        return statisticRepository.totalUserPurchased();
    }
}
