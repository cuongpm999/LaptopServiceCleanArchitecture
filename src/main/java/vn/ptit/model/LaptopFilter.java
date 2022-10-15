package vn.ptit.model;

import lombok.Getter;

import java.util.List;
@Getter
public class LaptopFilter{
    private QueryFilter queryFilter;
    private String searchText;
    private List<Long> manufacturerIds;
    private List<Integer> categories;
    private List<String> cpus;
    private List<String> rams;
    private List<String> hardDrives;
    private List<String> vgas;

    public LaptopFilter(QueryFilter queryFilter, String searchText, List<Long> manufacturerIds, List<Integer> categories, List<String> cpus, List<String> rams, List<String> hardDrives, List<String> vgas) {
        this.queryFilter = queryFilter;
        this.searchText = searchText;
        this.manufacturerIds = manufacturerIds;
        this.categories = categories;
        this.cpus = cpus;
        this.rams = rams;
        this.hardDrives = hardDrives;
        this.vgas = vgas;
    }

    public static LaptopFilter create(Integer limit, Integer page, String sort, String searchText, List<Long> manufacturerIds, List<Integer> categories, List<String> cpus, List<String> rams, List<String> hardDrives, List<String> vgas) {
        QueryFilter queryFilter = QueryFilter.create(limit, page, sort);
        return new LaptopFilter(queryFilter, searchText, manufacturerIds, categories, cpus, rams, hardDrives, vgas);
    }
}
