package vn.ptit.service.statistic;

import org.springframework.stereotype.Service;
import vn.ptit.repository.statistic.IStatisticTotalCountRepository;

@Service
public class StatTotalCountService implements IStatTotalCountService {
    private final IStatisticTotalCountRepository statisticRepository;

    public StatTotalCountService(IStatisticTotalCountRepository statisticRepository) {
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
