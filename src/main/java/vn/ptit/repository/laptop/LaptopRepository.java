package vn.ptit.repository.laptop;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.model.Laptop;
import vn.ptit.model.QueryFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LaptopRepository implements ILaptopRepository{
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
        if(laptopEntity == null)
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
    public List<Laptop> filter(QueryFilter filter, String searchText, String manufacturerId, String category,
                               String cpu, String ram, String hardDrive, String vga) {
        List<Long> manufacturerIds = new ArrayList<>();
        List<Integer> categories = new ArrayList<>();
        List<String> cpus = new ArrayList<>();
        List<String> rams = new ArrayList<>();
        List<String> hardDrives = new ArrayList<>();
        List<String> vgas = new ArrayList<>();
        String jpql = "select l from LaptopEntity l where l.isDelete = false";
        if(searchText != null && !searchText.equals("")){
            jpql += " and (l.name LIKE '%" + searchText + "%'" + " or l.manufacturer.name LIKE '%" + searchText + "%')";
        }
        if(manufacturerId != null && !manufacturerId.equals("")){
            String[] strManufacturerIds = manufacturerId.split("\\,");
            manufacturerIds = Arrays.asList(strManufacturerIds).stream().map(Long::parseLong).collect(Collectors.toList());
            jpql += " and l.manufacturer.id IN :manufacturerIds";
        }
        if(category != null && !category.equals("")){
            String[] strCategoríe = category.split("\\,");
            categories = Arrays.asList(strCategoríe).stream().map(Integer::parseInt).collect(Collectors.toList());
            jpql += " and category IN :categories";
        }
        if(cpu != null && !cpu.equals("")){
            cpus = Arrays.asList(cpu.split("\\,"));
            jpql += " and cpu IN :cpus";
        }
        if(ram != null && !ram.equals("")){
            rams = Arrays.asList(ram.split("\\,"));
            jpql += " and ram IN :rams";
        }
        if(hardDrive != null && !hardDrive.equals("")){
            hardDrives = Arrays.asList(hardDrive.split("\\,"));
            jpql += " and hardDrive IN :hardDrives";
        }
        if(vga != null && !vga.equals("")){
            vgas = Arrays.asList(vga.split("\\,"));
            jpql += " and vga IN :vgas";
        }
        javax.persistence.Query query = entityManager.createQuery(jpql, LaptopEntity.class);
        if(manufacturerIds.size() > 0){
            query.setParameter("manufacturerIds", manufacturerIds);
        }
        if(categories.size() > 0){
            query.setParameter("categories", categories);
        }
        if(cpus.size() > 0){
            query.setParameter("cpus", cpus);
        }
        if(rams.size() > 0){
            query.setParameter("rams", rams);
        }
        if(hardDrives.size() > 0){
            query.setParameter("hardDrives", hardDrives);
        }
        if(vgas.size() > 0){
            query.setParameter("vgas", vgas);
        }
        List<LaptopEntity> laptops = query.getResultList();
        if(filter.getSort().equals("asc")){
            laptops.sort((o1, o2) -> {
                double price1 = o1.getPrice() * (100 - o1.getDiscount());
                double price2 = o2.getPrice() * (100 - o2.getDiscount());
                return Double.compare(price1, price2);
            });
        }else if(filter.getSort().equals("desc")){
            laptops.sort((o1, o2) -> {
                double price1 = o1.getPrice() * (100 - o1.getDiscount());
                double price2 = o2.getPrice() * (100 - o2.getDiscount());
                return Double.compare(price2, price1);
            });
        }
        return laptops.stream().map(LaptopEntity::toDomain).collect(Collectors.toList());
    }
}
