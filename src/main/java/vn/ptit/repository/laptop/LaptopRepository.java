package vn.ptit.repository.laptop;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.model.Laptop;
import vn.ptit.model.LaptopFilter;
import vn.ptit.model.QueryFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LaptopRepository implements ILaptopRepository {
    private final LaptopJpa laptopJpa;
    private final ImageJpa imageJpa;
    @PersistenceContext
    private EntityManager entityManager;

    public LaptopRepository(LaptopJpa laptopJpa, ImageJpa imageJpa) {
        this.laptopJpa = laptopJpa;
        this.imageJpa = imageJpa;
    }

    @Override
    public void save(Laptop laptop) {
        laptopJpa.save(LaptopEntity.fromDomain(laptop));
    }

    @Override
    public Laptop findById(long id) {
        LaptopEntity laptopEntity = laptopJpa.findByIsDeleteFalseAndId(id);
        if (laptopEntity == null)
            return null;
        return laptopEntity.toDomain();
    }

    @Override
    public List<Laptop> findAll(QueryFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit(),
                filter.getSort().equals("asc") ? Sort.by("updatedAt").ascending() : Sort.by("updatedAt").descending());
        return laptopJpa.findByIsDeleteFalse(pageable).stream().map(LaptopEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(Laptop laptop) {
        imageJpa.deleteImageByLaptopId(laptop.getId());
        save(laptop);
    }

    @Override
    public List<Laptop> findSameManufacturer(long manufacturerId, long id, int limit) {
        return laptopJpa.findSameManufacturer(manufacturerId, id, limit).stream().map(LaptopEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Laptop> filter(LaptopFilter laptopFilter) {
        String searchText = laptopFilter.getSearchText();
        List<Long> manufacturerIds = laptopFilter.getManufacturerIds();
        List<Integer> categories = laptopFilter.getCategories();
        List<String> cpus = laptopFilter.getCpus();
        List<String> rams = laptopFilter.getRams();
        List<String> hardDrives = laptopFilter.getHardDrives();
        List<String> vgas = laptopFilter.getVgas();
        Integer page = laptopFilter.getQueryFilter().getPage();
        Integer limit = laptopFilter.getQueryFilter().getLimit();
        String sort = laptopFilter.getQueryFilter().getSort();
        String jpql = "select l from LaptopEntity l where l.isDelete = false";
        if (searchText != null && !searchText.equals("")) {
            jpql += " and (l.name LIKE '%" + searchText + "%'" + " or l.manufacturer.name LIKE '%" + searchText + "%')";
        }
        if (manufacturerIds != null && !manufacturerIds.isEmpty()) {
            jpql += " and l.manufacturer.id IN :manufacturerIds";
        }
        if (categories != null && !categories.isEmpty()) {
            jpql += " and l.category IN :categories";
        }
        if (cpus != null && !cpus.isEmpty()) {
            jpql += " and l.cpu IN :cpus";
        }
        if (rams != null && !rams.isEmpty()) {
            jpql += " and l.ram IN :rams";
        }
        if (hardDrives != null && !hardDrives.isEmpty()) {
            jpql += " and l.hardDrive IN :hardDrives";
        }
        if (vgas != null && !vgas.isEmpty()) {
            jpql += " and l.vga IN :vgas";
        }
        javax.persistence.Query query = entityManager.createQuery(jpql, LaptopEntity.class);
        if (manufacturerIds != null && !manufacturerIds.isEmpty()) {
            query.setParameter("manufacturerIds", manufacturerIds);
        }
        if (categories != null && !categories.isEmpty()) {
            query.setParameter("categories", categories);
        }
        if (cpus != null && !cpus.isEmpty()) {
            query.setParameter("cpus", cpus);
        }
        if (rams != null && !rams.isEmpty()) {
            query.setParameter("rams", rams);
        }
        if (hardDrives != null && !hardDrives.isEmpty()) {
            query.setParameter("hardDrives", hardDrives);
        }
        if (vgas != null && !vgas.isEmpty()) {
            query.setParameter("vgas", vgas);
        }
        query.setFirstResult(page * limit);
        query.setMaxResults(limit);
        List<LaptopEntity> laptops = query.getResultList();
        if (sort.equals("asc")) {
            laptops.sort((o1, o2) -> {
                double price1 = o1.getPrice() * (100 - o1.getDiscount());
                double price2 = o2.getPrice() * (100 - o2.getDiscount());
                return Double.compare(price1, price2);
            });
        } else if (sort.equals("desc")) {
            laptops.sort((o1, o2) -> {
                double price1 = o1.getPrice() * (100 - o1.getDiscount());
                double price2 = o2.getPrice() * (100 - o2.getDiscount());
                return Double.compare(price2, price1);
            });
        }
        return laptops.stream().map(LaptopEntity::toDomain).collect(Collectors.toList());
    }
}
