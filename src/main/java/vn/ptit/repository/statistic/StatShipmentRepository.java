package vn.ptit.repository.statistic;

import org.springframework.stereotype.Repository;
import vn.ptit.model.ShipmentStat;
import javax.persistence.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class StatShipmentRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public List<ShipmentStat> shipmentWithTotalShipped() {
        String sql = "SELECT shipments.*, A.SoLuong FROM shipments, (SELECT COUNT(id) AS SoLuong, shipment_id FROM orders WHERE status = 2 GROUP BY shipment_id) AS A WHERE shipments.id = A.shipment_id AND shipments.is_delete = FALSE";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> records = query.getResultList();
        List<ShipmentStat> shipmentStats = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            ShipmentStat shipmentStat = new ShipmentStat();
            shipmentStat.setId(Long.parseLong(records.get(i)[0].toString()));
            shipmentStat.setAddress(records.get(i)[3].toString());
            shipmentStat.setName(records.get(i)[5].toString());
            shipmentStat.setPrice(Double.parseDouble(records.get(i)[6].toString()));
            shipmentStat.setTotalShipped(Integer.parseInt(records.get(i)[7].toString()));
            shipmentStats.add(shipmentStat);
        }

        Collections.sort(shipmentStats, (o1, o2) -> o2.getTotalShipped()-o1.getTotalShipped());

        return shipmentStats;
    }
}
